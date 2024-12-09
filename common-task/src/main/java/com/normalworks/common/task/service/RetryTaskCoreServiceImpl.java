package com.normalworks.common.task.service;

import com.normalworks.common.task.executor.AbstractRetryTaskExecutor;
import com.normalworks.common.task.model.RetryTask;
import com.normalworks.common.task.model.RetryTaskConfig;
import com.normalworks.common.task.repository.RetryTaskRepository;
import com.normalworks.common.utils.AssertUtil;
import com.normalworks.common.utils.ContextUtil;
import com.normalworks.common.utils.LoggerUtil;
import com.normalworks.common.utils.assertion.CommonResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

/**
 * RetryTaskCoreServiceImpl
 *
 * @author : shaoshuai.shao
 * @version : V1.0
 * @date : 2022/12/1 10:16 AM
 */
@Service
public class RetryTaskCoreServiceImpl implements RetryTaskCoreService {

    private static final Logger COMMON_TASK_LOGGER = LoggerFactory.getLogger("COMMON-TASK");

    @Resource
    private Map<String, AbstractRetryTaskExecutor> retryTaskExecutorMap;

    @Resource
    private RetryTaskRepository retryTaskRepository;

    @Override
    public RetryTask register(String retryTaskCode, String businessId, Date nextExecuteTime) {
        return register(retryTaskCode, businessId, nextExecuteTime, Collections.emptyMap());
    }

    @Override
    public RetryTask register(String retryTaskCode, String businessId, Date nextExecuteTime, Map<String, String> extInfos) {

        AbstractRetryTaskExecutor executor = retryTaskExecutorMap.get(retryTaskCode);
        AssertUtil.notNull(executor, CommonResultCode.RETRY_TASK_EXECUTOR_IS_NULL, "重试任务执行器为空，retryTaskType = " + retryTaskCode);

        // 各个重试任务执行器判断当前重试任务是否可以落DB
        if (!executor.canRegister(businessId, nextExecuteTime, extInfos)) {
            LoggerUtil.warn(COMMON_TASK_LOGGER, "不符合落重试任务的业务要求，没有落地重试任务 retryTaskType = " + retryTaskCode + " ,businessId = " + businessId + " ,nextExecuteTime = " + nextExecuteTime + " ,extInfos = " + extInfos);
            return null;
        }

        return constructAndInsert(retryTaskCode, businessId, nextExecuteTime, extInfos);
    }


    private RetryTask constructAndInsert(String retryTaskCode, String businessId, Date nextExecuteTime, Map<String, String> extInfos) {

        RetryTask retryTask = new RetryTask();
        try {
            RetryTaskConfig retryTaskConfig = new RetryTaskConfig();
            retryTaskConfig.setRetryTaskCode(retryTaskCode);

            retryTask.setRetryTaskConfig(retryTaskConfig);
            retryTask.setBusinessId(businessId);
            retryTask.setRetryTimes(0);
            retryTask.setNextExecuteTime(nextExecuteTime);

            // 将线程上下文中的traceId塞到异步任务中，方便异步任务捞起可以关联对应的traceId
            retryTask.getExtInfo().put(ContextUtil.TRACE_ID, MDC.get(ContextUtil.TRACE_ID));

            if (!CollectionUtils.isEmpty(extInfos)) {
                retryTask.getExtInfo().putAll(extInfos);
            }

            retryTaskRepository.create(retryTask);
            return retryTask;
        } catch (DuplicateKeyException e) {
            LoggerUtil.info(COMMON_TASK_LOGGER, "持久化重试任务幂等错误。retryTask=", retryTask);

            RetryTask existedRetryTask = retryTaskRepository.queryByRetryTaskCodeAndBusinessId(retryTaskCode, businessId);

            LoggerUtil.info(COMMON_TASK_LOGGER, "已存在的重试任务：existedRetryTask=", existedRetryTask);
            return existedRetryTask;
        }
    }
}
