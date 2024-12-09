package com.normalworks.common.task.repository;

import com.normalworks.common.task.model.TaskConfig;

/**
 * TaskConfigRepository
 * 任务配置仓储类
 *
 * @author : shaoshuai.shao
 * @version : V1.0
 * @date : 2022/11/14 2:59 PM
 */
public interface TaskConfigRepository {

    /**
     * 通过任务类型查询任务配置
     *
     * @param taskType 任务类型
     * @return 任务配置
     */
    TaskConfig queryByTaskType(String taskType);
}
