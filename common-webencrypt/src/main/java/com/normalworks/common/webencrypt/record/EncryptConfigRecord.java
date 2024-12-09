package com.normalworks.common.webencrypt.record;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * EncryptConfigRecord
 * 接口数据加解密配置流水
 *
 * @author : shaoshuai
 * @version V1.0
 * @date Date : 2022年04月07日 7:57 下午
 */
public class EncryptConfigRecord {

    private Long id;

    /**
     * 客户端请求RSA公私钥交换的ip
     */
    private String clientIp;

    /**
     * 服务端生成的RSA的KEY ID
     */
    private String rsaKeyId;

    /**
     * 服务端生成的RSA公钥字符串值
     */
    private String rsaPublicKey;

    /**
     * 服务端生成的RSA私钥字符串值
     */
    private String rsaPrivateKey;

    /**
     * 服务端生成的RSA公私钥对失效时间
     */
    private Date rsaInvalidTime;

    /**
     * 服务端生成的AES密钥KEY ID
     */
    private String aesKeyId;

    /**
     * 服务端生的AES KEY值
     */
    private String aesKey;

    /**
     * 服务端生成的AES密钥失效时间
     */
    private Date aesInvalidTime;

    /**
     * 使用公钥加密之后的AES秘钥
     */
    private String encryptedAesKey;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date modifyTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getRsaKeyId() {
        return rsaKeyId;
    }

    public void setRsaKeyId(String rsaKeyId) {
        this.rsaKeyId = rsaKeyId;
    }

    public String getRsaPublicKey() {
        return rsaPublicKey;
    }

    public void setRsaPublicKey(String rsaPublicKey) {
        this.rsaPublicKey = rsaPublicKey;
    }

    public String getRsaPrivateKey() {
        return rsaPrivateKey;
    }

    public void setRsaPrivateKey(String rsaPrivateKey) {
        this.rsaPrivateKey = rsaPrivateKey;
    }

    public Date getRsaInvalidTime() {
        return rsaInvalidTime;
    }

    public void setRsaInvalidTime(Date rsaInvalidTime) {
        this.rsaInvalidTime = rsaInvalidTime;
    }

    public String getAesKeyId() {
        return aesKeyId;
    }

    public void setAesKeyId(String aesKeyId) {
        this.aesKeyId = aesKeyId;
    }

    public String getAesKey() {
        return aesKey;
    }

    public void setAesKey(String aesKey) {
        this.aesKey = aesKey;
    }

    public Date getAesInvalidTime() {
        return aesInvalidTime;
    }

    public void setAesInvalidTime(Date aesInvalidTime) {
        this.aesInvalidTime = aesInvalidTime;
    }

    public String getEncryptedAesKey() {
        return encryptedAesKey;
    }

    public void setEncryptedAesKey(String encryptedAesKey) {
        this.encryptedAesKey = encryptedAesKey;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
