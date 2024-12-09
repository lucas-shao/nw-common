package com.normalworks.common.task.converter;

import com.normalworks.common.task.enums.TaskPriorityEnum;
import com.normalworks.common.task.enums.TaskStatusEnum;
import com.normalworks.common.task.model.RetryTaskConfig;
import com.normalworks.common.task.model.dal.RetryTaskConfigDO;

/**
 * RetryTaskConfigConverter
 *
 * @author : shaoshuai.shao
 * @version : V1.0
 * @date : 2022/11/14 11:52 AM
 */
public class RetryTaskConfigConverter {

    public static RetryTaskConfig toDomain(RetryTaskConfigDO retryTaskConfigDO) {
        if (null == retryTaskConfigDO) {
            return null;
        }
        RetryTaskConfig retryTaskConfig = new RetryTaskConfig();
        retryTaskConfig.setRetryTaskCode(retryTaskConfigDO.getRetryTaskCode());
        retryTaskConfig.setRetryTaskName(retryTaskConfigDO.getRetryTaskName());
        retryTaskConfig.setTaskStatus(TaskStatusEnum.getByValue(retryTaskConfigDO.getTaskStatus()));
        retryTaskConfig.setTaskExecuteIntervalConfig(retryTaskConfigDO.getTaskExecuteIntervalConfig());
        retryTaskConfig.setTaskPriority(TaskPriorityEnum.getByValue(retryTaskConfigDO.getTaskPriority()));
        retryTaskConfig.setTaskMaxTimesPerLoad(retryTaskConfigDO.getTaskMaxTimesPerLoad());
        retryTaskConfig.setCreateTime(retryTaskConfigDO.getCreateTime());
        retryTaskConfig.setModifyTime(retryTaskConfigDO.getModifyTime());

        return retryTaskConfig;
    }

    public static RetryTaskConfigDO toDO(RetryTaskConfig retryTaskConfig) {
        if (null == retryTaskConfig) {
            return null;
        }
        RetryTaskConfigDO retryTaskConfigDO = new RetryTaskConfigDO();
        retryTaskConfigDO.setRetryTaskCode(retryTaskConfig.getRetryTaskCode());
        retryTaskConfigDO.setRetryTaskName(retryTaskConfig.getRetryTaskName());
        retryTaskConfigDO.setTaskStatus(retryTaskConfig.getTaskStatus() != null ? retryTaskConfig.getTaskStatus().getValue() : null);
        retryTaskConfigDO.setTaskExecuteIntervalConfig(retryTaskConfig.getTaskExecuteIntervalConfig());
        retryTaskConfigDO.setTaskPriority(retryTaskConfig.getTaskPriority() != null ? retryTaskConfig.getTaskPriority().getValue() : null);
        retryTaskConfigDO.setTaskMaxTimesPerLoad(retryTaskConfig.getTaskMaxTimesPerLoad());
        retryTaskConfigDO.setCreateTime(retryTaskConfig.getCreateTime());
        retryTaskConfigDO.setModifyTime(retryTaskConfig.getModifyTime());

        return retryTaskConfigDO;
    }
}
