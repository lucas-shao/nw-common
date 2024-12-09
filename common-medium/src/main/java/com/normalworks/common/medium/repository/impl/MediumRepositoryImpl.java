package com.normalworks.common.medium.repository.impl;

import com.normalworks.common.medium.converter.MediumConverter;
import com.normalworks.common.medium.model.Medium;
import com.normalworks.common.medium.model.dal.MediumDO;
import com.normalworks.common.medium.repository.MediumRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

/**
 * MediumRepositoryImpl
 *
 * @author : shaoshuai.shao
 * @version : V1.0
 * @date : 2023/2/15 4:30 PM
 */
@Service
public class MediumRepositoryImpl extends BaseRepositoryImpl implements MediumRepository {

    @Override
    public Medium query(String mediumId) {

        String mediumTableName = mediumIntegrationConfig.getTablePrefix() + "medium";

        String configSql = "select " + columnList + " from " + mediumTableName + " where id = ?";

        MediumDO mediumDO = jdbcTemplate.query(configSql, new ResultSetExtractor<MediumDO>() {
            @Override
            public MediumDO extractData(ResultSet rs) throws SQLException, DataAccessException {
                return extractSingleMediumRowResult(rs);
            }
        }, mediumId);

        return MediumConverter.toDomain(mediumDO);
    }

    @Override
    public List<Medium> query(List<String> mediumIds) {
        String inSql = String.join(",", Collections.nCopies(mediumIds.size(), "?"));

        String mediumTableName = mediumIntegrationConfig.getTablePrefix() + "medium";

        String sql = "select " + columnList + " from " + mediumTableName + " where id in(" + inSql + ")";

        List<MediumDO> mediumDOs = jdbcTemplate.query(sql, new ResultSetExtractor<List<MediumDO>>() {
            @Override
            public List<MediumDO> extractData(ResultSet rs) throws SQLException, DataAccessException {
                return extractMediumRowResult(rs);
            }
        }, mediumIds.toArray());
        return MediumConverter.batchToDomain(mediumDOs);
    }
}
