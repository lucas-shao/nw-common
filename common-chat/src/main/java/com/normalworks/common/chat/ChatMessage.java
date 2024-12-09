package com.normalworks.common.chat;

import com.normalworks.common.utils.model.BaseModel;

import java.util.Date;

/**
 * 聊天消息
 */
public class ChatMessage extends BaseModel {
    /**
     * 消息id
     */
    private Long messageId;

    /**
     * requestId
     */
    private String requestId;


    /**
     * 发送者id
     */
    private String senderId;

    /**
     * 接收者id
     */
    private String receiverId;

    /**
     * 文本内容
     */
    private String textContent;

    /**
     * 话题
     * 如果两个人之间有多个会话，课使用topic来区分
     */
    private String topic;

    /**
     * 发送时间
     */
    private Date sendTime;

    /**
     * 阅读时间
     */
    private Date readTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date modifyTime;

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Date getReadTime() {
        return readTime;
    }

    public void setReadTime(Date readTime) {
        this.readTime = readTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}
