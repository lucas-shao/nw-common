package com.normalworks.common.task;

import com.normalworks.common.task.component.TaskCallback;
import com.normalworks.common.task.component.TaskLockTemplate;
import com.normalworks.common.task.config.TaskIntegrationConfig;
import com.normalworks.common.task.enums.TaskPriorityEnum;
import com.normalworks.common.task.executor.AbstractRetryTaskExecutor;
import com.normalworks.common.task.model.RetryTask;
import com.normalworks.common.task.repository.RetryTaskRepository;
import com.normalworks.common.utils.LoggerUtil;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * ScheduledRetryTask
 * 小磁场重试任务执行总入口
 *
 * @author : shaoshuai
 * @version V1.0
 * @date Date : 2021年11月07日 9:40 下午
 */
@Component
public class ScheduledRetryTask {

    private static final Logger COMMON_TASK_LOGGER = LoggerFactory.getLogger("COMMON-TASK");

    @Resource
    private Map<String, AbstractRetryTaskExecutor> retryTaskExecutorMap;

    @Resource
    private RetryTaskRepository retryTaskRepository;

    @Resource
    private TaskLockTemplate taskLockTemplate;

    @Resource
    private TaskIntegrationConfig taskIntegrationConfig;

    /**
     * 每隔5秒钟执行一次
     */
    @Scheduled(cron = "0/5 * * * * ?")
    public void scheduledTask() {

        COMMON_TASK_LOGGER.info("ScheduledRetryTask Start");

        //虽然在模板方法中有同一个判断关闭的逻辑，但考虑到这个任务执行频率比较高，所以在这里也加上了判断
        if (!taskIntegrationConfig.executeRetryTask()) {
            LoggerUtil.error("触发重试任务的定时任务已关闭。");
            return;
        }

        int BATCH_QUERY_RETRY_TASK_SIZE = taskIntegrationConfig.getBatchLoadRetryTaskSize();

        taskLockTemplate.lockThenExecute("RETRY_TASK", 3 * BATCH_QUERY_RETRY_TASK_SIZE, new TaskCallback() {
            @Override
            public void execute() {
                if (CollectionUtils.isEmpty(retryTaskExecutorMap)) {
                    COMMON_TASK_LOGGER.error("重试任务执行器集合retryTaskExecutorMap为空");
                    return;
                }

                List<RetryTask> retryTaskList = queryRetryTaskList(BATCH_QUERY_RETRY_TASK_SIZE, new Date());

                // 过滤掉超过执行次数的任务
                retryTaskList = filterRetryTaskList(retryTaskList);

                if (CollectionUtils.isEmpty(retryTaskList)) {
                    COMMON_TASK_LOGGER.info("ScheduledRetryTask No Data");
                    return;
                }

                for (RetryTask retryTask : retryTaskList) {

                    if (null == retryTask.getRetryTaskConfig()) {
                        COMMON_TASK_LOGGER.error("重试任务类型不存在，retryTask = " + retryTask);
                        continue;
                    }
                    AbstractRetryTaskExecutor executor = retryTaskExecutorMap.get(retryTask.getRetryTaskConfig().getRetryTaskCode());
                    if (null == executor) {
                        COMMON_TASK_LOGGER.error("重试任务执行器不存在，retryTask = " + retryTask);
                        continue;
                    }
                    // 执行重试任务
                    executor.execute(retryTask);
                }
            }
        });
    }

    private List<RetryTask> filterRetryTaskList(List<RetryTask> retryTaskList) {

        if (CollectionUtils.isEmpty(retryTaskList)) {
            return retryTaskList;
        }

        List<RetryTask> filteredRetryTaskList = new ArrayList<>();
        Map<String, Integer> retryTaskCountMap = new HashMap<>();
        for (RetryTask retryTask : retryTaskList) {
            String retryTaskCode = retryTask.getRetryTaskConfig().getRetryTaskCode();
            if (!retryTaskCountMap.containsKey(retryTaskCode)) {
                retryTaskCountMap.put(retryTaskCode, 1);
                filteredRetryTaskList.add(retryTask);
            } else {
                Integer retryTaskCount = retryTaskCountMap.get(retryTaskCode);
                if (retryTaskCount < retryTask.getRetryTaskConfig().getTaskMaxTimesPerLoad()) {
                    retryTaskCountMap.put(retryTaskCode, retryTaskCount + 1);
                    filteredRetryTaskList.add(retryTask);
                } else {
                    // 超过执行次数，nextExecuteTime设置向后退5S
                    retryTask.setNextExecuteTime(DateUtils.addSeconds(retryTask.getNextExecuteTime(), 5));
                    retryTaskRepository.update(retryTask);
                    COMMON_TASK_LOGGER.info("重试任务下次执行, retryTaskId = " + retryTask.getTaskId() + " retryTaskCode = " + retryTask.getRetryTaskConfig().getRetryTaskCode());
                }
            }
        }

        return filteredRetryTaskList;
    }

    private List<RetryTask> queryRetryTaskList(int batchQueryRetryTaskSize, Date date) {

        List<RetryTask> retryTaskList = new ArrayList<>();

        // 查询可执行的高优先级重试任务
        List<RetryTask> highPriorityRetryTaskList = retryTaskRepository.queryCanExecuteRetryTaskList(TaskPriorityEnum.HIGH, batchQueryRetryTaskSize, date);
        retryTaskList.addAll(highPriorityRetryTaskList);

        if (!CollectionUtils.isEmpty(retryTaskList)
                && retryTaskList.size() >= batchQueryRetryTaskSize) {
            // 数量达标，直接返回
            return retryTaskList;
        }

        // 查询可执行的中优先级重试任务
        List<RetryTask> middlePriorityRetryTaskList = retryTaskRepository.queryCanExecuteRetryTaskList(TaskPriorityEnum.MEDIUM, (batchQueryRetryTaskSize - retryTaskList.size()), date);
        retryTaskList.addAll(middlePriorityRetryTaskList);

        if (!CollectionUtils.isEmpty(retryTaskList)
                && retryTaskList.size() >= batchQueryRetryTaskSize) {
            // 数量达标，直接返回
            return retryTaskList;
        }

        // 查询可执行的低优先级重试任务
        List<RetryTask> lowPriorityRetryTaskList = retryTaskRepository.queryCanExecuteRetryTaskList(TaskPriorityEnum.LOW, (batchQueryRetryTaskSize - retryTaskList.size()), date);
        retryTaskList.addAll(lowPriorityRetryTaskList);

        return retryTaskList;
    }
}
