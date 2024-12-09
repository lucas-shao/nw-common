package com.normalworks.common.webencrypt.util;

import com.normalworks.common.utils.LoggerUtil;
import com.normalworks.common.utils.assertion.AssertionException;
import com.normalworks.common.utils.assertion.CommonResultCode;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;
import java.security.*;
import java.security.spec.MGF1ParameterSpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * RSAUtil
 * RSA加密和解密工具类
 *
 * @author : shaoshuai
 * @version V1.0
 * @date Date : 2022年04月07日 4:52 下午
 */
public class RSAUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(RSAUtil.class);

    /**
     * 公钥KEY名称
     */
    public static final String PUBLIC_KEY_STR = "publicKeyString";

    /**
     * 私钥KEY名称
     */
    public static final String PRIVATE_KEY_STR = "privateKeyString";

    /**
     * 数字签名，密钥算法
     */
    private static final String RSA_KEY_ALGORITHM = "RSA";

    /**
     * 数字签名签名/验证算法
     */
    private static final String SIGNATURE_ALGORITHM = "MD5withRSA";

    /**
     * RSA密钥长度，RSA算法的默认密钥长度是1024密钥长度必须是64的倍数，在512到65536位之间
     */
    private static final int KEY_SIZE = 1024;

    /**
     * 生成RSA公私钥对
     */
    public static Map<String, String> initKey() {

        try {
            KeyPairGenerator keygen = KeyPairGenerator.getInstance(RSA_KEY_ALGORITHM);
            SecureRandom secrand = new SecureRandom();
            /**
             * 初始化随机产生器
             */
            secrand.setSeed("initSeed".getBytes());
            /**
             * 初始化密钥生成器
             */
            keygen.initialize(KEY_SIZE, secrand);
            KeyPair keys = keygen.genKeyPair();

            byte[] pub_key = keys.getPublic().getEncoded();
            String publicKeyString = Base64.encodeBase64String(pub_key);

            byte[] pri_key = keys.getPrivate().getEncoded();
            String privateKeyString = Base64.encodeBase64String(pri_key);

            Map<String, String> keyPairMap = new HashMap<>();
            keyPairMap.put(PUBLIC_KEY_STR, publicKeyString);
            keyPairMap.put(PRIVATE_KEY_STR, privateKeyString);

            return keyPairMap;
        } catch (NoSuchAlgorithmException e) {
            LoggerUtil.error(LOGGER, "初始化公私钥异常。", e);
            throw new AssertionException(CommonResultCode.UNKNOWN_EXCEPTION);
        }
    }

    /**
     * 密钥转成字符串
     *
     * @param key
     * @return
     */
    public static String encodeBase64String(byte[] key) {
        return Base64.encodeBase64String(key);
    }

    /**
     * 密钥转成byte[]
     *
     * @param key
     * @return
     */
    public static byte[] decodeBase64(String key) {
        return Base64.decodeBase64(key);
    }

    /**
     * 公钥加密
     *
     * @param data      加密前的字符串
     * @param publicKey 公钥
     * @return 加密后的字符串
     * @throws Exception
     */
    public static String encryptByPubKey(String data, String publicKey) {
        byte[] pubKey = RSAUtil.decodeBase64(publicKey);
        byte[] enSign = encryptByPubKey(data.getBytes(), pubKey);
        return Base64.encodeBase64String(enSign);
    }

    /**
     * 公钥加密
     *
     * @param data   待加密数据
     * @param pubKey 公钥
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPubKey(byte[] data, byte[] pubKey) {
        try {
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(pubKey);
            KeyFactory keyFactory = KeyFactory.getInstance(RSA_KEY_ALGORITHM);
            PublicKey publicKey = keyFactory.generatePublic(x509KeySpec);
            Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPPadding");
            OAEPParameterSpec oaepParams = new OAEPParameterSpec("SHA-256", "MGF1", new MGF1ParameterSpec("SHA-256"), PSource.PSpecified.DEFAULT);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey, oaepParams);
            return cipher.doFinal(data);
        } catch (Exception e) {
            LoggerUtil.error(LOGGER, "公钥加密异常，data=" + data + ",pubKey=" + pubKey, e);
            throw new AssertionException(CommonResultCode.UNKNOWN_EXCEPTION);
        }
    }

    /**
     * 私钥加密
     *
     * @param data       加密前的字符串
     * @param privateKey 私钥
     * @return 加密后的字符串
     * @throws Exception
     */
    public static String encryptByPriKey(String data, String privateKey) throws Exception {
        byte[] priKey = RSAUtil.decodeBase64(privateKey);
        byte[] enSign = encryptByPriKey(data.getBytes(), priKey);
        return Base64.encodeBase64String(enSign);
    }

    /**
     * 私钥加密
     *
     * @param data   待加密的数据
     * @param priKey 私钥
     * @return 加密后的数据
     * @throws Exception
     */
    public static byte[] encryptByPriKey(byte[] data, byte[] priKey) throws Exception {
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(priKey);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_KEY_ALGORITHM);
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPPadding");
        OAEPParameterSpec oaepParams = new OAEPParameterSpec("SHA-256", "MGF1", new MGF1ParameterSpec("SHA-256"), PSource.PSpecified.DEFAULT);
        cipher.init(Cipher.ENCRYPT_MODE, privateKey, oaepParams);
        return cipher.doFinal(data);
    }

    /**
     * 公钥解密
     *
     * @param data   待解密的数据
     * @param pubKey 公钥
     * @return 解密后的数据
     * @throws Exception
     */
    public static byte[] decryptByPubKey(byte[] data, byte[] pubKey) throws Exception {
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(pubKey);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_KEY_ALGORITHM);
        PublicKey publicKey = keyFactory.generatePublic(x509KeySpec);
        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPPadding");
        OAEPParameterSpec oaepParams = new OAEPParameterSpec("SHA-256", "MGF1", new MGF1ParameterSpec("SHA-256"), PSource.PSpecified.DEFAULT);
        cipher.init(Cipher.DECRYPT_MODE, publicKey, oaepParams);
        return cipher.doFinal(data);
    }

    /**
     * 公钥解密
     *
     * @param data      解密前的字符串
     * @param publicKey 公钥
     * @return 解密后的字符串
     * @throws Exception
     */
    public static String decryptByPubKey(String data, String publicKey) throws Exception {
        byte[] pubKey = RSAUtil.decodeBase64(publicKey);
        byte[] design = decryptByPubKey(Base64.decodeBase64(data), pubKey);
        return new String(design);
    }

    /**
     * 私钥解密
     *
     * @param data   待解密的数据
     * @param priKey 私钥
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPriKey(byte[] data, byte[] priKey) throws Exception {
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(priKey);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_KEY_ALGORITHM);
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPPadding");
        OAEPParameterSpec oaepParams = new OAEPParameterSpec("SHA-256", "MGF1", new MGF1ParameterSpec("SHA-256"), PSource.PSpecified.DEFAULT);
        cipher.init(Cipher.DECRYPT_MODE, privateKey, oaepParams);
        return cipher.doFinal(data);
    }

    /**
     * 私钥解密
     *
     * @param data       解密前的字符串
     * @param privateKey 私钥
     * @return 解密后的字符串
     * @throws Exception
     */
    public static String decryptByPriKey(String data, String privateKey) throws Exception {
        byte[] priKey = RSAUtil.decodeBase64(privateKey);
        byte[] design = decryptByPriKey(Base64.decodeBase64(data), priKey);
        return new String(design);
    }

    /**
     * RSA签名
     *
     * @param data   待签名数据
     * @param priKey 私钥
     * @return 签名
     * @throws Exception
     */
    public static String sign(byte[] data, byte[] priKey) throws Exception {
        // 取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(priKey);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_KEY_ALGORITHM);
        // 生成私钥
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        // 实例化Signature
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        // 初始化Signature
        signature.initSign(privateKey);
        // 更新
        signature.update(data);
        return Base64.encodeBase64String(signature.sign());
    }

    public static void main(String[] args) {
        try {
            Map<String, String> keyMap = initKey();
            String publicKeyString = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCNEmp1U2drgG2tvAq6WxK4NXQ/WKQNnr6oKTyljnk/wirAbnXUIGCEaYMU/4jEu36DQ9wqDDDVUwsNQHGWCNl0d2eHT3A7rruCxo3lKLX7BwB0vinpYMp8X5ZBYEjS+XCD/ratD1O+wvSGoZ21DiVQ4ED6nVUyOCDwUbdwIfgzPQIDAQAB";
            String privateKeyString = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAI0SanVTZ2uAba28CrpbErg1dD9YpA2evqgpPKWOeT/CKsBuddQgYIRpgxT/iMS7foND3CoMMNVTCw1AcZYI2XR3Z4dPcDuuu4LGjeUotfsHAHS+KelgynxflkFgSNL5cIP+tq0PU77C9IahnbUOJVDgQPqdVTI4IPBRt3Ah+DM9AgMBAAECgYA55bH9fwJ181qQMBmzDAetxHp2ORnzYRBckbgLNmL60OSc33jKW5NhJLCgoJNwMocktmgYdbJhCtkBUk6LxgwAjYO10ZaENJgwnZA0AoTPiCrgSx4qFM2O2+7VOtO93ix8DJejAb/mNPMPrm9qGUGK2dmPv93xCOGGjhd3yVj5AQJBAMf6r8tbgse1UL8Ugb6D9JR0om+U5d/4vZrGIztgXY6SRMacjM9okNesL4n8zWveA3SAhiuytrrs2A7aSe5fbH0CQQC0lz/9aRl1mNCi0NUUNDHbuTA882ZTpE0LqpP6RzOfLLDBkgtfnSJHvs/IXQwjRFxD0hrKr6ML9JthxJXAdl3BAkAWug6wdb3vJFQA4zu7gxOQq5CqTamyQnqJ1Gq8nL8FLOpUCAaYq7GKUS81510RHTIXU2PsI2tQ4wnK5lPwmzehAkEAmXLXjbUrS/BAf8hg9hzvrJsr8O6FKVc3nE0iFg1AQOWRf7lf7quvQDVTixX9RU5F897oowcgzPzYtvd6o6IwQQJAJ7FjxMn+L0R4EMqka3FLYVcgfWeKgemXx4Zq/QRRE9lWpYXON2V9X4irT6bLwgY3sdwvHDvNYoB9njomHIIoMg==";
            System.out.println("公钥:" + publicKeyString);
            System.out.println("私钥:" + privateKeyString);

            // 待加密数据
            String data = "e6678e836553b0aeffb99631e5598a2d";
            // 公钥加密
            String encrypt = RSAUtil.encryptByPubKey(data, publicKeyString);
            // 私钥解密
            String decrypt = RSAUtil.decryptByPriKey(encrypt, privateKeyString);

            System.out.println("加密前:" + data);
            System.out.println("加密后:" + encrypt);
            System.out.println("解密后:" + decrypt);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * RSA校验数字签名
     *
     * @param data   待校验数据
     * @param sign   数字签名
     * @param pubKey 公钥
     * @return boolean 校验成功返回true，失败返回false
     */
    public boolean verify(byte[] data, byte[] sign, byte[] pubKey) throws Exception {
        // 实例化密钥工厂
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_KEY_ALGORITHM);
        // 初始化公钥
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(pubKey);
        // 产生公钥
        PublicKey publicKey = keyFactory.generatePublic(x509KeySpec);
        // 实例化Signature
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        // 初始化Signature
        signature.initVerify(publicKey);
        // 更新
        signature.update(data);
        // 验证
        return signature.verify(sign);
    }
}
