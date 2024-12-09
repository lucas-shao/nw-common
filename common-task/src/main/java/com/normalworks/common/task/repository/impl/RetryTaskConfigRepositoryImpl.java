package com.normalworks.common.task.repository.impl;

import com.normalworks.common.task.config.TaskIntegrationConfig;
import com.normalworks.common.task.converter.RetryTaskConfigConverter;
import com.normalworks.common.task.model.RetryTaskConfig;
import com.normalworks.common.task.model.dal.RetryTaskConfigDO;
import com.normalworks.common.task.repository.RetryTaskConfigRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * RetryTaskConfigRepositoryImpl
 *
 * @author : shaoshuai.shao
 * @version : V1.0
 * @date : 2022/11/11 3:59 PM
 */
@Service
public class RetryTaskConfigRepositoryImpl implements RetryTaskConfigRepository {

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Resource
    private TaskIntegrationConfig taskIntegrationConfig;

    /**
     * 重试任务配置表列字段信息
     */
    private static final String columnList = "id, retry_task_code, retry_task_name, task_execute_interval_config, task_status, task_priority, task_max_times_per_load, create_time, modify_time";


    @Override
    public RetryTaskConfig queryByCode(String retryTaskCode) {

        String retryTaskConfigTableName = taskIntegrationConfig.getTablePrefix() + "retry_task_config";

        if (StringUtils.isBlank(retryTaskCode)) {
            return null;
        }
        String configSql = "select " + columnList + " from " + retryTaskConfigTableName + " where retry_task_code = ?";

        RetryTaskConfigDO retryTaskConfigDO = jdbcTemplate.query(configSql, new ResultSetExtractor<RetryTaskConfigDO>() {
            @Override
            public RetryTaskConfigDO extractData(ResultSet rs) throws SQLException, DataAccessException {
                return extractSingleRetryTaskConfigRowResult(rs);
            }
        }, retryTaskCode);

        return RetryTaskConfigConverter.toDomain(retryTaskConfigDO);
    }


    private RetryTaskConfigDO extractSingleRetryTaskConfigRowResult(ResultSet rs) throws SQLException {

        if (rs.getFetchSize() > 1) {
            throw new RuntimeException("expect 1 result, actual is " + rs.getFetchSize());
        }

        while (rs.next()) {
            RetryTaskConfigDO retryTaskConfigDO = new RetryTaskConfigDO();
            retryTaskConfigDO.setRetryTaskCode(rs.getString("retry_task_code"));
            retryTaskConfigDO.setRetryTaskName(rs.getString("retry_task_name"));
            retryTaskConfigDO.setTaskExecuteIntervalConfig(rs.getString("task_execute_interval_config"));
            retryTaskConfigDO.setTaskStatus(rs.getString("task_status"));
            retryTaskConfigDO.setTaskPriority(rs.getString("task_priority"));
            retryTaskConfigDO.setTaskMaxTimesPerLoad(rs.getInt("task_max_times_per_load"));
            retryTaskConfigDO.setCreateTime(rs.getTimestamp("create_time"));
            retryTaskConfigDO.setModifyTime(rs.getTimestamp("modify_time"));
            return retryTaskConfigDO;
        }

        return null;
    }
}
