package com.normalworks.common.utils.model;

import java.util.List;

/**
 * EmailMessageInfo
 * 邮箱邮件内容完整信息
 *
 * @author : shaoshuai.shao
 * @version : V1.0
 * @date : 2023/2/20 4:03 PM
 */
public class EmailMessageInfo extends BaseModel {

    /**
     * 邮件基础信息
     */
    private EmailBaseInfo emailBaseInfo;

    /**
     * 邮件正文纯文本信息
     */
    private String contextText;

    /**
     * 邮件附件文件列表
     */
    private List<EmailFileInfo> attachmentFileInfoList;

    /**
     * 包含在邮件正文中的文件列表
     */
    private List<EmailFileInfo> inlineFileInfoList;

    /**
     * 该邮件下载本地保存路径
     * 保存为EML文件
     */
    private String emailFileLocalSavedPath;

    public EmailBaseInfo getEmailBaseInfo() {
        return emailBaseInfo;
    }

    public void setEmailBaseInfo(EmailBaseInfo emailBaseInfo) {
        this.emailBaseInfo = emailBaseInfo;
    }

    public String getContextText() {
        return contextText;
    }

    public void setContextText(String contextText) {
        this.contextText = contextText;
    }

    public List<EmailFileInfo> getAttachmentFileInfoList() {
        return attachmentFileInfoList;
    }

    public void setAttachmentFileInfoList(List<EmailFileInfo> attachmentFileInfoList) {
        this.attachmentFileInfoList = attachmentFileInfoList;
    }

    public List<EmailFileInfo> getInlineFileInfoList() {
        return inlineFileInfoList;
    }

    public void setInlineFileInfoList(List<EmailFileInfo> inlineFileInfoList) {
        this.inlineFileInfoList = inlineFileInfoList;
    }

    public String getEmailFileLocalSavedPath() {
        return emailFileLocalSavedPath == null ? null : emailFileLocalSavedPath.trim();
    }

    public void setEmailFileLocalSavedPath(String emailFileLocalSavedPath) {
        this.emailFileLocalSavedPath = (emailFileLocalSavedPath == null) ? null : emailFileLocalSavedPath.trim();
    }
}
