package com.normalworks.common.webencrypt.web;

import com.normalworks.common.utils.LoggerUtil;
import com.normalworks.common.utils.AssertUtil;
import com.normalworks.common.utils.assertion.CommonResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ApiEncryptDataInit
 * Controller请求参数和返回结果做数据加解密的加载初始化
 * <p>
 * 需要加解密的Controller需要在两个地方添加@WebEncryptConfig注解
 * * Controller类添加注解
 * * 所有添加了RequestMapping（包括PostMapping等注解）的服务方法都需要添加@WebEncryptConfig注解
 * <p>
 * ApiEncryptDataInit会基于WebEncryptConfig注解，将注解中配置需要加密的URI都保存一份数据副本，在系统运行时用于判断是否需要进行加解密操作
 *
 * @author : shaoshuai
 * @version V1.0
 * @date Date : 2022年04月06日 10:19 下午
 */
@Component
public class ApiEncryptDataInit implements ApplicationContextAware {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiEncryptDataInit.class);

    /**
     * 需要对请求内容进行解密的接口URI
     */
    private static List<String> requestDecryptUriList = new ArrayList<>();

    /**
     * 需要对响应内容进行加密的接口URI
     */
    private static List<String> responseEncryptUriList = new ArrayList<>();

    /**
     * 上下文路径
     */
    private String contextPath;

    public static List<String> getRequestDecryptUriList() {
        return requestDecryptUriList;
    }

    public static void setRequestDecryptUriList(List<String> requestDecryptUriList) {
        ApiEncryptDataInit.requestDecryptUriList = requestDecryptUriList;
    }

    public static List<String> getResponseEncryptUriList() {
        return responseEncryptUriList;
    }

    public static void setResponseEncryptUriList(List<String> responseEncryptUriList) {
        ApiEncryptDataInit.responseEncryptUriList = responseEncryptUriList;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        LOGGER.info("开始-读取加载Controller请求参数和返回结果的数据加解密注解");

        this.contextPath = applicationContext.getEnvironment().getProperty("server.servlet.context-path");
        Map<String, Object> beanMap = applicationContext.getBeansWithAnnotation(WebEncryptConfig.class);
        initApiEncryptConfig(beanMap);

        LOGGER.info("完毕-读取加载Controller请求参数和返回结果的数据加解密注解");
    }

    /**
     * 通过注解初始化API的加解密配置
     *
     * @param beanMap
     */
    private void initApiEncryptConfig(Map<String, Object> beanMap) {

        if (CollectionUtils.isEmpty(beanMap)) {
            return;
        }

        for (Object bean : beanMap.values()) {

            Class<?> clz = bean.getClass();
            Method[] methods = clz.getMethods();

            for (Method method : methods) {

                if (AnnotationUtils.findAnnotation(method, RequestMapping.class) == null) {
                    LoggerUtil.info(LOGGER, "非web请求类服务，跳过。method=", method);
                    continue;
                }

                WebEncryptConfig config = AnnotationUtils.findAnnotation(method, WebEncryptConfig.class);
                AssertUtil.notNull(config, CommonResultCode.UNKNOWN_EXCEPTION, "web请求类方法必须有WebEncryptConfig注解。method=", method);

                if (config.encrypt()) {
                    String uri = RequestUriUtil.getApiUri(clz, method, contextPath);
                    LOGGER.info("Encrypt URI: {}", uri);
                    responseEncryptUriList.add(uri);
                }
                if (config.decrypt()) {
                    String uri = RequestUriUtil.getApiUri(clz, method, contextPath);
                    LOGGER.info("Decrypt URI: {}", uri);
                    requestDecryptUriList.add(uri);
                }
            }
        }
    }
}
