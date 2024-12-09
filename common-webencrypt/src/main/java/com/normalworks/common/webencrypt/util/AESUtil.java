package com.normalworks.common.webencrypt.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

/**
 * AESUtil
 * AES 加解密工具类
 * 使用密钥时请使用 initKey() 方法来生成随机密钥
 * initKey 方法内部使用 java.crypto.KeyGenerator 密钥生成器来生成特定于 AES 算法参数集的随机密钥
 * 使用密钥生成器生成的密钥能保证更强的随机性
 * 生成的二进制密钥建议使用 Hex 进行编码
 *
 * @author : shaoshuai
 * @version V1.0
 * @date Date : 2022年04月07日 1:23 下午
 */
public class AESUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(AESUtil.class);

    /**
     * 密钥算法类型
     */
    private static final String KEY_ALGORITHM = "AES";

    /**
     * 密钥的默认位长度
     */
    private static final int DEFAULT_KEY_SIZE = 128;

    /**
     * 加解密算法/工作模式/填充方式
     */
    private static final String ECB_PKCS_7_PADDING = "AES/ECB/PKCS7Padding";

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    private AESUtil() {
        // 关闭构造
    }

    /**
     * 生成 Hex 格式默认长度的随机密钥
     * 字符串长度为 32，解二进制后为 16 个字节
     *
     * @return String Hex 格式的随机密钥
     */
    public static String initHexKey() {
        return Hex.encodeHexString(initKey());
    }

    /**
     * 给内容进行AES算法的加密
     *
     * @param content   待加密的内容
     * @param hexAesKey AES密钥key
     * @return
     * @throws Exception
     */
    public static String aesEncrypt(String content, String hexAesKey) {
        try {
            if (StringUtils.isBlank(content)) {
                return content;
            }
            return base64Encode(aesEncryptToBytes(content, hexAesKey));
        } catch (Exception exception) {
            LOGGER.error("AES 加密失败, content=" + content + " , hexAesKey=" + hexAesKey, exception);
            throw new RuntimeException(exception);
        }
    }

    /**
     * 给内容进行AES算法的解密
     *
     * @param encryptContent 待解密的内容
     * @param hexAesKey      AES密钥key
     * @return 解密出来的内容
     * @throws Exception
     */
    public static String aesDecrypt(String encryptContent, String hexAesKey) {
        try {
            if (StringUtils.isBlank(encryptContent)) {
                return encryptContent;
            }
            return aesDecryptByBytes(base64Decode(encryptContent), hexAesKey);
        } catch (Exception exception) {
            LOGGER.error("AES 解密失败, encryptContent=" + encryptContent + " , hexAesKey=" + hexAesKey, exception);
            throw new RuntimeException(exception);
        }
    }

    /**
     * 生成默认长度的随机密钥
     * 默认长度为 128
     *
     * @return byte[] 二进制密钥
     */
    private static byte[] initKey() {
        return initKey(DEFAULT_KEY_SIZE);
    }

    /**
     * 生成密钥
     * 128、192、256 可选
     *
     * @param keySize 密钥长度
     * @return byte[] 二进制密钥
     */
    private static byte[] initKey(int keySize) {
        // AES 要求密钥长度为 128 位、192 位或 256 位
        if (keySize != 128 && keySize != 192 && keySize != 256) {
            throw new RuntimeException("error keySize: " + keySize);
        }
        // 实例化
        KeyGenerator keyGenerator;
        try {
            keyGenerator = KeyGenerator.getInstance(KEY_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("no such algorithm exception: " + KEY_ALGORITHM, e);
        }
        keyGenerator.init(keySize);
        // 生成秘密密钥
        SecretKey secretKey = keyGenerator.generateKey();
        // 获得密钥的二进制编码形式
        return secretKey.getEncoded();
    }

    private static String base64Encode(byte[] bytes) {
        return Base64.encodeBase64String(bytes);
    }

    private static byte[] base64Decode(String base64Code) {
        return Base64.decodeBase64(base64Code);
    }

    private static String aesDecryptByBytes(byte[] encryptBytes, String hexAesKey) throws Exception {
        byte[] decrypt = decrypt(encryptBytes, Hex.decodeHex(hexAesKey.toCharArray()));
        return new String(decrypt, StandardCharsets.UTF_8);
    }

    private static byte[] aesEncryptToBytes(String content, String hexAesKey) throws Exception {
        return encrypt(content.getBytes(StandardCharsets.UTF_8), Hex.decodeHex(hexAesKey.toCharArray()));
    }

    /**
     * 解密
     *
     * @param data 待解密数据
     * @param key  密钥
     * @return byte[] 解密的数据
     */
    private static byte[] decrypt(byte[] data, byte[] key) {
        return decrypt(data, key, ECB_PKCS_7_PADDING);
    }

    /**
     * 解密
     *
     * @param data            待解密数据
     * @param key             密钥
     * @param cipherAlgorithm 算法/工作模式/填充模式
     * @return byte[] 解密的数据
     */
    private static byte[] decrypt(byte[] data, byte[] key, final String cipherAlgorithm) {
        // 还原密钥
        Key k = toKey(key);
        try {
            Cipher cipher = Cipher.getInstance(cipherAlgorithm);
            // 初始化，设置为解密模式
            cipher.init(Cipher.DECRYPT_MODE, k);

            // 执行操作
            return cipher.doFinal(data);
        } catch (Exception e) {
            throw new RuntimeException("AES decrypt error", e);
        }
    }

    /**
     * 加密
     *
     * @param data 待加密数据
     * @param key  密钥
     * @return byte[] 加密的数据
     */
    private static byte[] encrypt(byte[] data, byte[] key) {
        return encrypt(data, key, ECB_PKCS_7_PADDING);
    }

    /**
     * 加密
     *
     * @param data            待加密数据
     * @param key             密钥
     * @param cipherAlgorithm 算法/工作模式/填充模式
     * @return byte[] 加密的数据
     */
    private static byte[] encrypt(byte[] data, byte[] key, final String cipherAlgorithm) {
        // 还原密钥
        Key k = toKey(key);
        try {
            Cipher cipher = Cipher.getInstance(cipherAlgorithm);
            // 初始化，设置为加密模式
            cipher.init(Cipher.ENCRYPT_MODE, k);

            // 执行操作
            return cipher.doFinal(data);
        } catch (Exception e) {
            throw new RuntimeException("AES encrypt error", e);
        }
    }

    /**
     * 转换密钥
     *
     * @param key 二进制密钥
     * @return Key 密钥
     */
    private static Key toKey(byte[] key) {
        // 实例化 DES 密钥材料
        return new SecretKeySpec(key, KEY_ALGORITHM);
    }

    public static void main(String[] args) {

        try {

            // AES加密密钥
            String aesKey = "add69b69ebc66cda4f64939f76d1b3fa";

            String content = "{\n" +
                    "  \"briefIntroduction\": \"聊一聊，看一看，哈雷摩托突突突\",\n" +
                    "  \"clubId\": \"3\"\n" +
                    "}";

            System.out.println("加密前内容 content =" + content);
            System.out.println("加密后内容 encryptContent =" + AESUtil.aesEncrypt(content, aesKey));

            String encryptContent = "MljuOBzplX6yGNslKrTZj5+aPWAQjiZ+m5/5UFG8qT+sxgKEDaNJ5QrQ7N7v9BZamwq/xrwB2kwg/h9BJDWFt6OWgbQtRlt1or1BQzFqvdXCDCshuCN2PkCsg4Sg5OSUmN8gI699HPVrA/71QtCsF4peIWStXfvCGZYFQTH+8kRxoWVnbpoiZh9+d1xrhs4CELCa9+F6X2cEeKCS8SERMK1w6G660DDbCHp4fT8poz2ootM4GJRtnkgSaWd8ZRNGKix2kPJZWe2ZO9F5rI7jBw==";
            content = AESUtil.aesDecrypt(encryptContent, aesKey);
            System.out.println("解密后的content:" + content);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
