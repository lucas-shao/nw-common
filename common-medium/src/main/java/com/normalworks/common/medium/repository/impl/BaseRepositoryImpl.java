package com.normalworks.common.medium.repository.impl;

import com.normalworks.common.medium.config.MediumIntegrationConfig;
import com.normalworks.common.medium.model.dal.MediumDO;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * BaseRepositoryImpl
 * 仓储基础类
 *
 * @author : shaoshuai.shao
 * @version : V1.0
 * @date : 2023/2/15 4:33 PM
 */
public abstract class BaseRepositoryImpl {

    @Resource
    protected JdbcTemplate jdbcTemplate;

    @Resource
    protected MediumIntegrationConfig mediumIntegrationConfig;

    /**
     * 媒体表列字段信息
     */
    protected static final String columnList = "id, medium_type, medium_digest, medium_size, medium_status, medium_content_type, upload_user_id, medium_url, image_height, image_width, standard_medium_url, blur_medium_url, cover_image_url, create_time, modify_time, audit_failed_details, medium_duration, text_content, ext_info, medium_name";

    protected static final String insertColumnList = "medium_type, medium_digest, medium_size, medium_status, medium_content_type, upload_user_id, medium_url, image_height, image_width, standard_medium_url, blur_medium_url, cover_image_url, create_time, modify_time, audit_failed_details, medium_duration, text_content, ext_info, medium_name";


    protected MediumDO extractSingleMediumRowResult(ResultSet rs) throws SQLException {

        if (rs.getFetchSize() > 1) {
            throw new RuntimeException("expect 1 result, actual is " + rs.getFetchSize());
        }

        while (rs.next()) {
            MediumDO mediumDO = new MediumDO();
            mediumDO.setId(rs.getLong("id"));
            mediumDO.setMediumType(rs.getString("medium_type"));
            mediumDO.setMediumDigest(rs.getString("medium_digest"));
            mediumDO.setMediumSize(rs.getLong("medium_size"));
            mediumDO.setMediumStatus(rs.getString("medium_status"));
            mediumDO.setMediumContentType(rs.getString("medium_content_type"));
            mediumDO.setUploadUserId(rs.getString("upload_user_id"));
            mediumDO.setMediumUrl(rs.getString("medium_url"));
            mediumDO.setImageHeight(rs.getInt("image_height"));
            mediumDO.setImageWidth(rs.getInt("image_width"));
            mediumDO.setStandardMediumUrl(rs.getString("standard_medium_url"));
            mediumDO.setBlurMediumUrl(rs.getString("blur_medium_url"));
            mediumDO.setCoverImageUrl(rs.getString("cover_image_url"));
            mediumDO.setCreateTime(rs.getTimestamp("create_time"));
            mediumDO.setModifyTime(rs.getTimestamp("modify_time"));
            mediumDO.setAuditFailedDetails(rs.getString("audit_failed_details"));
            mediumDO.setMediumDuration(rs.getLong("medium_duration"));
            mediumDO.setTextContent(rs.getString("text_content"));
            mediumDO.setExtInfo(rs.getString("ext_info"));
            mediumDO.setMediumName(rs.getString("medium_name"));
            return mediumDO;
        }

        return null;
    }

    protected List<MediumDO> extractMediumRowResult(ResultSet rs) throws SQLException {

        List<MediumDO> mediumDOs = new ArrayList<>();
        while (rs.next()) {
            MediumDO mediumDO = new MediumDO();
            mediumDO.setId(rs.getLong("id"));
            mediumDO.setMediumType(rs.getString("medium_type"));
            mediumDO.setMediumDigest(rs.getString("medium_digest"));
            mediumDO.setMediumSize(rs.getLong("medium_size"));
            mediumDO.setMediumStatus(rs.getString("medium_status"));
            mediumDO.setMediumContentType(rs.getString("medium_content_type"));
            mediumDO.setUploadUserId(rs.getString("upload_user_id"));
            mediumDO.setMediumUrl(rs.getString("medium_url"));
            mediumDO.setImageHeight(rs.getInt("image_height"));
            mediumDO.setImageWidth(rs.getInt("image_width"));
            mediumDO.setStandardMediumUrl(rs.getString("standard_medium_url"));
            mediumDO.setBlurMediumUrl(rs.getString("blur_medium_url"));
            mediumDO.setCoverImageUrl(rs.getString("cover_image_url"));
            mediumDO.setCreateTime(rs.getTimestamp("create_time"));
            mediumDO.setModifyTime(rs.getTimestamp("modify_time"));
            mediumDO.setAuditFailedDetails(rs.getString("audit_failed_details"));
            mediumDO.setMediumDuration(rs.getLong("medium_duration"));
            mediumDO.setTextContent(rs.getString("text_content"));
            mediumDO.setExtInfo(rs.getString("ext_info"));
            mediumDO.setMediumName(rs.getString("medium_name"));
            mediumDOs.add(mediumDO);
        }

        return mediumDOs;
    }
}
