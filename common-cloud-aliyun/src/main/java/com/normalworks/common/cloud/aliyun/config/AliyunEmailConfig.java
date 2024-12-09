package com.normalworks.common.cloud.aliyun.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * AliyunEmailConfig
 * 阿里云邮箱发送服务配置
 *
 * @author : shaoshuai.shao
 * @version : V1.0
 * @date : 2023/1/10 5:05 PM
 */
@Configuration
public class AliyunEmailConfig {

    @Value("${aliyun.email.smtp.host}")
    private String smtpHost;

    @Value("${aliyun.email.smtp.from}")
    private String smtpFrom;

    @Value("${aliyun.email.smtp.user}")
    private String smtpUser;

    @Value("${aliyun.email.smtp.password}")
    private String smtpPassword;

    public String getSmtpHost() {
        return smtpHost;
    }

    public void setSmtpHost(String smtpHost) {
        this.smtpHost = smtpHost;
    }

    public String getSmtpFrom() {
        return smtpFrom;
    }

    public void setSmtpFrom(String smtpFrom) {
        this.smtpFrom = smtpFrom;
    }

    public String getSmtpUser() {
        return smtpUser;
    }

    public void setSmtpUser(String smtpUser) {
        this.smtpUser = smtpUser;
    }

    public String getSmtpPassword() {
        return smtpPassword;
    }

    public void setSmtpPassword(String smtpPassword) {
        this.smtpPassword = smtpPassword;
    }
}
