package com.normalworks.common.login.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * LoginConfig
 * 登录配置项
 *
 * @author : shaoshuai.shao
 * @version : V1.0
 * @date : 2022/11/17 9:05 PM
 */
@Configuration
public class LoginConfig {

    /**
     * 登录token的前缀
     * 一般都是以系统+场景开头: PINVO_LOGIN_
     */
    @Value("${login.token.prefix}")
    private String tokenPrefix;

    /**
     * 登录token的有效期，单位分钟
     */
    @Value("${login.token.expire}")
    private long tokenExpire;

    /**
     * 存储在客户端的token的KEY名称
     */
    @Value("${login.token.key}")
    private String tokenKey;

    /**
     * token的签名密钥
     */
    @Value("${login.token.secret}")
    private String tokenSecret;

    /**
     * 默认的未登录用户身份
     */
    @Value("${login.notLoginUserId}")
    private String notLoginUserId;

    /**
     * 登录OTP的有效期，单位分钟
     */
    @Value("${login.otp.expire}")
    private long loginOtpExpire;

    public String getTokenPrefix() {
        return tokenPrefix;
    }

    public void setTokenPrefix(String tokenPrefix) {
        this.tokenPrefix = tokenPrefix;
    }

    public long getTokenExpire() {
        return tokenExpire;
    }

    public void setTokenExpire(long tokenExpire) {
        this.tokenExpire = tokenExpire;
    }

    public String getTokenKey() {
        return tokenKey;
    }

    public void setTokenKey(String tokenKey) {
        this.tokenKey = tokenKey;
    }

    public String getTokenSecret() {
        return tokenSecret;
    }

    public void setTokenSecret(String tokenSecret) {
        this.tokenSecret = tokenSecret;
    }

    public String getNotLoginUserId() {
        return notLoginUserId;
    }

    public void setNotLoginUserId(String notLoginUserId) {
        this.notLoginUserId = notLoginUserId;
    }

    public long getLoginOtpExpire() {
        return loginOtpExpire;
    }

    public void setLoginOtpExpire(long loginOtpExpire) {
        this.loginOtpExpire = loginOtpExpire;
    }
}
