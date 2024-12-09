package com.normalworks.common.medium.repository;

import com.normalworks.common.medium.model.Medium;

import java.util.List;

/**
 * MediumRepository
 * 媒体通用仓储类
 *
 * @author : shaoshuai.shao
 * @version : V1.0
 * @date : 2023/2/15 4:29 PM
 */
public interface MediumRepository {

    /**
     * 通过媒体ID查询媒体信息
     *
     * @param mediumId 媒体ID
     * @return 具体媒体信息
     */
    Medium query(String mediumId);

    /**
     * 批量查询媒体
     */
    List<Medium> query(List<String> mediumIds);
}
