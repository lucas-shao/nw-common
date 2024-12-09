package com.normalworks.common.medium.service;

import com.normalworks.common.utils.enums.HttpContentTypeEnum;
import com.normalworks.common.medium.model.Image;

import java.io.ByteArrayOutputStream;

/**
 * ImageCoreService
 * 图片领域核心服务
 *
 * @author : shaoshuai.shao
 * @version : V1.0
 * @date : 2022/12/20 4:22 PM
 */
public interface ImageCoreService {

    /**
     * 直接注册源图
     * （不进行内容审核和模糊图生成）
     *
     * @param userId           上传用户ID
     * @param imageUrl         源图URL
     * @param imageName        源图名称
     * @param imageDigest      源图摘要
     * @param imageSize        源图大小
     * @param imageHeight      源图高度
     * @param imageWidth       源图宽度
     * @param imageContentType 源图类型
     * @param standardImageUrl 前端压缩后的标准图
     * @return 图片媒体
     */
    Image register(String userId, String imageUrl, String imageName, String imageDigest, Long imageSize, Integer imageHeight, Integer imageWidth, HttpContentTypeEnum imageContentType, String standardImageUrl);

    /**
     * 存储图片
     *
     * @param userId           用户id
     * @param imageUrl         完整的图片url
     * @param imageName        注册图片名称
     * @param baos             图片流
     * @param imageContentType 源文件类型
     * @return 带id的image实例
     */
    Image register(String userId, String imageUrl, String imageName, ByteArrayOutputStream baos, HttpContentTypeEnum imageContentType);

    /**
     * 上传本地图片到OSS文件存储
     *
     * @param localImagePath  本地文件名称
     * @param contentTypeEnum 文件类型
     * @param assetDomainUrl  媒体域名
     * @param uploadUserId    上传用户ID
     * @return 图片媒体
     */
    Image uploadImage(String localImagePath, HttpContentTypeEnum contentTypeEnum, String assetDomainUrl, String uploadUserId);

    /**
     * 上传本地图片到OSS文件存储
     *
     * @param imageName       原始图片名称
     * @param localImagePath  本地文件全路径
     * @param contentTypeEnum 文件类型
     * @param assetDomainUrl  媒体域名
     * @param uploadUserId    上传用户ID
     * @return 图片媒体
     */
    Image uploadImage(String imageName, String localImagePath, HttpContentTypeEnum contentTypeEnum, String assetDomainUrl, String uploadUserId);
}
