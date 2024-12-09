package com.normalworks.common.medium.repository;

import com.normalworks.common.medium.model.Image;

/**
 * ImageRepository
 * 图片媒体仓储层
 *
 * @author : shaoshuai.shao
 * @version : V1.0
 * @date : 2022/12/20 4:13 PM
 */
public interface ImageRepository {

    /**
     * 创建图片
     *
     * @param image 图片
     */
    void create(Image image);

    /**
     * 通过图片内容摘要反查图片
     *
     * @param userId      用户ID
     * @param imageDigest 图片内容摘要
     * @return 图片
     */
    Image queryUserIdByDigest(String userId, String imageDigest);

    /**
     * 通过ID查询图片
     *
     * @param imageId 图片ID
     * @return 图片
     */
    Image queryById(String imageId);

    /**
     * 通过ID锁定图片
     *
     * @param imageId 图片ID
     * @return 图片
     */
    Image activeById(String imageId);

    /**
     * 更新图片
     *
     * @param image 图片
     */
    int update(Image image);
}
