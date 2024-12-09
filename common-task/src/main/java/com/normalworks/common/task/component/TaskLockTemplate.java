package com.normalworks.common.task.component;

import com.normalworks.common.task.client.DistributedLockClient;
import com.normalworks.common.task.config.TaskIntegrationConfig;
import com.normalworks.common.task.enums.TaskStatusEnum;
import com.normalworks.common.task.model.TaskConfig;
import com.normalworks.common.task.repository.TaskConfigRepository;
import com.normalworks.common.utils.ContextUtil;
import com.normalworks.common.utils.LoggerUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * TaskLockTemplate
 * 任务锁定模版
 *
 * @author: lingeng
 * @date: 3/28/22
 */
@Service
public class TaskLockTemplate {

    private static final Logger COMMON_TASK_LOGGER = LoggerFactory.getLogger("COMMON-TASK");

    /**
     * 任务在缓存的前缀
     */
    public static final String TASK_CACHE_PREFIX = "TASK_";

    @Resource
    private DistributedLockClient distributedLockClient;

    @Resource
    private TaskConfigRepository taskConfigRepository;

    @Resource
    private TaskIntegrationConfig taskIntegrationConfig;

    /**
     * 锁定并执行
     *
     * @param taskType 任务类型
     * @param timeout  超时时间 秒
     * @param callback 回调
     */
    public void lockThenExecute(String taskType, long timeout, TaskCallback callback) {

        //不执行所有任务，一般是在本地跑测试用例时配置为不执行
        if (!taskIntegrationConfig.executeRetryTask()) {
            LoggerUtil.error("触发重试任务的定时任务已关闭。");
            return;
        }

        //如果该任务配置为暂停，则不处理。一般线上应急时才会配置为暂停
        if (taskStopped(taskType)) {
            return;
        }

        boolean obtainLock = false;

        try {
            // 上下文初始化
            ContextUtil.init();

            obtainLock = distributedLockClient.lock(taskIntegrationConfig.getCachePrefix() + TASK_CACHE_PREFIX + taskType, timeout);

            if (!obtainLock) {
                COMMON_TASK_LOGGER.info(taskType + "锁失败。");
                return;
            }

            callback.execute();

            COMMON_TASK_LOGGER.info(taskType + "执行完成。");

        } catch (Throwable ex) {
            COMMON_TASK_LOGGER.error(taskType + "执行异常", ex);
        } finally {

            // 清除线程上下文
            ContextUtil.clear();

            // 清除日志上下文
            MDC.clear();

            if (obtainLock) {
                distributedLockClient.releaseLock(taskIntegrationConfig.getCachePrefix() + TASK_CACHE_PREFIX + taskType);
            }
        }

    }

    private boolean taskStopped(String taskType) {

        TaskConfig taskConfig = taskConfigRepository.queryByTaskType(taskType);
        if (taskConfig != null && StringUtils.isNotBlank(taskConfig.getStatus())) {

            //暂停执行是极端异常行为，所以先打印error日志，避免误配置没有被发现
            if (TaskStatusEnum.getByValue(taskConfig.getStatus()) == TaskStatusEnum.STOP) {
                COMMON_TASK_LOGGER.warn("定时任务暂停执行：" + taskType);
                return true;
            }
        }
        return false;
    }
}
