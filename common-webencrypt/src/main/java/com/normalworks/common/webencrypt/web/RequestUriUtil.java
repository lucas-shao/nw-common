package com.normalworks.common.webencrypt.web;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;

/**
 * RequestUriUtil
 *
 * @author : shaoshuai
 * @version V1.0
 * @date Date : 2022年04月06日 10:54 下午
 */
public class RequestUriUtil {

    private static final String separator = "/";

    public static String getApiUri(Class<?> clz, Method method, String contextPath) {
        String methodType = "";
        StringBuilder uri = new StringBuilder();

        RequestMapping reqMapping = AnnotationUtils.findAnnotation(clz, RequestMapping.class);
        if (reqMapping != null && reqMapping.value() != null && reqMapping.value().length > 0) {
            uri.append(formatUri(reqMapping.value()[0]));
        }


        GetMapping getMapping = AnnotationUtils.findAnnotation(method, GetMapping.class);
        PostMapping postMapping = AnnotationUtils.findAnnotation(method, PostMapping.class);
        RequestMapping requestMapping = AnnotationUtils.findAnnotation(method, RequestMapping.class);
        PutMapping putMapping = AnnotationUtils.findAnnotation(method, PutMapping.class);
        DeleteMapping deleteMapping = AnnotationUtils.findAnnotation(method, DeleteMapping.class);

        if (getMapping != null && getMapping.value() != null && getMapping.value().length > 0) {
            methodType = HttpMethodTypeEnum.GET.getValue();
            uri.append(formatUri(getMapping.value()[0]));

        } else if (postMapping != null && postMapping.value() != null && postMapping.value().length > 0) {
            methodType = HttpMethodTypeEnum.POST.getValue();
            uri.append(formatUri(postMapping.value()[0]));

        } else if (putMapping != null && putMapping.value() != null && putMapping.value().length > 0) {
            methodType = HttpMethodTypeEnum.PUT.getValue();
            uri.append(formatUri(putMapping.value()[0]));

        } else if (deleteMapping != null && deleteMapping.value() != null && deleteMapping.value().length > 0) {
            methodType = HttpMethodTypeEnum.DELETE.getValue();
            uri.append(formatUri(deleteMapping.value()[0]));

        } else if (requestMapping != null && requestMapping.value() != null && requestMapping.value().length > 0) {
            RequestMethod requestMethod = RequestMethod.GET;
            if (requestMapping.method().length > 0) {
                requestMethod = requestMapping.method()[0];
            }

            methodType = requestMethod.name().toLowerCase() + ":";
            uri.append(formatUri(requestMapping.value()[0]));

        }

        if (StringUtils.hasText(contextPath) && !separator.equals(contextPath)) {
            if (contextPath.endsWith(separator)) {
                contextPath = contextPath.substring(0, contextPath.length() - 1);
            }
            return methodType + contextPath + uri;
        }

        return methodType + uri;
    }

    private static String formatUri(String uri) {
        if (uri.startsWith(separator)) {
            return uri;
        }
        return separator + uri;
    }
}
