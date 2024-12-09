package com.normalworks.common.cloud.aws.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * AwsCommonConfig
 *
 * @author : shaoshuai.shao
 * @version : V1.0
 * @date : 2022/10/17 8:33 PM
 */
@Configuration
public class AwsCommonConfig {

    @Value("${aws.common.accessKeyId}")
    private String accessKeyId;

    @Value("${aws.common.accessKeySecret}")
    private String accessKeySecret;

    @Value("${aws.common.region}")
    private String region;

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
