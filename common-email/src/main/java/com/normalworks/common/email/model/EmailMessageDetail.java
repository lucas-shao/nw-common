package com.normalworks.common.email.model;

import com.normalworks.common.utils.model.BaseModel;

import java.util.Date;
import java.util.List;

/**
 * EmailMessageDetail
 * 邮件内容详情
 *
 * @author : shaoshuai.shao
 * @version : V1.0
 * @date : 2023/2/20 8:27 PM
 */
public class EmailMessageDetail extends BaseModel {

    /**
     * 邮件内容唯一ID
     */
    private String emailMsgId;

    /**
     * 邮件主题
     */
    private String subject;

    /**
     * 邮件正文纯文本内容
     */
    private String contentText;

    /**
     * 邮件发送人地址
     */
    private String fromAddress;

    /**
     * 邮件收件人地址
     */
    private String toAddress;

    /**
     * 邮件发送时间
     */
    private Date sentTime;

    /**
     * 邮件接受时间
     */
    private Date receivedTime;

    /**
     * 邮件附件的媒体列表
     */
    private List<String> attachmentMediumList;

    /**
     * 邮件正文中内嵌的媒体列表
     */
    private List<String> inlineMediumList;

    /**
     * 下载邮件存储的媒体文件
     * 将整个邮件保存为EML文件存储格式
     */
    private String emailFileMedium;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date modifyTime;

    public String getEmailMsgId() {
        return emailMsgId;
    }

    public void setEmailMsgId(String emailMsgId) {
        this.emailMsgId = emailMsgId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContentText() {
        return contentText;
    }

    public void setContentText(String contentText) {
        this.contentText = contentText;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public Date getSentTime() {
        return sentTime;
    }

    public void setSentTime(Date sentTime) {
        this.sentTime = sentTime;
    }

    public Date getReceivedTime() {
        return receivedTime;
    }

    public void setReceivedTime(Date receivedTime) {
        this.receivedTime = receivedTime;
    }

    public List<String> getAttachmentMediumList() {
        return attachmentMediumList;
    }

    public void setAttachmentMediumList(List<String> attachmentMediumList) {
        this.attachmentMediumList = attachmentMediumList;
    }

    public List<String> getInlineMediumList() {
        return inlineMediumList;
    }

    public void setInlineMediumList(List<String> inlineMediumList) {
        this.inlineMediumList = inlineMediumList;
    }

    public String getEmailFileMedium() {
        return emailFileMedium == null ? null : emailFileMedium.trim();
    }

    public void setEmailFileMedium(String emailFileMedium) {
        this.emailFileMedium = (emailFileMedium == null) ? null : emailFileMedium.trim();
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
