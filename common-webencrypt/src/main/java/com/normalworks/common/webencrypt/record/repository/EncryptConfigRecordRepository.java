package com.normalworks.common.webencrypt.record.repository;

import com.normalworks.common.webencrypt.record.EncryptConfigRecord;

import java.util.Date;

/**
 * EncryptConfigRecordRepository
 * 接口数据加解密配置流水仓储类
 *
 * @author : shaoshuai.shao
 * @version : V1.0
 * @date : 2022/12/23 3:13 PM
 */
public interface EncryptConfigRecordRepository {

    /**
     * 使用这个方式来执行insert操作，主要是想要获取到自增id
     */
    void insert(EncryptConfigRecord record);

    /**
     * 查询某个客户端最新的有效的秘钥信息
     */
    EncryptConfigRecord queryLatestValidApiDataEncryptConfigRecord(String clientIp, Date now);

    /**
     * 使用AESKeyId查询秘钥信息
     */
    EncryptConfigRecord queryByAesKeyId(String aesKeyId);

    /**
     * 使用rsaKeyId查询秘钥信息
     */
    EncryptConfigRecord queryByRsaKeyId(String rsaKeyId);

    /**
     * 更新AES 秘钥信息
     */
    int updateAesKey(EncryptConfigRecord record);
}
