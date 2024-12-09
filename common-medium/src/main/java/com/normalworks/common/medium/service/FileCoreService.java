package com.normalworks.common.medium.service;

import com.normalworks.common.utils.enums.HttpContentTypeEnum;
import com.normalworks.common.medium.model.File;

import java.io.ByteArrayOutputStream;
import java.util.Map;

/**
 * FileCoreService
 * 文件核心领域服务
 *
 * @author : shaoshuai.shao
 * @version : V1.0
 * @date : 2022/12/20 4:35 PM
 */
public interface FileCoreService {

    /**
     * 直接注册原始文件
     *
     * @param userId          上传用户ID
     * @param fileUrl         源文件地址
     * @param fileName        源文件名称
     * @param fileDigest      源文件摘要
     * @param fileSize        源文件大小
     * @param fileContentType 源文件类型
     * @param extInfo         扩展信息
     * @return 文件媒体
     */
    File register(String userId, String fileUrl, String fileName, String fileDigest, Long fileSize, HttpContentTypeEnum fileContentType, Map<String, String> extInfo);

    /**
     * 本地文件流注册文件
     *
     * @param userId          上传用户ID
     * @param fileUrl         源文件地址
     * @param fileName        注册文件名称
     * @param baos            文件流
     * @param fileContentType 源文件类型
     * @param extInfo         扩展信息
     * @return 文件媒体
     */
    File register(String userId, String fileUrl, String fileName, ByteArrayOutputStream baos, HttpContentTypeEnum fileContentType, Map<String, String> extInfo);

    /**
     * 上传本地文件到OSS文件存储
     *
     * @param localFilePath   本地文件路径
     * @param contentTypeEnum 文件类型
     * @param assetDomainUrl  媒体域名
     * @param uploadUserId    上传用户ID
     * @param mediumExtInfo   媒体扩展信息
     * @return
     */
    File uploadFile(String localFilePath, HttpContentTypeEnum contentTypeEnum, String assetDomainUrl, String uploadUserId, Map<String, String> mediumExtInfo);

    /**
     * 上传本地文件到OSS文件存储
     *
     * @param fileName        原始文件名称
     * @param localFilePath   本地文件路径
     * @param contentTypeEnum 文件类型
     * @param assetDomainUrl  媒体域名
     * @param uploadUserId    上传用户ID
     * @param mediumExtInfo   媒体扩展信息
     * @return
     */
    File uploadFile(String fileName, String localFilePath, HttpContentTypeEnum contentTypeEnum, String assetDomainUrl, String uploadUserId, Map<String, String> mediumExtInfo);

}
