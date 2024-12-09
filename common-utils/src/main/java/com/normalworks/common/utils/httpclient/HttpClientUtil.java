package com.normalworks.common.utils.httpclient;

import com.normalworks.common.utils.AssertUtil;
import com.normalworks.common.utils.LoggerUtil;
import com.normalworks.common.utils.assertion.AssertionException;
import com.normalworks.common.utils.assertion.CommonResultCode;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * HttpClientUtil
 *
 * @author: lingeng
 * @date: 11/2/22
 */
public class HttpClientUtil {

    private final static Logger LOGGER = LoggerFactory.getLogger(HttpClientUtil.class);

    // 默认的超时时间，单位毫秒
    // 定义了在建立与服务器的连接之前可以经过的最长时间。如果在指定的时间内无法建立连接，则会抛出 ConnectTimeoutException。
    public final static int DEFAULT_CONNECT_TIMEOUT = 3000;

    // 默认的超时时间，单位毫秒
    // 此设置定义了在从服务器读取数据之前可以经过的最长时间。如果在指定的时间内无法读取数据，则会抛出 SocketTimeoutException。
    public final static int DEFAULT_SOCKET_TIMEOUT = 30000;

    // 默认的超时时间，单位毫秒
    // 此设置定义了在从连接池获取连接之前可以经过的最长时间。如果在指定的时间内无法获取连接，则会抛出 ConnectionPoolTimeoutException。
    public final static int DEFAULT_CONNECTION_REQUEST_TIMEOUT = 3000;

    /**
     * POST的数据是一串JSON字符串时使用
     */
    public static <T> T postJson(String url,
                                 Map<String, String> urlParams,
                                 Map<String, String> headers,
                                 String postJson,
                                 HttpRequestCallback<T> callback,
                                 int connectTimeout,
                                 int socketTimeout,
                                 int connectionRequestTimeout) {
        return doHttpRequest(HttpPost.class, url, urlParams, headers, postJson, null, null, null, callback, connectTimeout, socketTimeout, connectionRequestTimeout);
    }

    public static <T> T postJson(String url,
                                 Map<String, String> urlParams,
                                 Map<String, String> headers,
                                 String postJson,
                                 HttpRequestCallback<T> callback) {
        return postJson(url, urlParams, headers, postJson, callback, DEFAULT_CONNECT_TIMEOUT, DEFAULT_SOCKET_TIMEOUT, DEFAULT_CONNECTION_REQUEST_TIMEOUT);
    }

    /**
     * POST请求的内容数据FormData格式
     */
    public static <T> T postFormData(String url,
                                     Map<String, String> urlParams,
                                     Map<String, String> headers,
                                     Map<String, String> formTextData,
                                     Map<String, ContentBody> formFileData,
                                     HttpRequestCallback<T> callback,
                                     int connectTimeout,
                                     int socketTimeout,
                                     int connectionRequestTimeout) {
        return doHttpRequest(HttpPost.class, url, urlParams, headers, null, null, formTextData, formFileData, callback, connectTimeout, socketTimeout, connectionRequestTimeout);
    }

    public static <T> T postFormData(String url,
                                     Map<String, String> urlParams,
                                     Map<String, String> headers,
                                     Map<String, String> formTextData,
                                     Map<String, ContentBody> formFileData,
                                     HttpRequestCallback<T> callback) {
        return postFormData(url, urlParams, headers, formTextData, formFileData, callback, DEFAULT_CONNECT_TIMEOUT, DEFAULT_SOCKET_TIMEOUT, DEFAULT_CONNECTION_REQUEST_TIMEOUT);
    }

    /**
     * POST请求的内容数据x-www-form-urlencoded格式
     */
    public static <T> T postUrlencodedFormTextData(String url,
                                                   Map<String, String> urlParams,
                                                   Map<String, String> headers,
                                                   Map<String, String> urlencodedFormTextData,
                                                   HttpRequestCallback<T> callback,
                                                   int connectTimeout,
                                                   int socketTimeout,
                                                   int connectionRequestTimeout) {
        return doHttpRequest(HttpPost.class, url, urlParams, headers, null, urlencodedFormTextData, null, null, callback, connectTimeout, socketTimeout, connectionRequestTimeout);
    }

    public static <T> T postUrlencodedFormTextData(String url,
                                                   Map<String, String> urlParams,
                                                   Map<String, String> headers,
                                                   Map<String, String> urlencodedFormTextData,
                                                   HttpRequestCallback<T> callback) {
        return postUrlencodedFormTextData(url, urlParams, headers, urlencodedFormTextData, callback, DEFAULT_CONNECT_TIMEOUT, DEFAULT_SOCKET_TIMEOUT, DEFAULT_CONNECTION_REQUEST_TIMEOUT);
    }

