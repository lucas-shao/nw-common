package com.normalworks.common.utils.httpclient;

import org.apache.http.HttpResponse;

import java.io.IOException;

/**
 * HttpRequestCallback
 *
 * @author: lingeng
 * @date: 11/2/22
 */
public interface HttpRequestCallback<T> {

    /**
     * 是否是一次成功响应
     */
    boolean success(HttpResponse response);

    /**
     * 成功响应的处理逻辑
     */
    T processSuccessResponse(HttpResponse response) throws IOException;

    /**
     * 失败响应的处理逻辑
     */
    T processFailureResponse(HttpResponse response) throws IOException;
}
