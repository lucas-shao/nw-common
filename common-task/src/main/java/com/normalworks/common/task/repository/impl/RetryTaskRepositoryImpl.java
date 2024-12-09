package com.normalworks.common.task.repository.impl;

import com.normalworks.common.task.config.TaskIntegrationConfig;
import com.normalworks.common.task.converter.RetryTaskConverter;
import com.normalworks.common.task.enums.TaskPriorityEnum;
import com.normalworks.common.task.model.RetryTask;
import com.normalworks.common.task.model.RetryTaskConfig;
import com.normalworks.common.task.model.dal.RetryTaskDO;
import com.normalworks.common.task.repository.RetryTaskConfigRepository;
import com.normalworks.common.task.repository.RetryTaskRepository;
import com.normalworks.common.utils.DateUtil;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * RetryTaskRepositoryImpl
 *
 * @author : shaoshuai.shao
 * @version : V1.0
 * @date : 2022/11/11 4:00 PM
 */
@Service
public class RetryTaskRepositoryImpl implements RetryTaskRepository {

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Resource
    private TaskIntegrationConfig taskIntegrationConfig;

    @Resource
    private RetryTaskConfigRepository retryTaskConfigRepository;

    /**
     * 重试任务表列字段信息
     */
    private static final String columnList = "id, retry_task_code, business_id, retry_times, ext_info, next_execute_time, create_time, modify_time";

