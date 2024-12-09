package com.normalworks.common.utils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class HmacUtil {

    /**
     * 校验HMAC一致性
     *
     * @param data        需要校验的数据
     * @param secret      用于生成HMAC的密钥
     * @param hmacToCheck 传入的HMAC，需要与生成的HMAC进行比较
     * @return 校验是否一致
     */
    public static boolean validateHmac(String data, String secret, String hmacToCheck) {
        try {
            // 使用HmacSHA256算法初始化Mac对象
            String algorithm = "HmacSHA256";
            Mac mac = Mac.getInstance(algorithm);
            SecretKeySpec secretKeySpec = new SecretKeySpec(secret.getBytes(), algorithm);
            mac.init(secretKeySpec);

            // 生成数据的HMAC
            byte[] rawHmac = mac.doFinal(data.getBytes());

            // 将生成的HMAC转换为十六进制字符串或Base64编码，这取决于你如何与传入的HMAC进行比较
            String generatedHmac = Base64.getEncoder().encodeToString(rawHmac);

            // 比较生成的HMAC与传入的HMAC是否一致
            return generatedHmac.equals(hmacToCheck);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
            return false;
        }
    }

}
