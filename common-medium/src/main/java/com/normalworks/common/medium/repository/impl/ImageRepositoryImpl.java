package com.normalworks.common.medium.repository.impl;

import com.normalworks.common.medium.converter.ImageConverter;
import com.normalworks.common.medium.model.Image;
import com.normalworks.common.medium.model.dal.MediumDO;
import com.normalworks.common.medium.repository.ImageRepository;
import com.normalworks.common.utils.DateUtil;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.sql.*;

/**
 * ImageRepositoryImpl
 *
 * @author : shaoshuai.shao
 * @version : V1.0
 * @date : 2022/12/20 6:20 PM
 */
@Service
public class ImageRepositoryImpl extends BaseRepositoryImpl implements ImageRepository {

    @Override
    public void create(Image image) {

        // 数据组装
        image.setCreateTime(DateUtil.curr());
        image.setModifyTime(DateUtil.curr());
        MediumDO mediumDO = ImageConverter.toDO(image);

        String mediumTableName = mediumIntegrationConfig.getTablePrefix() + "medium";
        String sql = "insert into " + mediumTableName + " (" + insertColumnList + ") " + "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();

        /**
         * 使用这个方式来执行insert操作，主要是想要获取到自增id
         */
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, mediumDO.getMediumType());
                ps.setString(2, mediumDO.getMediumDigest());
                if (null == mediumDO.getMediumSize()) {
                    ps.setNull(3, Types.INTEGER);
                } else {
                    ps.setLong(3, mediumDO.getMediumSize());
                }
                ps.setString(4, mediumDO.getMediumStatus());
                ps.setString(5, mediumDO.getMediumContentType());
                ps.setString(6, mediumDO.getUploadUserId());
                ps.setString(7, mediumDO.getMediumUrl());
                if (null == mediumDO.getImageHeight()) {
                    ps.setNull(8, Types.INTEGER);
                } else {
                    ps.setInt(8, mediumDO.getImageHeight());
                }
                if (null == mediumDO.getImageWidth()) {
                    ps.setNull(9, Types.INTEGER);
                } else {
                    ps.setInt(9, mediumDO.getImageWidth());
                }
                ps.setString(10, mediumDO.getStandardMediumUrl());
                ps.setString(11, mediumDO.getBlurMediumUrl());
                ps.setString(12, mediumDO.getCoverImageUrl());
                ps.setTimestamp(13, new Timestamp(mediumDO.getCreateTime().getTime()));
                ps.setTimestamp(14, new Timestamp(mediumDO.getModifyTime().getTime()));
                ps.setString(15, mediumDO.getAuditFailedDetails());
                if (null == mediumDO.getMediumDuration()) {
                    ps.setNull(16, Types.INTEGER);
                } else {
                    ps.setLong(16, mediumDO.getMediumDuration());
                }
                ps.setString(17, mediumDO.getTextContent());
                ps.setString(18, mediumDO.getExtInfo());
                ps.setString(19, mediumDO.getMediumName());
                return ps;
            }
        }, generatedKeyHolder);

        image.setMediumId(generatedKeyHolder.getKey().toString());
    }

    @Override
    public Image queryUserIdByDigest(String userId, String imageDigest) {

        String mediumTableName = mediumIntegrationConfig.getTablePrefix() + "medium";

        String configSql = "select " + columnList + " from " + mediumTableName + " where upload_user_id = ? and medium_digest = ? and medium_type = 'IMAGE'";

        MediumDO mediumDO = jdbcTemplate.query(configSql, new ResultSetExtractor<MediumDO>() {
            @Override
            public MediumDO extractData(ResultSet rs) throws SQLException, DataAccessException {
                return extractSingleMediumRowResult(rs);
            }
        }, userId, imageDigest);

        return ImageConverter.toDomain(mediumDO);
    }

    @Override
    public Image queryById(String imageId) {

        String mediumTableName = mediumIntegrationConfig.getTablePrefix() + "medium";

        String configSql = "select " + columnList + " from " + mediumTableName + " where id = ?";

        MediumDO mediumDO = jdbcTemplate.query(configSql, new ResultSetExtractor<MediumDO>() {
            @Override
            public MediumDO extractData(ResultSet rs) throws SQLException, DataAccessException {
                return extractSingleMediumRowResult(rs);
            }
        }, imageId);

        return ImageConverter.toDomain(mediumDO);
    }

    @Override
    public Image activeById(String imageId) {

        String mediumTableName = mediumIntegrationConfig.getTablePrefix() + "medium";

        String configSql = "select " + columnList + " from " + mediumTableName + " where id = ? for update";

        MediumDO mediumDO = jdbcTemplate.query(configSql, new ResultSetExtractor<MediumDO>() {
            @Override
            public MediumDO extractData(ResultSet rs) throws SQLException, DataAccessException {
                return extractSingleMediumRowResult(rs);
            }
        }, imageId);

        return ImageConverter.toDomain(mediumDO);
    }

    @Override
    public int update(Image image) {

        // 数据组装
        image.setModifyTime(DateUtil.curr());
        MediumDO mediumDO = ImageConverter.toDO(image);

        String mediumTableName = mediumIntegrationConfig.getTablePrefix() + "medium";
        String sql = "update " + mediumTableName + " set medium_type = ?, medium_digest = ?, medium_size = ?, medium_status = ?, medium_content_type = ?, upload_user_id = ?, medium_url = ?, image_height = ?, image_width = ?, standard_medium_url = ?, blur_medium_url = ?, cover_image_url = ?, create_time = ?, modify_time = ?, audit_failed_details = ?, medium_duration = ?, text_content = ?, ext_info = ?, medium_name = ? where id = ?";

        Object[] args = {mediumDO.getMediumType(), mediumDO.getMediumDigest(), mediumDO.getMediumSize(), mediumDO.getMediumStatus(), mediumDO.getMediumContentType(), mediumDO.getUploadUserId(), mediumDO.getMediumUrl(), mediumDO.getImageHeight(), mediumDO.getImageWidth(), mediumDO.getStandardMediumUrl(), mediumDO.getBlurMediumUrl(), mediumDO.getCoverImageUrl(), mediumDO.getCreateTime(), mediumDO.getModifyTime(), mediumDO.getAuditFailedDetails(), mediumDO.getMediumDuration(), mediumDO.getTextContent(), mediumDO.getExtInfo(), mediumDO.getMediumName(), mediumDO.getId()};
        return jdbcTemplate.update(sql, args);

    }
}
