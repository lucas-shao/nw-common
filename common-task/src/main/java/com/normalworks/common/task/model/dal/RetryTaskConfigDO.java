package com.normalworks.common.task.model.dal;

import java.util.Date;

/**
 * RetryTaskConfigDO
 * 重试任务配置
 *
 * @author : shaoshuai.shao
 * @version : V1.0
 * @date : 2022/11/14 11:49 AM
 */
public class RetryTaskConfigDO {

    /**
     * 重试任务标识符
     */
    private String retryTaskCode;

    /**
     * 重试任务名称
     */
    private String retryTaskName;

    /**
     * 重试任务执行间隔配置
     */
    private String taskExecuteIntervalConfig;

    /**
     * 任务状态
     */
    private String taskStatus;

    /**
     * 任务优先级
     */
    private String taskPriority;

    /**
     * 任务每次捞取可执行的最大次数
     */
    private Integer taskMaxTimesPerLoad;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date modifyTime;

    public String getRetryTaskCode() {
        return retryTaskCode;
    }

    public void setRetryTaskCode(String retryTaskCode) {
        this.retryTaskCode = retryTaskCode;
    }

    public String getRetryTaskName() {
        return retryTaskName;
    }

    public void setRetryTaskName(String retryTaskName) {
        this.retryTaskName = retryTaskName;
    }

    public String getTaskExecuteIntervalConfig() {
        return taskExecuteIntervalConfig;
    }

    public void setTaskExecuteIntervalConfig(String taskExecuteIntervalConfig) {
        this.taskExecuteIntervalConfig = taskExecuteIntervalConfig;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getTaskPriority() {
        return taskPriority == null ? null : taskPriority.trim();
    }

    public void setTaskPriority(String taskPriority) {
        this.taskPriority = (taskPriority == null) ? null : taskPriority.trim();
    }

    public Integer getTaskMaxTimesPerLoad() {
        return taskMaxTimesPerLoad;
    }

    public void setTaskMaxTimesPerLoad(Integer taskMaxTimesPerLoad) {
        this.taskMaxTimesPerLoad = taskMaxTimesPerLoad;
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
