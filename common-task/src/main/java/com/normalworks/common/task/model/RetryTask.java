package com.normalworks.common.task.model;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * RetryTask
 * 重试任务
 *
 * @author : shaoshuai
 * @version V1.0
 * @date Date : 2021年12月07日 4:27 下午
 */
public class RetryTask {

    /**
     * 重试任务ID
     */
    private String taskId;

    /**
     * 任务类型配置
     */
    private RetryTaskConfig retryTaskConfig;

    /**
     * 任务执行业务ID
     */
    private String businessId;

    /**
     * 任务已经重试的次数
     */
    private Integer retryTimes;

    /**
     * 扩展信息
     */
    private Map<String, String> extInfo = new HashMap<>();

    /**
     * 任务下次执行时间
     */
    private Date nextExecuteTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date modifyTime;

    /**
     * 判断重试任务是否可以执行
     *
     * @return 是否可执行
     */
    public boolean canExecute() {
        return nextExecuteTime.before(new Date());
    }

    /**
     * 获取扩展信息
     *
     * @param key 扩展信息KEY
     * @return 扩展信息VALUE
     */
    public String fetchExtInfo(String key) {
        if (CollectionUtils.isEmpty(extInfo)) {
            return null;
        }
        return extInfo.get(key);
    }

    /**
     * 获取本次重试的间隔时间，单位秒
     *
     * @param retryTimes 已经重试的次数，从1开始
     * @return 下一次重试的时间间隔，单位秒
     */
    public int obtainIntervalSeconds(Integer retryTimes) {
        String[] intervalSeconds = StringUtils.split(this.retryTaskConfig.getTaskExecuteIntervalConfig(), ',');
        if (retryTimes > intervalSeconds.length) {
            return Integer.valueOf(intervalSeconds[intervalSeconds.length - 1]);
        } else {
            return Integer.valueOf(intervalSeconds[retryTimes - 1]);
        }
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public RetryTaskConfig getRetryTaskConfig() {
        return retryTaskConfig;
    }

    public void setRetryTaskConfig(RetryTaskConfig retryTaskConfig) {
        this.retryTaskConfig = retryTaskConfig;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
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

    public Map<String, String> getExtInfo() {
        return extInfo;
    }

    public void setExtInfo(Map<String, String> extInfo) {
        this.extInfo = extInfo;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
