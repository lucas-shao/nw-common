package com.normalworks.common.task.repository;

import com.normalworks.common.task.enums.TaskPriorityEnum;
import com.normalworks.common.task.model.RetryTask;

import java.util.Date;
import java.util.List;

/**
 * RetryTaskRepository
 * 重试任务仓储类
 *
 * @author : shaoshuai.shao
 * @version : V1.0
 * @date : 2022/11/11 3:57 PM
 */
public interface RetryTaskRepository {

    /**
     * 插入重试任务
     *
     * @param retryTask 重试任务
     */
    void create(RetryTask retryTask);

    /**
     * 通过id锁定重试任务
     *
     * @param taskId 任务ID
     * @return 重试任务
     */
    RetryTask active(String taskId);

    /**
     * 通过id删除重试任务
     *
     * @param taskId 任务ID
     */
    int delete(String taskId);

    /**
     * 更新重试任务
     *
     * @param retryTask 重试任务
     */
    int update(RetryTask retryTask);

    /**
     * 批量查询重试任务
     *
     * @param batchQueryRetryTaskSize 批量查询任务的条数
     * @return 批量重试任务
     */
    List<RetryTask> queryCanExecuteRetryTaskList(TaskPriorityEnum taskPriority, Integer batchQueryRetryTaskSize, Date now);

    /**
     * 通过重试任务code和业务ID查询重试任务
     *
     * @param retryTaskCode 重试任务编码
     * @param businessId    业务ID
     * @return 重试任务
     */
    RetryTask queryByRetryTaskCodeAndBusinessId(String retryTaskCode, String businessId);
}
