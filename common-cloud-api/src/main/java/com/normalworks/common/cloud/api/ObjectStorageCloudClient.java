package com.normalworks.common.cloud.api;

import java.io.BufferedReader;
import java.io.InputStream;

/**
 * ObjectStorageCloudClient
 * 对象存储云服务
 *
 * @author : shaoshuai.shao
 * @version : V1.0
 * @date : 2022/11/1 5:23 PM
 */
public interface ObjectStorageCloudClient {

    /**
     * 上传输入流到对象存储上
     *
     * @param inputStream 上传输入流
     * @param fullKeyName 完整的存储Key
     */
    void uploadObject(InputStream inputStream, String fullKeyName);

    /**
     * 查询对象存储上的流
     *
     * @param fullKeyName 完整的存储key
     */
    BufferedReader getObject(String fullKeyName);

    /**
     * 删除对象存储
     *
     * @param fullKeyName 完整的存储key
     */
    void deleteObject(String fullKeyName);
}
