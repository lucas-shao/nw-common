package com.normalworks.common.task.model.dal;

import java.util.Date;

/**
 * RetryTaskDO
 * 重试任务DO
 *
 * @author : shaoshuai.shao
 * @version : V1.0
 * @date : 2022/11/14 11:33 AM
 */
public class RetryTaskDO {

    private Long id;

    private String retryTaskCode;

    private String businessId;

    private Integer retryTimes;

    private String extInfo;

    private Date nextExecuteTime;

    private Date createTime;

    private Date modifyTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRetryTaskCode() {
        return retryTaskCode;
    }

    public void setRetryTaskCode(String retryTaskCode) {
        this.retryTaskCode = retryTaskCode == null ? null : retryTaskCode.trim();
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId == null ? null : businessId.trim();
    }

    public Integer getRetryTimes() {
        return retryTimes;
    }

    public void setRetryTimes(Integer retryTimes) {
        this.retryTimes = retryTimes;
    }

    public Date getNextExecuteTime() {
        return nextExecuteTime;
    }

    public void setNextExecuteTime(Date nextExecuteTime) {
        this.nextExecuteTime = nextExecuteTime;
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

    public String getExtInfo() {
        return extInfo;
    }

    public void setExtInfo(String extInfo) {
        this.extInfo = extInfo;
    }
}
