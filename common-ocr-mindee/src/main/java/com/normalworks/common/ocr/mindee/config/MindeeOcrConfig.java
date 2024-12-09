package com.normalworks.common.ocr.mindee.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * MindeeOcrConfig
 * mindee ocr 配置信息
 *
 * @author : shaoshuai.shao
 * @version : V1.0
 * @date : 2023/2/3 2:48 PM
 */
@Configuration
public class MindeeOcrConfig {

    @Value("${mindee.ocr.api.key}")
    private String apiKey;

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
