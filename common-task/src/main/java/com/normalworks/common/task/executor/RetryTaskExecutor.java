package com.normalworks.common.task.executor;

import com.normalworks.common.task.model.RetryTask;

import java.util.Date;
import java.util.Map;

/**
 * RetryTaskExecutor
 * 重试任务执行器
 *
 * @author : shaoshuai
 * @version V1.0
 * @date Date : 2021年11月07日 9:51 下午
 */
public interface RetryTaskExecutor {

    /**
     * 重试任务执行器
     *
     * @param retryTask 重试任务
     */
    void execute(RetryTask retryTask);

    /**
     * 是否满足重试任务注册条件
     *
     * @param businessId      业务ID
     * @param nextExecuteTime 下次执行时间
     * @param extInfos        业务扩展参数
     * @return 是否可注册
     */
    boolean canRegister(String businessId, Date nextExecuteTime, Map<String, String> extInfos);
}
