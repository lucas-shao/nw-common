package com.normalworks.common.task.repository.impl;

import com.normalworks.common.task.config.TaskIntegrationConfig;
import com.normalworks.common.task.converter.TaskConfigConverter;
import com.normalworks.common.task.model.TaskConfig;
import com.normalworks.common.task.model.dal.TaskConfigDO;
import com.normalworks.common.task.repository.TaskConfigRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * TaskConfigRepositoryImpl
 *
 * @author : shaoshuai.shao
 * @version : V1.0
 * @date : 2022/11/14 3:39 PM
 */
@Service
public class TaskConfigRepositoryImpl implements TaskConfigRepository {

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Resource
    private TaskIntegrationConfig taskIntegrationConfig;

    /**
     * 表列字段信息
     */
    private static final String columnList = "id, task_type, status, create_time, modify_time";

    @Override
    public TaskConfig queryByTaskType(String taskType) {

        String tableName = taskIntegrationConfig.getTablePrefix() + "task_config";

        if (StringUtils.isBlank(taskType)) {
            return null;
        }
        String configSql = "select " + columnList + " from " + tableName + " where task_type = ?";

        TaskConfigDO taskConfigDO = jdbcTemplate.query(configSql, new ResultSetExtractor<TaskConfigDO>() {
            @Override
            public TaskConfigDO extractData(ResultSet rs) throws SQLException, DataAccessException {
                return extractSingleTaskConfigRowResult(rs);
            }
        }, taskType);

        return TaskConfigConverter.toDomain(taskConfigDO);
    }

    private TaskConfigDO extractSingleTaskConfigRowResult(ResultSet rs) throws SQLException {
        if (rs.getFetchSize() > 1) {
            throw new RuntimeException("expect 1 result, actual is " + rs.getFetchSize());
        }

        while (rs.next()) {
            TaskConfigDO taskConfigDO = new TaskConfigDO();
            taskConfigDO.setId(rs.getLong("id"));
            taskConfigDO.setTaskType(rs.getString("task_type"));
            taskConfigDO.setStatus(rs.getString("status"));
            taskConfigDO.setCreateTime(rs.getTimestamp("create_time"));
            taskConfigDO.setModifyTime(rs.getTimestamp("modify_time"));
            return taskConfigDO;
        }

        return null;
    }
}
