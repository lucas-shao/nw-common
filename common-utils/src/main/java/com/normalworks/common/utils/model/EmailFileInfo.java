package com.normalworks.common.utils.model;

/**
 * EmailFileInfo
 * 邮件关联文件信息（附件文件/inline正文文件）
 *
 * @author : shaoshuai.shao
 * @version : V1.0
 * @date : 2023/2/8 3:14 PM
 */
public class EmailFileInfo extends BaseModel {

    /**
     * 原始文件名
     */
    private String originalFileName;

    /**
     * 文件的contentType : application/pdf
     */
    private String contentType;

    /**
     * 文件在本地的保存地址
     */
    private String localSavedPath;

    /**
     * 缩略图在本地的保存地址
     * PDF即为第一页的缩略图
     */
    private String thumbnailLocalSavedPath;

    /**
     * 邮件体类型（Part.ATTACHMENT / Part.INLINE）
     */
    private String bodyPartType;

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getLocalSavedPath() {
        return localSavedPath;
    }

    public void setLocalSavedPath(String localSavedPath) {
        this.localSavedPath = localSavedPath;
    }

    public String getBodyPartType() {
        return bodyPartType;
    }

    public void setBodyPartType(String bodyPartType) {
        this.bodyPartType = bodyPartType;
    }

    public String getThumbnailLocalSavedPath() {
        return thumbnailLocalSavedPath == null ? null : thumbnailLocalSavedPath.trim();
    }

    public void setThumbnailLocalSavedPath(String thumbnailLocalSavedPath) {
        this.thumbnailLocalSavedPath = (thumbnailLocalSavedPath == null) ? null : thumbnailLocalSavedPath.trim();
    }
}
