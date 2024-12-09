package com.normalworks.common.task.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * TaskIntegrationConfig
 * 任务集成配置
 *
 * @author : shaoshuai.shao
 * @version : V1.0
 * @date : 2022/11/11 4:05 PM
 */
@Configuration
public class TaskIntegrationConfig {

    /**
     * 重试任务表前缀
     * 一般都是以系统开头： pinvo_
     */
    @Value("${task.database.tablePrefix}")
    private String tablePrefix;

    /**
     * 任务缓存前缀
     * 一般都是以系统开头: PINVO_
     */
    @Value("${task.cache.prefix}")
    private String cachePrefix;

    /**
     * 一次性捞取的重试任务的条数
     * 例：50
     */
    @Value("${task.retrytask.batchLoadRetryTaskSize}")
    private int batchLoadRetryTaskSize;

    /**
     * 是否执行重试任务
     * on：执行
     * off：不执行
     */
    @Value("${task.retrytask.executeRetryTask}")
    private String executeRetryTask;

    /**
     * 默认是打开，只有设置为off的时候才是关闭
     */
    public boolean executeRetryTask() {
        if (executeRetryTask == null) {
            return true;
        } else {
            return !"off".equals(executeRetryTask);
        }
    }

    public String getTablePrefix() {
        return tablePrefix;
    }

    public void setTablePrefix(String tablePrefix) {
        this.tablePrefix = tablePrefix;
    }

    public String getCachePrefix() {
        return cachePrefix;
    }

    public void setCachePrefix(String cachePrefix) {
        this.cachePrefix = cachePrefix;
    }

    public int getBatchLoadRetryTaskSize() {
        return batchLoadRetryTaskSize;
    }

    public void setBatchLoadRetryTaskSize(int batchLoadRetryTaskSize) {
        this.batchLoadRetryTaskSize = batchLoadRetryTaskSize;
    }

    public String getExecuteRetryTask() {
        return executeRetryTask;
    }

    public void setExecuteRetryTask(String executeRetryTask) {
        this.executeRetryTask = executeRetryTask;
    }
}
