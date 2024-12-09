package com.normalworks.common.cloud.aws.client;

import java.net.URL;

/**
 * AwsS3Client
 * AWS S3文件服务
 *
 * @author : shaoshuai.shao
 * @version : V1.0
 * @date : 2022/12/20 10:18 AM
 */
public interface AwsS3Client {

    /**
     * 生成有时效性的上传URL
     *
     * @param fullKeyName  上传对象的全路径
     * @param contentType  文件类型
     * @param expTimeInSec 超期时长 单位秒
     * @return 上传URL
     */
    URL generatePreSignedUploadUrl(String fullKeyName, String contentType, long expTimeInSec);

    /**
     * 创建S3目录
     *
     * @param folderName 目录名称
     */
    void createFolder(String folderName);
}
