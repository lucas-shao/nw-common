package com.normalworks.common.template;

import com.normalworks.common.model.service.BaseResult;
import com.normalworks.common.utils.assertion.AssertionException;
import com.normalworks.common.utils.assertion.AssertionResultCode;
import com.normalworks.common.utils.assertion.CommonResultCode;
import com.normalworks.common.utils.assertion.ErrorLevels;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * ServiceTemplate
 * 服务模板
 *
 * @author : shaoshuai
 * @version V1.0
 * @date Date : 2021年10月31日 5:41 下午
 */
@Service
public class ServiceTemplate {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceTemplate.class);

    @Autowired
    private DataSourceTransactionManager dataSourceTransactionManager;

    /**
     * 标准服务处理模板（未开启事务）
     *
     * @param result   处理结果
     * @param callback 服务回调模板
     */
    public void execute(BaseResult result, ServiceCallback callback) {

        try {

            // Step 1. 校验参数
            callback.checkParams();

            // Step 2. 执行服务处理
            callback.process();

        } catch (AssertionException exception) {

            // 打印错误日志，根据不同错误级别，打印不同级别的日志
            alertAssertionException(exception);

            fillResultWhenAssertionException(result, exception);

        } catch (Throwable throwable) {

            // 打印非预期的错误日志，全部当做ERROR级别打印
            alertThrowable(throwable);

            // 填充失败处理结果
            processFailureResult(result, new AssertionException(CommonResultCode.UNKNOWN_EXCEPTION));

        } finally {

            // 所有线程上下文清空
        }
    }


    /**
     * 标准服务处理模板（开启事务）
     *
     * @param result   处理结果
     * @param callback 服务回调模板
     */
    public void executeWithTransaction(BaseResult result, ServiceCallback callback) {

        // 设置事务隔离级别和传播行为
        // 事务隔离级别：使用后端数据库默认的隔离级别
        // 事务传播行为：创建新事务，如果已经存在事务则暂停当前事务
        DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        transactionDefinition.setIsolationLevel(TransactionDefinition.ISOLATION_DEFAULT);
        transactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);

        // 开启事务
        TransactionStatus transactionStatus = dataSourceTransactionManager.getTransaction(transactionDefinition);

        try {
            // Step 1. 校验参数
            callback.checkParams();

            // Step 2. 执行服务处理
            callback.process();

            // Step 3. 事务提交
            dataSourceTransactionManager.commit(transactionStatus);

            LOGGER.info("服务模版事务提交状态：" + transactionStatus.isCompleted());

        } catch (AssertionException exception) {

            // 事务回滚
            dataSourceTransactionManager.rollback(transactionStatus);

            // 打印错误日志，根据不同错误级别，打印不同级别的日志
            alertAssertionException(exception);

            fillResultWhenAssertionException(result, exception);

        } catch (Throwable throwable) {

            // 事务回滚
            dataSourceTransactionManager.rollback(transactionStatus);

            // 打印非预期的错误日志，全部当做ERROR级别打印
            alertThrowable(throwable);

            // 填充失败处理结果
            processFailureResult(result, new AssertionException(CommonResultCode.UNKNOWN_EXCEPTION));

        } finally {

            // 所有线程上下文清空
        }
    }

    private void fillResultWhenAssertionException(BaseResult result, AssertionException exceptioin) {
        if (exceptioin.getResultCode() == CommonResultCode.IDEMPOTENT) {
            buildSuccessResult(result);

        } else {
            // 填充失败处理结果
            processFailureResult(result, exceptioin);
        }
    }

    private static void alertThrowable(Throwable throwable) {

        LOGGER.error("Service is throwable failed", throwable);
    }

    private void processFailureResult(BaseResult result, AssertionException exception) {

        // 设置result
        result.setSuccess(false);
        result.setReadableResultCode(exception.getResultCode().getResultCode());
        result.setHttpStatus(exception.getResultCode().getHttpStatus());
        result.setDetailResultMessage(exception.getResultCode().getResultMsg());
    }

    private static void alertAssertionException(AssertionException exception) {

        AssertionResultCode errorCode = exception.getResultCode();
        String logMessage = "resultCode:" + (errorCode == null ? "NULL" : errorCode.getResultCode()) + ",message:" + exception.getMessage();
        if (null != errorCode) {
            if (StringUtils.equals(errorCode.getErrorLevel(), ErrorLevels.WARN)) {
                // BIZ 错误码，预期之内，使用WARN打印
                LOGGER.warn("Service is biz failed. " + logMessage, exception);
                return;
            }
        }

        // 其余预期之外的错误码，使用ERROR打印
        LOGGER.error("Service is error failed. " + logMessage, exception);
    }

    /**
     * 构建成功结果
     *
     * @param result
     */
    public void buildSuccessResult(BaseResult result) {

        // 设置result
        result.setSuccess(true);
        result.setReadableResultCode(CommonResultCode.SUCCESS.name());
        result.setDetailResultMessage(CommonResultCode.SUCCESS.getResultMsg());
        result.setHttpStatus(CommonResultCode.SUCCESS.getHttpStatus());
    }

    /**
     * 设置失败结果
     */
    public void buildFailResult(BaseResult result, AssertionResultCode resultCode) {
        result.setSuccess(false);
        result.setReadableResultCode(resultCode.getResultCode());
        result.setDetailResultMessage(resultCode.getResultMsg());
        result.setHttpStatus(resultCode.getHttpStatus());
    }

}
