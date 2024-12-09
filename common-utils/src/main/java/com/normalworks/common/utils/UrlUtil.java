package com.normalworks.common.utils;

import com.normalworks.common.utils.assertion.AssertionException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.*;

/**
 * UrlUtil
 * URL工具类
 *
 * @author : shaoshuai
 * @version V1.0
 * @date Date : 2021年11月21日 2:39 下午
 */
public class UrlUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(UrlUtil.class);

    // http连接超时设置
    public static final int HTTP_CONNECTION_TIME_OUT = 3 * 1000;

    /**
     * 获取URL的文件后缀名
     *
     * @param urlName url
     * @return 后缀名
     */
    public static String fetchFileExtension(String urlName) {

        if (StringUtils.isBlank(urlName)) {
            return null;
        }
        String fileName = StringUtils.substringAfterLast(urlName, "/");
        String extension = StringUtils.substringAfterLast(fileName, ".");

        return StringUtils.trim(extension);
    }

    /**
     * 获取URL的文件名
     *
     * @param urlName url
     * @return 文件名
     */
    public static String fetchFileName(String urlName) {

        if (StringUtils.isBlank(urlName)) {
            return null;
        }
        String fileName = StringUtils.substringAfterLast(urlName, "/");
        return fileName;
    }

    /**
     * 校验url的有效性
     *
     * @param urlName url字符串
     * @return 有效性判断
     */
    public static boolean validateUrl(String urlName) {

        if (StringUtils.isBlank(urlName)) {
            return false;
        }

        try {
            URI uri = toURI(urlName);
            return uri != null;
        } catch (Exception exception) {
            LOGGER.warn("URL格式校验不合法，urlName = " + urlName);
            return false;
        }
    }

    /**
     * 将字符串url转为URI，间接可以作为URL的有效性判断
     *
     * @param urlName 字符串Url
     * @return URI
     * @throws URISyntaxException
     */
    public static URI toURI(String urlName) {

        URI uri = null;
        try {
            uri = new URI(urlName);
        } catch (URISyntaxException e) {
            LOGGER.warn("Url字符串不合法，urlName = " + urlName, e);
            return null;
        }

        return uri;
    }

    /**
     * 通过url域名返回HttpURLConnection
     *
     * @param urlName 域名
     * @return HttpURLConnection
     * @throws IOException
     * @throws URISyntaxException
     */
    public static HttpURLConnection obtainHttpURLConnection(String urlName) throws IOException, AssertionException {

        // urlName有效性判断
        URI uri = toURI(urlName);

        // new一个URL对象
        URL url = new URL(urlName);

        // 创建HttpURLConnection
        HttpURLConnection httpURLConnection = null;
        if (uri.getScheme().equalsIgnoreCase("http")) {
            // http请求
            httpURLConnection = (HttpURLConnection) url.openConnection();
        } else {
            // https请求
            httpURLConnection = (HttpsURLConnection) url.openConnection();
        }

        // 伪装http的请求，避免对方服务器返回403，不让抓取
        httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.setConnectTimeout(HTTP_CONNECTION_TIME_OUT);

        return httpURLConnection;
    }

    /**
     * url encode param
     *
     * @param rawParam 原始的param
     * @return encode后的param
     */
    public static String encodeUrlParam(String rawParam) {

        try {
            String param = URLEncoder.encode(rawParam, "UTF-8");
            return param;
        } catch (Exception exception) {
            LOGGER.error("URL encode 出现异常 rawParam = " + rawParam, exception);
            return null;
        }
    }

    /**
     * 将小磁场h5 url链接转为微信小程序的链接
     *
     * @param h5Url 原始的小磁场h5 url的链接
     * @return 微信小程序的链接
     */
    public static String convertToWeChatMiniProgramUrl(String h5Url) {

        try {
            return "/pages/index/index?url=" + URLEncoder.encode(h5Url, "UTF-8");
        } catch (Exception exception) {
            LOGGER.error("转化为微信小程序链接出现异常 h5Url = " + h5Url, exception);
            return null;
        }
    }

    public static void main(String[] args) {
        String url = "https://www.baidu.com/234.png";
        System.out.println(fetchFileName(url));
    }
}
