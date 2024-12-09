package com.normalworks.common.cloud.aws.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * AwsS3Config
 *
 * @author : shaoshuai.shao
 * @version : V1.0
 * @date : 2022/10/17 8:39 PM
 */
@Configuration
public class AwsS3Config {

    @Value("${aws.s3.bucketName}")
    private String bucketName;

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }
}
