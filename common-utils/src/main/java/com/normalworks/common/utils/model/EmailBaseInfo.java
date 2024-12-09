package com.normalworks.common.utils.model;

import java.util.Date;

/**
 * EmailBaseInfo
 * 邮件基础信息
 *
 * @author : shaoshuai.shao
 * @version : V1.0
 * @date : 2023/2/8 3:16 PM
 */
public class EmailBaseInfo extends BaseModel {

    /**
     * 邮件唯一ID
     */
    private String messageId;

    /**
     * 邮件主题
     */
    private String subject;

    /**
     * 邮件发送人地址
     */
    private String fromAddress;

    /**
     * 邮件发送时间
     */
    private Date sendDate;

    /**
     * 邮件接收到时间
     */
    private Date receivedDate;

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public Date getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(Date receivedDate) {
        this.receivedDate = receivedDate;
    }
}