    @Override
    public void create(RetryTask retryTask) {

        // 数据组装
        retryTask.setCreateTime(DateUtil.curr());
        retryTask.setModifyTime(DateUtil.curr());
        retryTask.setRetryTimes(0);
        RetryTaskDO retryTaskDO = RetryTaskConverter.toDO(retryTask);

        String retryTaskTableName = taskIntegrationConfig.getTablePrefix() + "retry_task";
        String sql = "insert into " + retryTaskTableName + " (retry_task_code, business_id, retry_times, ext_info, next_execute_time, create_time, modify_time) " + "values (?,?,?,?,?,?,?)";

        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();

        /**
         * 使用这个方式来执行insert操作，主要是想要获取到自增id
         */
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, retryTaskDO.getRetryTaskCode());
                ps.setString(2, retryTaskDO.getBusinessId());
                ps.setInt(3, retryTaskDO.getRetryTimes());
                ps.setString(4, retryTaskDO.getExtInfo());
                ps.setTimestamp(5, new Timestamp(retryTaskDO.getNextExecuteTime().getTime()));
                ps.setTimestamp(6, new Timestamp(retryTaskDO.getCreateTime().getTime()));
                ps.setTimestamp(7, new Timestamp(retryTaskDO.getModifyTime().getTime()));
                return ps;
            }
        }, generatedKeyHolder);

        retryTask.setTaskId(generatedKeyHolder.getKey().toString());
    }

    @Override
    public RetryTask active(String taskId) {

        String retryTaskTableName = taskIntegrationConfig.getTablePrefix() + "retry_task";
        String sql = "select " + columnList + " from " + retryTaskTableName + " where id = ? for update";
        RetryTaskDO retryTaskDO = jdbcTemplate.query(sql, new ResultSetExtractor<RetryTaskDO>() {
            @Override
            public RetryTaskDO extractData(ResultSet rs) throws SQLException, DataAccessException {
                return extractSingleRetryTaskRowResult(rs);
            }
        }, taskId);

        if (null == retryTaskDO) {
            return null;
        }
        RetryTask retryTask = RetryTaskConverter.toDomain(retryTaskDO);
        RetryTaskConfig retryTaskConfig = retryTaskConfigRepository.queryByCode(retryTask.getRetryTaskConfig().getRetryTaskCode());
        retryTask.setRetryTaskConfig(retryTaskConfig);

        return retryTask;
    }

    @Override
    public int delete(String taskId) {

        String retryTaskTableName = taskIntegrationConfig.getTablePrefix() + "retry_task";
        String sql = "delete from " + retryTaskTableName + " where id = ?";
        Object[] args = {taskId};
        return jdbcTemplate.update(sql, args);
    }

    @Override
    public int update(RetryTask retryTask) {

        // 数据组装
        retryTask.setModifyTime(DateUtil.curr());
        RetryTaskDO retryTaskDO = RetryTaskConverter.toDO(retryTask);

        String retryTaskTableName = taskIntegrationConfig.getTablePrefix() + "retry_task";
        String sql = "update " + retryTaskTableName + " set retry_task_code = ? , business_id = ? , retry_times = ? , ext_info = ? , next_execute_time = ? , create_time = ? , modify_time = ? where id = ?";

        Object[] args = {retryTaskDO.getRetryTaskCode(), retryTaskDO.getBusinessId(), retryTaskDO.getRetryTimes(), retryTaskDO.getExtInfo(), retryTaskDO.getNextExecuteTime(), retryTaskDO.getCreateTime(), retryTaskDO.getModifyTime(), retryTaskDO.getId()};
        return jdbcTemplate.update(sql, args);
    }

    @Override
    public List<RetryTask> queryCanExecuteRetryTaskList(TaskPriorityEnum taskPriority, Integer batchQueryRetryTaskSize, Date now) {

        String retryTaskTableName = taskIntegrationConfig.getTablePrefix() + "retry_task";
        String retryTaskConfigTableName = taskIntegrationConfig.getTablePrefix() + "retry_task_config";

        String sql = "select t1.* from " + retryTaskTableName + " t1 LEFT JOIN " + retryTaskConfigTableName + " t2 on t1.`retry_task_code` = t2.`retry_task_code`" + " where t1.next_execute_time < ? and t2.`task_status` = 'RUNNING' and t2.`task_priority` = ? order by t1.next_execute_time asc limit ?";
        List<RetryTaskDO> retryTaskDOList = jdbcTemplate.query(sql, new ResultSetExtractor<List<RetryTaskDO>>() {
            @Override
            public List<RetryTaskDO> extractData(ResultSet rs) throws SQLException, DataAccessException {
                return extractRetryTaskRowResult(rs);
            }
        }, now, taskPriority.getValue(), batchQueryRetryTaskSize);

        return batchConstruct2RetryTaskList(retryTaskDOList);
    }

    @Override
    public RetryTask queryByRetryTaskCodeAndBusinessId(String retryTaskCode, String businessId) {

        String retryTaskTableName = taskIntegrationConfig.getTablePrefix() + "retry_task";
        String sql = "select " + columnList + " from " + retryTaskTableName + " where business_id = ? and retry_task_code = ?";
        RetryTaskDO retryTaskDO = jdbcTemplate.query(sql, new ResultSetExtractor<RetryTaskDO>() {
            @Override
            public RetryTaskDO extractData(ResultSet rs) throws SQLException, DataAccessException {
                return extractSingleRetryTaskRowResult(rs);
            }
        }, businessId, retryTaskCode);

        if (null == retryTaskDO) {
            return null;
        }
        RetryTask retryTask = RetryTaskConverter.toDomain(retryTaskDO);
        RetryTaskConfig retryTaskConfig = retryTaskConfigRepository.queryByCode(retryTask.getRetryTaskConfig().getRetryTaskCode());
        retryTask.setRetryTaskConfig(retryTaskConfig);

        return retryTask;
    }

    private List<RetryTask> batchConstruct2RetryTaskList(List<RetryTaskDO> retryTaskDOList) {

        List<RetryTask> retryTaskList = new ArrayList<>();
        if (CollectionUtils.isEmpty(retryTaskDOList)) {
            return new ArrayList<>();
        }
        for (RetryTaskDO retryTaskDO : retryTaskDOList) {
            RetryTask retryTask = RetryTaskConverter.toDomain(retryTaskDO);
            RetryTaskConfig retryTaskConfig = retryTaskConfigRepository.queryByCode(retryTask.getRetryTaskConfig().getRetryTaskCode());
            retryTask.setRetryTaskConfig(retryTaskConfig);
            retryTaskList.add(retryTask);
        }
        return retryTaskList;
    }

    private List<RetryTaskDO> extractRetryTaskRowResult(ResultSet rs) throws SQLException {

        List<RetryTaskDO> retryTaskDOList = new ArrayList<>();

        while (rs.next()) {
            RetryTaskDO retryTaskDO = new RetryTaskDO();
            retryTaskDO.setId(rs.getLong("id"));
            retryTaskDO.setRetryTaskCode(rs.getString("retry_task_code"));
            retryTaskDO.setBusinessId(rs.getString("business_id"));
            retryTaskDO.setRetryTimes(rs.getInt("retry_times"));
            retryTaskDO.setNextExecuteTime(rs.getTimestamp("next_execute_time"));
            retryTaskDO.setExtInfo(rs.getString("ext_info"));
            retryTaskDO.setCreateTime(rs.getTimestamp("create_time"));
            retryTaskDO.setModifyTime(rs.getTimestamp("modify_time"));
            retryTaskDOList.add(retryTaskDO);
        }

        return retryTaskDOList;
    }

    private RetryTaskDO extractSingleRetryTaskRowResult(ResultSet rs) throws SQLException {
        if (rs.getFetchSize() > 1) {
            throw new RuntimeException("expect 1 result, actual is " + rs.getFetchSize());
        }

        while (rs.next()) {
            RetryTaskDO retryTaskDO = new RetryTaskDO();
            retryTaskDO.setId(rs.getLong("id"));
            retryTaskDO.setRetryTaskCode(rs.getString("retry_task_code"));
            retryTaskDO.setBusinessId(rs.getString("business_id"));
            retryTaskDO.setRetryTimes(rs.getInt("retry_times"));
            retryTaskDO.setNextExecuteTime(rs.getTimestamp("next_execute_time"));
            retryTaskDO.setExtInfo(rs.getString("ext_info"));
            retryTaskDO.setCreateTime(rs.getTimestamp("create_time"));
            retryTaskDO.setModifyTime(rs.getTimestamp("modify_time"));
            return retryTaskDO;
        }

        return null;
    }
}
