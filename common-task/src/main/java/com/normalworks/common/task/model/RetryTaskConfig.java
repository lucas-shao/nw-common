package com.normalworks.common.task.model;

import com.normalworks.common.task.enums.TaskPriorityEnum;
import com.normalworks.common.task.enums.TaskStatusEnum;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * RetryTaskConfig
 * 重试任务配置
 *
 * @author : shaoshuai.shao
 * @version : V1.0
 * @date : 2022/11/11 10:29 PM
 */
public class RetryTaskConfig {

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
    private TaskStatusEnum taskStatus;

    /**
     * 任务优先级
     */
    private TaskPriorityEnum taskPriority;

    /**
     * 每次捞取任务集合中，该任务的最大次数
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

    public TaskStatusEnum getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(TaskStatusEnum taskStatus) {
        this.taskStatus = taskStatus;
    }

    public TaskPriorityEnum getTaskPriority() {
        return taskPriority;
    }

    public void setTaskPriority(TaskPriorityEnum taskPriority) {
        this.taskPriority = taskPriority;
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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
