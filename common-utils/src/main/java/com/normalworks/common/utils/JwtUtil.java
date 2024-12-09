package com.normalworks.common.utils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

/**
 * JwtUtil
 * jwt通用工具类
 *
 * @author : shaoshuai.shao
 * @version : V1.0
 * @date : 2024/3/15 11:36
 */
public class JwtUtil {

    /**
     * 生成JWT
     */
    public static String createJwt(String keyId, Map<String, Object> payload, Algorithm algorithm, long tokenExpireInSeconds) {
        return JWT.create().withPayload(payload).withKeyId(keyId)
                .withExpiresAt(new Date(System.currentTimeMillis() + tokenExpireInSeconds * 1000L))
                .sign(algorithm);
    }

    /**
     * 验证并解码JWT的Header
     */
    public static String verifyAndDecodeJwtHeader(String jwt, Algorithm algorithm) {
        DecodedJWT decodedJWT = JWT.require(algorithm).build().verify(jwt);
        return new String(Base64.getUrlDecoder().decode(decodedJWT.getHeader()), StandardCharsets.UTF_8);
    }

    /**
     * 解码JWT的Header，不验证是否加密正确有篡改
     */
    public static String decodeJwtHeader(String jwt) {
        DecodedJWT decodedJWT = JWT.decode(jwt);
        return new String(Base64.getUrlDecoder().decode(decodedJWT.getHeader()), StandardCharsets.UTF_8);
    }

    /**
     * 验证并解码JWT的Payload
     */
    public static String verifyAndDecodeJwtPayload(String jwt, Algorithm algorithm) {
        DecodedJWT decodedJWT = JWT.require(algorithm).build().verify(jwt);
        return new String(Base64.getUrlDecoder().decode(decodedJWT.getPayload()), StandardCharsets.UTF_8);
    }

    /**
     * 只解码JWT的Payload，不验证是否加密正确有篡改
     */
    public static String decodeJwtPayload(String jwt) {
        DecodedJWT decodedJWT = JWT.decode(jwt);
        return new String(Base64.getUrlDecoder().decode(decodedJWT.getPayload()), StandardCharsets.UTF_8);
    }

    public static void main(String[] args) {
        String secretKeyId = "app_65f3baa7e0c074f4fc1af09a";
        String secret = "nyGwL8KESMvUWxnuW1_Gc-se62nFUQTWOu-_VpOEy9B1xJhB-xaLLbMIOpunp3wO8FC0kEv9zQLtnzX9YiRBfQ";
        Algorithm algorithm = Algorithm.HMAC256(secret);
        Map<String, Object> payload = new HashMap<>();
        payload.put("external_id", "12345678");
        payload.put("email", "janes@soap.com");
        payload.put("email_verified", true);
        payload.put("name", "jane Soap");
        payload.put("scope", "user");

        String jwt = createJwt(secretKeyId, payload, algorithm, 60);
        System.out.println("JWT: " + jwt);

        System.out.println("Header: " + verifyAndDecodeJwtHeader(jwt, algorithm));
        System.out.println("Payload: " + verifyAndDecodeJwtPayload(jwt, algorithm));
    }
}
