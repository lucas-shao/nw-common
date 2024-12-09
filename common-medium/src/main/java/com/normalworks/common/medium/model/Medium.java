package com.normalworks.common.medium.model;

import com.normalworks.common.utils.enums.HttpContentTypeEnum;
import com.normalworks.common.medium.enums.MediumStatusEnum;
import com.normalworks.common.medium.enums.MediumTypeEnum;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Medium
 * 媒体领域模型
 *
 * @author : shaoshuai.shao
 * @version : V1.0
 * @date : 2022/12/20 3:37 PM
 */
public class Medium {

    /**
     * 媒体ID
     */
    private String mediumId;

    /**
     * 媒体名称
     */
    private String mediumName;

    /**
     * 媒体类型
     */
    private MediumTypeEnum mediumType;

    /**
     * 媒体的内容摘要
     */
    private String mediumDigest;

    /**
     * 媒体的内容大小
     */
    private Long mediumSize;

    /**
     * 上传该媒体的用户ID
     */
    private String uploadUserId;

    /**
     * 媒体内容的状态
     */
    private MediumStatusEnum mediumStatus;

    /**
     * 媒体审核失败详情
     */
    private String auditFailedDetails;

    /**
     * 媒体内容类型
     */
    private HttpContentTypeEnum mediumContentType;

    /**
     * 扩展信息
     * com.normalworks.common.medium.enums.MediumExtInfoKeyEnum
     */
    private Map<String, String> extInfo = new HashMap<>();

    /**
     * 媒体创建时间
     */
    private Date createTime;

    /**
     * 媒体修改时间
     */
    private Date modifyTime;

    public String getMediumId() {
        return mediumId;
    }

    public void setMediumId(String mediumId) {
        this.mediumId = mediumId;
    }

    public String getMediumName() {
        return mediumName;
    }

    public void setMediumName(String mediumName) {
        this.mediumName = mediumName;
    }

    public MediumTypeEnum getMediumType() {
        return mediumType;
    }

    public void setMediumType(MediumTypeEnum mediumType) {
        this.mediumType = mediumType;
    }

    public String getMediumDigest() {
        return mediumDigest;
    }

    public void setMediumDigest(String mediumDigest) {
        this.mediumDigest = mediumDigest;
    }

    public Long getMediumSize() {
        return mediumSize;
    }

    public void setMediumSize(Long mediumSize) {
        this.mediumSize = mediumSize;
    }

    public String getUploadUserId() {
        return uploadUserId;
    }

    public void setUploadUserId(String uploadUserId) {
        this.uploadUserId = uploadUserId;
    }

    public MediumStatusEnum getMediumStatus() {
        return mediumStatus;
    }

    public void setMediumStatus(MediumStatusEnum mediumStatus) {
        this.mediumStatus = mediumStatus;
    }

    public String getAuditFailedDetails() {
        return auditFailedDetails;
    }

    public void setAuditFailedDetails(String auditFailedDetails) {
        this.auditFailedDetails = auditFailedDetails;
    }

    public HttpContentTypeEnum getMediumContentType() {
        return mediumContentType;
    }

    public void setMediumContentType(HttpContentTypeEnum mediumContentType) {
        this.mediumContentType = mediumContentType;
    }

    public Map<String, String> getExtInfo() {
        return extInfo;
    }

    public void setExtInfo(Map<String, String> extInfo) {
        this.extInfo = extInfo;
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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
