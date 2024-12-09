package com.normalworks.common.webencrypt.web;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * IpUtil
 * IP地址工具类
 *
 * @author : shaoshuai
 * @version V1.0
 * @date Date : 2021年10月31日 8:41 下午
 */
public class IpUtil {

    /**
     * 获取真正的请求IP地址
     *
     * @param request http请求
     * @return 真实IP地址
     */
    public static String getIpAddress(HttpServletRequest request) {

        String ipAddress = null;
        try {
            ipAddress = request.getHeader("x-forwarded-for");
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
                if (ipAddress.equals("127.0.0.1")) {
                    // 根据网卡取本机配置的IP
                    try {
                        ipAddress = InetAddress.getLocalHost().getHostAddress();
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                }
            }
            // 通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            if (ipAddress != null) {
                if (ipAddress.contains(",")) {
                    return ipAddress.split(",")[0];
                } else {
                    return ipAddress;
                }
            } else {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
