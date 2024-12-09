package com.normalworks.common.task.executor;

import com.normalworks.common.task.model.RetryTask;
import com.normalworks.common.task.repository.RetryTaskRepository;
import com.normalworks.common.utils.ContextUtil;
import com.normalworks.common.utils.assertion.AssertionException;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

/**
 * AbstractRetryTaskExecutor
 * 重试任务处理器基类
 *
 * @author : shaoshuai
 * @version V1.0
 * @date Date : 2021年11月07日 9:56 下午
 */
public abstract class AbstractRetryTaskExecutor implements RetryTaskExecutor {

    private static final Logger COMMON_RETRY_TASK_DIGEST_LOGGER = LoggerFactory.getLogger("COMMON-RETRY-TASK-DIGEST");

    private static final Logger COMMON_TASK_LOGGER = LoggerFactory.getLogger("COMMON-TASK");

    @Resource
    protected RetryTaskRepository retryTaskRepository;

    @Resource
    private DataSourceTransactionManager dataSourceTransactionManager;

    @Override
    public void execute(RetryTask retryTask) {

        // 计时开始
        long startTime = System.currentTimeMillis();
        // 下一次执行时间，用来做日志采集
        long nextExecuteTime = System.currentTimeMillis();
        boolean retryTaskResult = false;
        RetryTask activeRetryTask = null;

        // 开启事务
        // 设置事务隔离级别和传播行为
        // 事务隔离级别：使用后端数据库默认的隔离级别
        // 事务传播行为：默认传播行为，方法在一个事务中执行，如果调用的方法已经在一个事务中，则使用该事务，否则将创建一个新的事务
        DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        transactionDefinition.setIsolationLevel(TransactionDefinition.ISOLATION_DEFAULT);
        transactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionStatus transactionStatus = dataSourceTransactionManager.getTransaction(transactionDefinition);

        try {
            // 在日志线程上下文种下新的traceId，避免重试任务密集执行捞取一大波日志
            ContextUtil.init();

            // 开始执行重试任务
            COMMON_TASK_LOGGER.info("开始执行重试任务: retryTask=" + retryTask);

            // Step 1. 锁定重试任务
            activeRetryTask = retryTaskRepository.active(retryTask.getTaskId());

            if (null == activeRetryTask || !activeRetryTask.canExecute()) {

                COMMON_TASK_LOGGER.warn("重试任务不存在，或者不满足执行要求，不能被执行. activeRetryTaskId = " + activeRetryTask.getTaskId());

            } else {

                nextExecuteTime = activeRetryTask.getNextExecuteTime().getTime();

                // Step 2. 执行重试任务
                retryTaskResult = doRetryTask(activeRetryTask);

                // Step 3. 删除或者更新重试任务
                processRetryTask(activeRetryTask, retryTaskResult);
            }

            // Step 4. 事务提交
            dataSourceTransactionManager.commit(transactionStatus);

            COMMON_TASK_LOGGER.info("重试任务执行服务模版事务提交状态：" + transactionStatus.isCompleted());

        } catch (AssertionException assertionException) {

            COMMON_TASK_LOGGER.warn("执行重试任务业务异常", assertionException);

            doRollBack(dataSourceTransactionManager, transactionStatus, activeRetryTask);

        } catch (Throwable e) {

            COMMON_TASK_LOGGER.error("执行重试任务系统异常", e);

            doRollBack(dataSourceTransactionManager, transactionStatus, activeRetryTask);

        } finally {
            long finishedTime = System.currentTimeMillis();
            long period = finishedTime - startTime;
            long expectExecuteTimeInterval = startTime - nextExecuteTime;
            COMMON_TASK_LOGGER.info("重试任务执行结果:" + retryTaskResult + " ,时长：" + period + "ms, 开始执行时间距离期望执行时间：", expectExecuteTimeInterval, "ms");
            COMMON_RETRY_TASK_DIGEST_LOGGER.info(retryTask.getRetryTaskConfig().getRetryTaskCode() + "," + retryTask.getBusinessId() + "," + (retryTaskResult ? "Y" : "N") + "," + period + "ms," + expectExecuteTimeInterval + "ms");

            // 清除线程上下文
            ContextUtil.clear();

            // 清除日志上下文
            MDC.clear();
        }
    }

    /**
     * 默认实现方式是允许注册
     * 继承类可以自由实现覆盖父类的该方法
     *
     * @param businessId      业务ID
     * @param nextExecuteTime 下次执行时间
     * @param extInfos        业务扩展参数
     * @return
     */
    @Override
    public boolean canRegister(String businessId, Date nextExecuteTime, Map<String, String> extInfos) {
        return true;
    }


    private void doRollBack(DataSourceTransactionManager dataSourceTransactionManager, TransactionStatus transactionStatus, RetryTask activeRetryTask) {

        try {

            dataSourceTransactionManager.rollback(transactionStatus);

            // 重试任务失败
            processRetryTask(activeRetryTask, false);

        } catch (Throwable throwable) {

            COMMON_TASK_LOGGER.error("设置重试任务失败，事务回滚系统异常", throwable);
        }
    }


    private void processRetryTask(RetryTask activeRetryTask, boolean retryTaskResult) {

        // 重试任务可能都没有锁到
        if (null == activeRetryTask) {
            return;
        }

        if (retryTaskResult) {
            // 执行成功，删除任务
            retryTaskRepository.delete(activeRetryTask.getTaskId());
        } else {
            // 执行失败，更新任务
            activeRetryTask.setRetryTimes(activeRetryTask.getRetryTimes() + 1);
            activeRetryTask.setNextExecuteTime(DateUtils.addSeconds(new Date(), activeRetryTask.obtainIntervalSeconds(activeRetryTask.getRetryTimes())));
            retryTaskRepository.update(activeRetryTask);
        }
    }

    /**
     * 任务处理核心逻辑
     *
     * @param retryTask 将要执行的重试任务
     * @return
     */
    public abstract boolean doRetryTask(RetryTask retryTask) throws Exception;
}
