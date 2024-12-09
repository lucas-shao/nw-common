package com.normalworks.common.webencrypt.web;

import com.normalworks.common.utils.AssertUtil;
import com.normalworks.common.utils.assertion.AssertionException;
import com.normalworks.common.utils.assertion.CommonResultCode;
import com.normalworks.common.webencrypt.record.repository.EncryptConfigRecordRepository;
import com.normalworks.common.webencrypt.util.AESUtil;
import com.normalworks.common.webencrypt.record.EncryptConfigRecord;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * WebEncryptionService
 *
 * @author: lingeng
 * @date: 10/19/22
 */
@Service
public class WebEncryptFilterService {

    private final static Logger LOGGER = LoggerFactory.getLogger(WebEncryptFilterService.class);

    /**
     * 加密响应返回值类型
     */
    private static final String ENCRYPT_RESPONSE_CONTENT_TYPE = "text/plain";

    @Resource
    private EncryptConfig encryptConfig;

    @Resource
    private EncryptConfigRecordRepository encryptConfigRecordRepository;

    public void process(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, WebEncryptFilterServiceCallback callback) {

        String uri = request.getRequestURI();
        LOGGER.info("RequestURI: {}", uri);

        try {

            // 调试模式不加解密
            if (encryptConfig.isDebug()) {
                LOGGER.info("出入参数据加密调试模式开启，未执行API出入参数加解密逻辑");
                filterChain.doFilter(request, response);
                return;
            }

            // 非调试模式下，判断是否需要入参解密，出参加密
            boolean decryptionStatus = needEncryption(ApiEncryptDataInit.getRequestDecryptUriList(), uri, request);
            boolean encryptionStatus = needEncryption(ApiEncryptDataInit.getResponseEncryptUriList(), uri, request);

            if (!decryptionStatus && !encryptionStatus) {
                LOGGER.info("出入参数无须执行加解密逻辑");
                filterChain.doFilter(request, response);
                return;
            }

            EncryptRequestWrapper requestWrapper = null;
            EncryptResponseWrapper responseWrapper = null;

            String aesKeyId = callback.getAesKeyId();

            EncryptConfigRecord encryptConfigRecord = encryptConfigRecordRepository.queryByAesKeyId(aesKeyId);
            AssertUtil.notNull(encryptConfigRecord, CommonResultCode.UNKNOWN_EXCEPTION, "AES key不存在，aesKeyId = " + aesKeyId);
            AssertUtil.isTrue(encryptConfigRecord.getAesInvalidTime().after(new Date()), CommonResultCode.UNKNOWN_EXCEPTION, "AES key已失效，aesKeyId = " + aesKeyId);

            String aesKey = encryptConfigRecord.getAesKey();

            if (decryptionStatus) {
                LOGGER.info("开始解密入参请求");
                requestWrapper = new EncryptRequestWrapper(request);
                processDecryption(requestWrapper, request, aesKey);
            }

            if (encryptionStatus) {
                responseWrapper = new EncryptResponseWrapper(response);
            }

            // 同时需要加解密
            if (encryptionStatus && decryptionStatus) {
                filterChain.doFilter(requestWrapper, responseWrapper);
            } else if (encryptionStatus) {
                // 只需要响应加密
                filterChain.doFilter(request, responseWrapper);
            } else if (decryptionStatus) {
                // 只需要请求解密
                filterChain.doFilter(requestWrapper, response);
            }

            // 配置了需要加密才处理
            if (encryptionStatus) {
                LOGGER.info("开始加密响应数据");
                String responseData = responseWrapper.getResponseData();
                writeEncryptContent(responseData, response, aesKey);
            }

        } catch (AssertionException xccException) {

            LOGGER.warn("执行接口数据加解密业务异常", xccException);
            response.setStatus(xccException.getResultCode().getHttpStatus());
        } catch (Exception exception) {

            LOGGER.error("执行接口数据加解密系统异常", exception);

            // 返回500错误码，服务端内部错误
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    /**
     * 输出加密内容
     *
     * @param responseData
     * @param response
     * @param aesKey
     */
    private void writeEncryptContent(String responseData, HttpServletResponse response, String aesKey) throws IOException {

        LOGGER.info("ResponseData: {}", responseData);
        ServletOutputStream out = null;
        long start = System.currentTimeMillis();
        try {
            responseData = AESUtil.aesEncrypt(responseData, aesKey);
            LOGGER.info("EncryptResponseData: {}", responseData);
            response.setContentLength(responseData.length());
            response.setCharacterEncoding(encryptConfig.getResponseCharset());
            response.setContentType(ENCRYPT_RESPONSE_CONTENT_TYPE);
            out = response.getOutputStream();
            out.write(responseData.getBytes(encryptConfig.getResponseCharset()));
        } catch (Exception e) {
            LOGGER.error("响应数据加密失败", e);
            throw new RuntimeException(e);
        } finally {
            LOGGER.info("响应数据加密耗时：{}ms", (System.currentTimeMillis() - start));
            if (out != null) {
                out.flush();
                out.close();
            }
        }
    }

    /**
     * 请求解密处理
     *
     * @param requestWrapper
     * @param request
     * @param aesKey
     */
    private void processDecryption(EncryptRequestWrapper requestWrapper, HttpServletRequest request, String aesKey) {
        String requestData = requestWrapper.getRequestData();

        LOGGER.info("请求解密处理的数据：" + requestData + ", aesKey=" + aesKey);
        long start = System.currentTimeMillis();

        try {
            if (!StringUtils.endsWithIgnoreCase(request.getMethod(), RequestMethod.GET.name())) {
                String decryptRequestData = AESUtil.aesDecrypt(requestData, aesKey);
                LOGGER.info("DecryptRequestData: {}", decryptRequestData);
                requestWrapper.setRequestData(decryptRequestData);
            }
        } catch (Exception e) {
            LOGGER.error("请求数据解密失败", e);
            throw new AssertionException(CommonResultCode.UNKNOWN_EXCEPTION);
        } finally {
            LOGGER.info("请求数据解密耗时：{}ms", (System.currentTimeMillis() - start));
        }
    }

    /**
     * 判断请求的uri是否需要出入参加解密
     *
     * @param encryptUriList
     * @param uri
     * @param request
     * @return
     */
    private boolean needEncryption(List<String> encryptUriList, String uri, HttpServletRequest request) {

        if (CollectionUtils.isEmpty(encryptUriList)) {
            return false;
        }
        String prefixUri = request.getMethod().toLowerCase() + ":" + uri;
        return encryptUriList.contains(prefixUri);
    }

}