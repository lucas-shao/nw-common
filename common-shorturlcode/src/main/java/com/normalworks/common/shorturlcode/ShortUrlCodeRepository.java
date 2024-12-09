package com.normalworks.common.shorturlcode;

import com.normalworks.common.utils.DateUtil;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.*;

/**
 * ShortUrlCodeRepository
 *
 * @author: lingeng
 * @date: 07/03/2023
 */
@Service
public class ShortUrlCodeRepository {

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Resource
    private ShortUrlCodeConfig shortUrlCodeConfig;

    public void create(ShortUrlCodeRecord record) {

        String sql = "insert into " + getTableName() + "(long_url,short_code,create_time,modify_time)" + "values(?,?,?,?)";

        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();

        /**
         * 使用这个方式来执行insert操作，主要是想要获取到自增id
         */
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, record.getLongUrl());
            ps.setString(2, record.getShortCode());
            ps.setTimestamp(3, new Timestamp(DateUtil.curr().getTime()));
            ps.setTimestamp(4, new Timestamp(DateUtil.curr().getTime()));
            return ps;
        }, generatedKeyHolder);

        record.setId(generatedKeyHolder.getKey().longValue());
    }


    public ShortUrlCodeRecord queryByLongUrl(String longUrl) {

        String sql = "select * from " + getTableName() + " where long_url=?";
        return jdbcTemplate.query(sql, new ResultSetExtractor<ShortUrlCodeRecord>() {
            @Override
            public ShortUrlCodeRecord extractData(ResultSet rs) throws SQLException, DataAccessException {
                return extractSingleRowResult(rs);
            }
        }, longUrl);
    }


    public ShortUrlCodeRecord queryByShortCode(String shortCode) {

        String sql = "select * from " + getTableName() + " where short_code=?";
        return jdbcTemplate.query(sql, new ResultSetExtractor<ShortUrlCodeRecord>() {
            @Override
            public ShortUrlCodeRecord extractData(ResultSet rs) throws SQLException, DataAccessException {
                return extractSingleRowResult(rs);
            }
        }, shortCode);
    }

    private ShortUrlCodeRecord extractSingleRowResult(ResultSet rs) throws SQLException {

        if (rs.getFetchSize() > 1) {
            throw new RuntimeException("expect 1 result, actual is " + rs.getFetchSize());
        }

        while (rs.next()) {

            ShortUrlCodeRecord record = new ShortUrlCodeRecord();
            record.setId(rs.getLong("id"));
            record.setLongUrl(rs.getString("long_url"));
            record.setShortCode(rs.getString("short_code"));
            record.setCreateTime(rs.getTimestamp("create_time"));
            record.setModifyTime(rs.getTimestamp("modify_time"));
            return record;
        }

        return null;
    }

    private String getTableName() {
        return shortUrlCodeConfig.getTablePrefix() + "short_url_code_record";
    }

}
