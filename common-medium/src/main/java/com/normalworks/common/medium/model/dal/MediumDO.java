package com.normalworks.common.medium.model.dal;

import java.util.Date;

public class MediumDO {
    private Long id;

    private String mediumType;

    private String mediumDigest;

    private Long mediumSize;

    private String mediumStatus;

    private String mediumContentType;

    private String uploadUserId;

    private String mediumUrl;

    private Integer imageHeight;

    private Integer imageWidth;

    private String standardMediumUrl;

    private String blurMediumUrl;

    private String coverImageUrl;

    private Date createTime;

    private Date modifyTime;

    private String auditFailedDetails;

    private Long mediumDuration;

    private String textContent;

    private String extInfo;

    private String mediumName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMediumType() {
        return mediumType;
    }

    public void setMediumType(String mediumType) {
        this.mediumType = mediumType == null ? null : mediumType.trim();
    }

    public String getMediumDigest() {
        return mediumDigest;
    }

    public void setMediumDigest(String mediumDigest) {
        this.mediumDigest = mediumDigest == null ? null : mediumDigest.trim();
    }

    public Long getMediumSize() {
        return mediumSize;
    }

    public void setMediumSize(Long mediumSize) {
        this.mediumSize = mediumSize;
    }

    public String getMediumStatus() {
        return mediumStatus;
    }

    public void setMediumStatus(String mediumStatus) {
        this.mediumStatus = mediumStatus == null ? null : mediumStatus.trim();
    }

    public String getMediumContentType() {
        return mediumContentType;
    }

    public void setMediumContentType(String mediumContentType) {
        this.mediumContentType = mediumContentType == null ? null : mediumContentType.trim();
    }

    public String getUploadUserId() {
        return uploadUserId;
    }

    public void setUploadUserId(String uploadUserId) {
        this.uploadUserId = uploadUserId == null ? null : uploadUserId.trim();
    }

    public String getMediumUrl() {
        return mediumUrl;
    }

    public void setMediumUrl(String mediumUrl) {
        this.mediumUrl = mediumUrl == null ? null : mediumUrl.trim();
    }

    public Integer getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(Integer imageHeight) {
        this.imageHeight = imageHeight;
    }

    public Integer getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(Integer imageWidth) {
        this.imageWidth = imageWidth;
    }

    public String getStandardMediumUrl() {
        return standardMediumUrl;
    }

    public void setStandardMediumUrl(String standardMediumUrl) {
        this.standardMediumUrl = standardMediumUrl == null ? null : standardMediumUrl.trim();
    }

    public String getBlurMediumUrl() {
        return blurMediumUrl;
    }

    public void setBlurMediumUrl(String blurMediumUrl) {
        this.blurMediumUrl = blurMediumUrl == null ? null : blurMediumUrl.trim();
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl == null ? null : coverImageUrl.trim();
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

    public String getAuditFailedDetails() {
        return auditFailedDetails;
    }

    public void setAuditFailedDetails(String auditFailedDetails) {
        this.auditFailedDetails = auditFailedDetails == null ? null : auditFailedDetails.trim();
    }

    public Long getMediumDuration() {
        return mediumDuration;
    }

    public void setMediumDuration(Long mediumDuration) {
        this.mediumDuration = mediumDuration;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent == null ? null : textContent.trim();
    }

    public String getExtInfo() {
        return extInfo;
    }

    public void setExtInfo(String extInfo) {
        this.extInfo = extInfo == null ? null : extInfo.trim();
    }

    public String getMediumName() {
        return mediumName;
    }

    public void setMediumName(String mediumName) {
        this.mediumName = mediumName == null ? null : mediumName.trim();
    }
}