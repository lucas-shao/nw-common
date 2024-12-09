package com.normalworks.common.email.model.dal;

import java.util.Date;

public class EmailMsgDetailDO {
    private String emailMsgId;

    private String subject;

    private String textContent;

    private String fromAddress;

    private String toAddress;

    private Date sentTime;

    private Date receivedTime;

    private String attachmentMediumList;

    private String inlineMediumList;

    private String emailFileMedium;

    private Date createTime;

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

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
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

    public String getAttachmentMediumList() {
        return attachmentMediumList;
    }

    public void setAttachmentMediumList(String attachmentMediumList) {
        this.attachmentMediumList = attachmentMediumList;
    }

    public String getInlineMediumList() {
        return inlineMediumList == null ? null : inlineMediumList.trim();
    }

    public void setInlineMediumList(String inlineMediumList) {
        this.inlineMediumList = (inlineMediumList == null) ? null : inlineMediumList.trim();
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