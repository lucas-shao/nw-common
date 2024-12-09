package com.normalworks.common.task.repository;

import com.normalworks.common.task.model.RetryTaskConfig;

/**
 * RetryTaskConfigRepository
 * 重试任务配置仓储类
 *
 * @author : shaoshuai.shao
 * @version : V1.0
 * @date : 2022/11/11 3:58 PM
 */
public interface RetryTaskConfigRepository {

    /**
     * 根据重试任务标识码查询重试任务配置
     *
     * @param retryTaskCode 重试任务标识码
     * @return 重试任务配置
     */
    RetryTaskConfig queryByCode(String retryTaskCode);
}
