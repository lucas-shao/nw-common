package com.normalworks.common.service.sso.google.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * GoogleSsoConfig
 * google单点登录相关配置
 *
 * @author : shaoshuai.shao
 * @version : V1.0
 * @date : 2022/11/3 8:12 PM
 */
@Configuration
public class GoogleSsoConfig {

    @Value("${google.sso.clientId}")
    private String clientId;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}
