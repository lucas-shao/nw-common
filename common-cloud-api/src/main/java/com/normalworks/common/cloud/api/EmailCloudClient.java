package com.normalworks.common.cloud.api;

import java.util.List;

/**
 * EmailCloudClient
 * 邮件云服务
 *
 * @author : shaoshuai.shao
 * @version : V1.0
 * @date : 2022/11/1 10:28 PM
 */
public interface EmailCloudClient {

    /**
     * 发送邮件服务
     *
     * @param fromAddr    来源的邮箱地址
     * @param fromAlias   来源的邮箱别名（可空）
     * @param toAddr      目的的邮箱地址
     * @param replyToAddr 回复的邮箱地址（可空）
     * @param subject     邮件主题
     * @param htmlBody    邮件主体HTML
     * @param textBody    邮件主体文字（可空）
     * @return 发送的messageId
     */
    String sendEmail(String fromAddr, String fromAlias, String toAddr, String replyToAddr, String subject, String htmlBody, String textBody);

    /**
     * 发送邮件服务
     *
     * @param fromAddr    来源的邮箱地址
     * @param fromAlias   来源的邮箱别名（可空）
     * @param toAddr      目的的邮箱地址
     * @param replyToAddr 回复的邮箱地址（可空）
     * @param ccAddrs
     * @param bccAddr     密送的邮箱地址（可空）
     * @param subject     邮件主题
     * @param htmlBody    邮件主体HTML
     * @param textBody    邮件主体文字（可空）
     * @return 发送的messageId
     */
    String sendEmail(String fromAddr, String fromAlias, String toAddr, String replyToAddr, List<String> ccAddrs, List<String> bccAddr, String subject, String htmlBody, String textBody);
}
