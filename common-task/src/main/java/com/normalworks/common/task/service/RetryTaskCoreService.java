package com.normalworks.common.task.service;

import com.normalworks.common.task.model.RetryTask;

import java.util.Date;
import java.util.Map;

/**
 * RetryTaskCoreService
 * 重试任务核心领域服务
 *
 * @author : shaoshuai.shao
 * @version : V1.0
 * @date : 2022/12/1 10:14 AM
 */
public interface RetryTaskCoreService {

    /**
     * 注册重试任务
     *
     * @param retryTaskCode   注册的重试任务类型
     * @param businessId      注册的重试任务业务ID
     * @param nextExecuteTime 重试任务下一次执行时间
     * @return
     */
    RetryTask register(String retryTaskCode, String businessId, Date nextExecuteTime);

    /**
     * 注册重试任务，带参数
     *
     * @param retryTaskCode   注册的重试任务类型
     * @param businessId      注册的重试任务业务ID
     * @param nextExecuteTime 重试任务下一次执行时间
     * @param extInfos        参数
     * @return
     */
    RetryTask register(String retryTaskCode, String businessId, Date nextExecuteTime, Map<String, String> extInfos);

}
