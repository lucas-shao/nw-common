package com.normalworks.common.webencrypt.record;

import com.normalworks.common.utils.LoggerUtil;
import com.normalworks.common.webencrypt.util.AESUtil;
import com.normalworks.common.webencrypt.web.EncryptConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * ParamEncryptService
 *
 * @author: lingeng
 * @date: 12/20/22
 */
@Service
public class ParamEncryptService {

    private final static Logger LOGGER = LoggerFactory.getLogger(ParamEncryptService.class);
    private final static String PARAM_AES_KEY_ID = "nw_k";

    @Resource
    private EncryptConfig encryptConfig;

    @Resource
    private EncryptConfigRecordService encryptConfigRecordService;


    /**
     * 根据应用设置的加密配置参数，如果是debug模式，则不做加密，不拼接秘钥，直接拼接业务参数后返回
     * 如果非debug模式，对参数进行加密，拼接成url
     * 参数字符串返回： a=123&b=345&nw_k=keyId。注意：开头没有&符号，调用方按需拼接。
     */
    public String encryptParams(Map<String, String> params, Boolean urlEncode) {

        if (params.isEmpty()) {
            return "";
        }

        StringBuilder sb = new StringBuilder();

        EncryptConfigRecord record = encryptConfigRecordService.fetchAesKey();

        boolean firstParam = true;
        for (String key : params.keySet()) {
            if (!firstParam) {
                sb.append("&");
            } else {
                firstParam = false;
            }
            String encryptParam = encryptParam(record, params.get(key));

            if (urlEncode) {
                encryptParam = URLEncoder.encode(encryptParam, StandardCharsets.UTF_8);
            }

            sb.append(key).append("=").append(encryptParam);
        }

        if (!encryptConfig.isDebug()) {
            sb.append("&").append(PARAM_AES_KEY_ID).append("=").append(record.getAesKeyId());
        }

        return sb.toString();
    }

    /**
     * 组装秘钥信息
     */
    private String appendAesKey(EncryptConfigRecord apiDataEncryptConfigRecord) {
        return PARAM_AES_KEY_ID + "=" + apiDataEncryptConfigRecord.getAesKeyId();
    }

    private String encryptParam(EncryptConfigRecord record, String paramValue) {
        if (record == null) {
            LoggerUtil.error(LOGGER, "获取加解密秘钥失败，无法对URL参数进行加密。");
            return paramValue;
        }

        if (encryptConfig.isDebug()) {
            return paramValue;
        } else {
            return AESUtil.aesEncrypt(paramValue, record.getAesKey());
        }
    }

}
