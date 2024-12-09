package com.normalworks.common.medium.repository;

import com.normalworks.common.medium.model.File;

/**
 * FileRepository
 * 文件仓储类
 *
 * @author : shaoshuai.shao
 * @version : V1.0
 * @date : 2022/12/20 4:14 PM
 */
public interface FileRepository {

    /**
     * 创建文件媒体
     *
     * @param file 文件媒体
     */
    void create(File file);

    /**
     * 更新文件媒体
     *
     * @param file 文件媒体
     */
    int update(File file);

    /**
     * 通过用户ID和文件摘要查询文件媒体
     *
     * @param userId     用户ID
     * @param fileDigest 文件媒体摘要
     * @return 文件媒体
     */
    File queryByUserIdAndDigest(String userId, String fileDigest);

    /**
     * 通过媒体ID查询文件
     *
     * @param mediumId 媒体ID
     * @return 文件媒体
     */
    File query(String mediumId);

    /**
     * 通过文件摘要查询
     *
     * @param userId       查询用户ID
     * @param mediumDigest 文件媒体摘要
     * @return 文件媒体
     */
    File queryUserIdByDigest(String userId, String mediumDigest);
}
