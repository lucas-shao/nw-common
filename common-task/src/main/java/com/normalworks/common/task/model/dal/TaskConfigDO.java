package com.normalworks.common.task.model.dal;

import java.util.Date;

/**
 * TaskConfigDO
 *
 * @author : shaoshuai.shao
 * @version : V1.0
 * @date : 2022/11/15 2:13 PM
 */
public class TaskConfigDO {

    private Long id;

    /**
     * 任务类型
     */
    private String taskType;

    /**
     * 任务状态
     */
    private String status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date modifyTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
