package com.normalworks.common.task.converter;

import com.normalworks.common.task.model.TaskConfig;
import com.normalworks.common.task.model.dal.TaskConfigDO;

/**
 * TaskConfigConverter
 *
 * @author : shaoshuai.shao
 * @version : V1.0
 * @date : 2022/11/15 2:15 PM
 */
public class TaskConfigConverter {

    public static TaskConfig toDomain(TaskConfigDO taskConfigDO) {
        if (null == taskConfigDO) {
            return null;
        }

        TaskConfig taskConfig = new TaskConfig();
        taskConfig.setTaskType(taskConfigDO.getTaskType());
        taskConfig.setStatus(taskConfigDO.getStatus());
        taskConfig.setCreateTime(taskConfigDO.getCreateTime());
        taskConfig.setModifyTime(taskConfigDO.getModifyTime());

        return taskConfig;
    }
}