    public static <T> T get(String url,
                            Map<String, String> urlParams,
                            Map<String, String> headers,
                            HttpRequestCallback<T> callback,
                            int connectTimeout,
                            int socketTimeout,
                            int connectionRequestTimeout) {
        return doHttpRequest(HttpGet.class, url, urlParams, headers, null, null, null, null, callback, connectTimeout, socketTimeout, connectionRequestTimeout);
    }

    public static <T> T get(String url,
                            Map<String, String> urlParams,
                            Map<String, String> headers,
                            HttpRequestCallback<T> callback) {
        return get(url, urlParams, headers, callback, DEFAULT_CONNECT_TIMEOUT, DEFAULT_SOCKET_TIMEOUT, DEFAULT_CONNECTION_REQUEST_TIMEOUT);
    }

    public static <T> T delete(String url,
                               Map<String, String> urlParams,
                               Map<String, String> headers,
                               HttpRequestCallback<T> callback,
                               int connectTimeout,
                               int socketTimeout,
                               int connectionRequestTimeout) {
        return doHttpRequest(HttpDelete.class, url, urlParams, headers, null, null, null, null, callback, connectTimeout, socketTimeout, connectionRequestTimeout);
    }

    public static <T> T delete(String url,
                               Map<String, String> urlParams,
                               Map<String, String> headers,
                               HttpRequestCallback<T> callback) {
        return delete(url, urlParams, headers, callback, DEFAULT_CONNECT_TIMEOUT, DEFAULT_SOCKET_TIMEOUT, DEFAULT_CONNECTION_REQUEST_TIMEOUT);
    }

    /**
     * 什么是patch:https://developer.mozilla.org/en-US/docs/Web/HTTP/Methods/PATCH
     */
    public static <T> T patch(String url,
                              Map<String, String> urlParams,
                              Map<String, String> headers,
                              HttpRequestCallback<T> callback,
                              int connectTimeout,
                              int socketTimeout,
                              int connectionRequestTimeout) {
        return doHttpRequest(HttpPatch.class, url, urlParams, headers, null, null, null, null, callback, connectTimeout, socketTimeout, connectionRequestTimeout);
    }

    public static <T> T patch(String url,
                              Map<String, String> urlParams,
                              Map<String, String> headers,
                              HttpRequestCallback<T> callback) {
        return patch(url, urlParams, headers, callback, DEFAULT_CONNECT_TIMEOUT, DEFAULT_SOCKET_TIMEOUT, DEFAULT_CONNECTION_REQUEST_TIMEOUT);
    }

