package com.normalworks.common.webencrypt.record.repository.impl;

import com.normalworks.common.utils.DateUtil;
import com.normalworks.common.webencrypt.record.EncryptConfigRecord;
import com.normalworks.common.webencrypt.record.repository.EncryptConfigRecordRepository;
import com.normalworks.common.webencrypt.web.EncryptConfig;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.*;
import java.util.Date;

/**
 * EncryptConfigRecordRepositoryImpl
 *
 * @author: lingeng
 * @date: 10/19/22
 */
@Service
public class EncryptConfigRecordRepositoryImpl implements EncryptConfigRecordRepository {

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Resource
    private EncryptConfig encryptConfig;

    /**
     * 插入数据
     */
    public void insert(EncryptConfigRecord record) {

        String tableName = encryptConfig.getTablePrefix() + "encrypt_config_record";
        String sql = "insert into " + tableName + " (client_ip, rsa_key_id,rsa_public_key,rsa_private_key, rsa_invalid_time,aes_key_id, aes_key, aes_invalid_time,create_time, modify_time) " + "values (?,?,?,?,?,?,?,?,?,?)";

        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();

        /**
         * 使用这个方式来执行insert操作，主要是想要获取到自增id
         */
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, record.getClientIp());
                ps.setString(2, record.getRsaKeyId());
                ps.setString(3, record.getRsaPublicKey());
                ps.setString(4, record.getRsaPrivateKey());
                ps.setTimestamp(5, record.getRsaInvalidTime() == null ? null : new Timestamp(record.getRsaInvalidTime().getTime()));
                ps.setString(6, record.getAesKeyId());
                ps.setString(7, record.getAesKey());
                ps.setTimestamp(8, record.getAesInvalidTime() == null ? null : new Timestamp(record.getAesInvalidTime().getTime()));
                ps.setTimestamp(9, new Timestamp(DateUtil.curr().getTime()));
                ps.setTimestamp(10, new Timestamp(DateUtil.curr().getTime()));
                return ps;
            }
        }, generatedKeyHolder);

        record.setId(generatedKeyHolder.getKey().longValue());
    }

    /**
     * 查询某个客户端最新的有效的秘钥信息
     */
    public EncryptConfigRecord queryLatestValidApiDataEncryptConfigRecord(String clientIp, Date now) {

        String tableName = encryptConfig.getTablePrefix() + "encrypt_config_record";
        String sql = "select * from " + tableName + " where client_ip = ? and rsa_invalid_time > ? order by rsa_invalid_time desc limit 1";
        return jdbcTemplate.query(sql, new ResultSetExtractor<EncryptConfigRecord>() {
            @Override
            public EncryptConfigRecord extractData(ResultSet rs) throws SQLException, DataAccessException {
                return extractSingleRowResult(rs);
            }
        }, clientIp, now);

    }

    /**
     * 使用AESKeyId查询秘钥信息
     */
    public EncryptConfigRecord queryByAesKeyId(String aesKeyId) {

        String tableName = encryptConfig.getTablePrefix() + "encrypt_config_record";
        String sql = "select * from " + tableName + " where aes_key_id = ? ";
        return jdbcTemplate.query(sql, new ResultSetExtractor<EncryptConfigRecord>() {
            @Override
            public EncryptConfigRecord extractData(ResultSet rs) throws SQLException, DataAccessException {
                return extractSingleRowResult(rs);
            }
        }, aesKeyId);

    }

    /**
     * 使用rsaKeyId查询秘钥信息
     */
    public EncryptConfigRecord queryByRsaKeyId(String rsaKeyId) {

        String tableName = encryptConfig.getTablePrefix() + "encrypt_config_record";
        String sql = "select * from " + tableName + " where rsa_key_id = ? ";
        return jdbcTemplate.query(sql, new ResultSetExtractor<EncryptConfigRecord>() {
            @Override
            public EncryptConfigRecord extractData(ResultSet rs) throws SQLException, DataAccessException {
                return extractSingleRowResult(rs);
            }
        }, rsaKeyId);

    }

    /**
     * 更新AES 秘钥信息
     */
    public int updateAesKey(EncryptConfigRecord record) {

        String tableName = encryptConfig.getTablePrefix() + "encrypt_config_record";
        String sql = "update " + tableName + " set aes_key_id=?,aes_key=?,aes_invalid_time=?,modify_time=? where id=?";
        Object[] args = {record.getAesKeyId(), record.getAesKey(), record.getAesInvalidTime(), DateUtil.curr(), record.getId()};
        return jdbcTemplate.update(sql, args);

    }

    /**
     * 将查询返回结果转换成Bean，返回结果不超过一条。
     */
    private static EncryptConfigRecord extractSingleRowResult(ResultSet rs) throws SQLException {
        if (rs.getFetchSize() > 1) {
            throw new RuntimeException("expect 1 result, actual is " + rs.getFetchSize());
        }

        while (rs.next()) {
            EncryptConfigRecord record = new EncryptConfigRecord();
            record.setId(rs.getLong("id"));
            record.setClientIp(rs.getString("client_ip"));
            record.setRsaKeyId(rs.getString("rsa_key_id"));
            record.setRsaPrivateKey(rs.getString("rsa_private_key"));
            record.setRsaPublicKey(rs.getString("rsa_public_key"));
            record.setRsaInvalidTime(rs.getTimestamp("rsa_invalid_time"));
            record.setAesKeyId(rs.getString("aes_key_id"));
            record.setAesKey(rs.getString("aes_key"));
            record.setAesInvalidTime(rs.getTimestamp("aes_invalid_time"));
            record.setCreateTime(rs.getTimestamp("create_time"));
            record.setModifyTime(rs.getTimestamp("modify_time"));
            return record;
        }

        return null;
    }

}
