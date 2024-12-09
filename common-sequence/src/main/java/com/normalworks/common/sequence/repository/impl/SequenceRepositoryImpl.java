package com.normalworks.common.sequence.repository.impl;

import com.normalworks.common.sequence.Sequence;
import com.normalworks.common.sequence.SequenceConfig;
import com.normalworks.common.sequence.repository.SequenceRepository;
import com.normalworks.common.utils.DateUtil;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * SequenceRepositoryImpl
 *
 * @author : shaoshuai.shao
 * @version : V1.0
 * @date : 2022/11/16 7:45 PM
 */
@Service
public class SequenceRepositoryImpl implements SequenceRepository {

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Resource
    private SequenceConfig sequenceConfig;

    /**
     * 列字段信息
     */
    private static final String columnList = "id, sequence_name, current_value, increment_value, minimum, maximum, create_time, modify_time";

    @Override
    public List<Sequence> queryAllSequence() {

        String tableName = sequenceConfig.getTablePrefix() + "sequence";
        String sql = "select " + columnList + " from " + tableName;
        List<Sequence> sequenceList = jdbcTemplate.query(sql, new ResultSetExtractor<List<Sequence>>() {
            @Override
            public List<Sequence> extractData(ResultSet rs) throws SQLException, DataAccessException {
                return extractSequenceRowResult(rs);
            }
        });

        return sequenceList;
    }

    @Override
    public Sequence activeBySequenceName(String sequenceName) {

        String tableName = sequenceConfig.getTablePrefix() + "sequence";
        String sql = "select " + columnList + " from " + tableName + " where sequence_name = ? for update";
        Sequence sequence = jdbcTemplate.query(sql, new ResultSetExtractor<Sequence>() {
            @Override
            public Sequence extractData(ResultSet rs) throws SQLException, DataAccessException {
                return extractSingleSequenceRowResult(rs);
            }
        }, sequenceName);

        return sequence;
    }


    @Override
    public int update(Sequence sequence) {

        // 数据组装
        sequence.setModifyTime(DateUtil.curr());

        String tableName = sequenceConfig.getTablePrefix() + "sequence";
        String sql = "update " + tableName + " set sequence_name = ? , current_value = ? , increment_value = ? , minimum = ? , maximum = ? , create_time = ? , modify_time = ? where id = ?";

        Object[] args = {sequence.getSequenceName(), sequence.getCurrentValue(), sequence.getIncrementValue(), sequence.getMinimum(), sequence.getMaximum(), sequence.getCreateTime(), sequence.getModifyTime(), sequence.getId()};
        return jdbcTemplate.update(sql, args);
    }

    private List<Sequence> extractSequenceRowResult(ResultSet rs) throws SQLException {

        List<Sequence> sequenceList = new ArrayList<>();

        while (rs.next()) {
            Sequence sequence = new Sequence();
            sequence.setId(rs.getLong("id"));
            sequence.setSequenceName(rs.getString("sequence_name"));
            sequence.setCurrentValue(rs.getLong("current_value"));
            sequence.setIncrementValue(rs.getLong("increment_value"));
            sequence.setMinimum(rs.getLong("minimum"));
            sequence.setMaximum(rs.getLong("maximum"));
            sequence.setCreateTime(rs.getTimestamp("create_time"));
            sequence.setModifyTime(rs.getTimestamp("modify_time"));
            sequenceList.add(sequence);
        }

        return sequenceList;
    }

    private Sequence extractSingleSequenceRowResult(ResultSet rs) throws SQLException {

        if (rs.getFetchSize() > 1) {
            throw new RuntimeException("expect 1 result, actual is " + rs.getFetchSize());
        }

        while (rs.next()) {
            Sequence sequence = new Sequence();
            sequence.setId(rs.getLong("id"));
            sequence.setSequenceName(rs.getString("sequence_name"));
            sequence.setCurrentValue(rs.getLong("current_value"));
            sequence.setIncrementValue(rs.getLong("increment_value"));
            sequence.setMinimum(rs.getLong("minimum"));
            sequence.setMaximum(rs.getLong("maximum"));
            sequence.setCreateTime(rs.getTimestamp("create_time"));
            sequence.setModifyTime(rs.getTimestamp("modify_time"));
            return sequence;
        }

        return null;
    }

}