    /**
     * http请求核心代码
     *
     * @param httpRequestClazz         Get请求 or Post请求
     * @param url                      请求的url地址
     * @param urlParams                请求的url参数
     * @param postJson                 如果请求是Post请求，请求参数类型是Json类型
     * @param urlencodedFormTextData   如果请求是Post请求，x-www-form-urlencoded格式请求参数
     * @param formTextData             form-data格式，表单文本数据
     * @param formFileData             form-data格式，表单文件数据
     * @param callback                 回调函数
     * @param connectTimeout           连接超时时间
     * @param socketTimeout            socket超时时间
     * @param connectionRequestTimeout 从连接池获取连接的超时时间
     * @return http响应返回值
     */
    private static <T> T doHttpRequest(Class httpRequestClazz,
                                       String url,
                                       Map<String, String> urlParams,
                                       Map<String, String> headers,
                                       String postJson,
                                       Map<String, String> urlencodedFormTextData,
                                       Map<String, String> formTextData,
                                       Map<String, ContentBody> formFileData,
                                       HttpRequestCallback<T> callback,
                                       int connectTimeout,
                                       int socketTimeout,
                                       int connectionRequestTimeout) {

        CloseableHttpClient httpClient = null;
        CloseableHttpResponse httpResponse = null;

        LoggerUtil.info(LOGGER, "doHttpRequest , httpRequestClazz = ", httpRequestClazz.getSimpleName(), " , url = ", url, " , urlParams = ", urlParams, " , postJson = ", postJson, " , urlencodedFormTextData = ", urlencodedFormTextData, " , formTextData = ", formTextData, " , formFileData = ", formFileData);

        try {
            // 创建Httpclient请求配置，设置对应的超时时间
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(connectTimeout)
                    .setSocketTimeout(socketTimeout)
                    .setConnectionRequestTimeout(connectionRequestTimeout)
                    .build();

            // 创建Httpclient对象
            httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig).build();

            // 创建uri
            URI uri = buildURI(url, urlParams);

            // 创建http请求
            HttpRequestBase httpRequest = createHttpRequest(httpRequestClazz, uri);

            // 设置http头
            setHeaders(httpRequest, headers);

            // 构建Post请求参数
            if ((httpRequest instanceof HttpPost)) {
                buildPostParams(httpRequest, postJson, urlencodedFormTextData, formTextData, formFileData);
            }

            httpResponse = httpClient.execute(httpRequest);

            AssertUtil.notNull(httpResponse, CommonResultCode.THIRD_PARTY_EXCEPTION, "response is null.");
            AssertUtil.notNull(httpResponse.getStatusLine(), CommonResultCode.THIRD_PARTY_EXCEPTION, "statusLine is null.");
            AssertUtil.notNull(httpResponse.getEntity(), CommonResultCode.THIRD_PARTY_EXCEPTION, "entity is null.");

            LoggerUtil.info(LOGGER, "statusCode=", httpResponse.getStatusLine().getStatusCode());

            if (callback.success(httpResponse)) {
                return callback.processSuccessResponse(httpResponse);
            } else {
                return callback.processFailureResponse(httpResponse);
            }

        } catch (AssertionException exception) {
            LoggerUtil.warn(LOGGER, "doHttpRequest 业务异常", exception);
            throw exception;
        } catch (Throwable throwable) {
            LoggerUtil.error(LOGGER, "doHttpRequest 系统异常", throwable);
            throw new AssertionException(CommonResultCode.THIRD_PARTY_EXCEPTION);
        } finally {
            try {
                // 关闭句柄
                if (httpResponse != null) {
                    httpResponse.close();
                }
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                LOGGER.error("doHttpRequest 关闭句柄，系统异常", e);
                throw new AssertionException(CommonResultCode.THIRD_PARTY_EXCEPTION);
            }
        }
    }

    /**
     * 创建Http请求
     */
    private static HttpRequestBase createHttpRequest(Class httpRequestClazz, URI uri) {
        HttpRequestBase httpRequest;
        if (httpRequestClazz == HttpPost.class) {
            httpRequest = new HttpPost(uri);
        } else if (httpRequestClazz == HttpDelete.class) {
            httpRequest = new HttpDelete(uri);
        } else if (httpRequestClazz == HttpGet.class) {
            httpRequest = new HttpGet(uri);
        } else if (httpRequestClazz == HttpPatch.class) {
            httpRequest = new HttpPatch(uri);
        } else {
            throw new UnsupportedOperationException("不支持的Http请求类型");
        }
        return httpRequest;
    }

    /**
     * 通过域名和url参数构建URI
     *
     * @param url       请求url
     * @param urlParams 请求url参数
     * @return
     * @throws URISyntaxException
     */
    private static URI buildURI(String url, Map<String, String> urlParams) throws URISyntaxException {

        URIBuilder builder = new URIBuilder(url);
        if (null != urlParams) {
            for (String key : urlParams.keySet()) {
                builder.addParameter(key, urlParams.get(key));
            }
        }
        URI uri = builder.build();
        return uri;
    }

    private static void setHeaders(HttpRequestBase httpRequest, Map<String, String> headers) {
        if (!CollectionUtils.isEmpty(headers)) {
            headers.forEach((key, value) -> httpRequest.setHeader(key, value));
        }
    }

    /**
     * 构建Post请求的参数
     *
     * @param httpRequest            http请求
     * @param postJson               json格式的Post请求
     * @param urlencodedFormTextData x-www-form-urlencoded格式，表单数据参数对形式的Post请求
     * @param formTextData           form-data格式，表单文本数据
     * @param formFileData           form-data格式，表单文件数据
     */
    private static void buildPostParams(HttpRequestBase httpRequest, String postJson, Map<String, String> urlencodedFormTextData, Map<String, String> formTextData, Map<String, ContentBody> formFileData) throws UnsupportedEncodingException {

        // 构建httpPost
        HttpPost httpPost = (HttpPost) httpRequest;

        // 优先使用postJson当做Post请求内容
        if (StringUtils.isNotBlank(postJson)) {
            StringEntity entity = new StringEntity(postJson, ContentType.APPLICATION_JSON);
            httpPost.setEntity(entity);
        } else if (!CollectionUtils.isEmpty(urlencodedFormTextData)) {
            // x-www-form-urlencoded格式的只能用来发送文本表单数据
            List<NameValuePair> paramList = new ArrayList<>();
            for (String key : urlencodedFormTextData.keySet()) {
                paramList.add(new BasicNameValuePair(key, urlencodedFormTextData.get(key)));
            }
            // 模拟表单
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList);
            httpPost.setEntity(entity);
        } else {
            // form-data格式，既可以发送表单，也可以发送文本文件
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            if (!CollectionUtils.isEmpty(formTextData)) {
                for (String key : formTextData.keySet()) {
                    builder.addTextBody(key, formTextData.get(key));
                }
            }
            if (!CollectionUtils.isEmpty(formFileData)) {
                for (String key : formFileData.keySet()) {
                    builder.addPart(key, formFileData.get(key));
                }
            }
            HttpEntity multipart = builder.build();
            httpPost.setEntity(multipart);
        }
    }
}
