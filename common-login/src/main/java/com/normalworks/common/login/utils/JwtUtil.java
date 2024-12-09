package com.normalworks.common.login.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.normalworks.common.utils.AssertUtil;
import com.normalworks.common.utils.assertion.AssertionException;
import com.normalworks.common.utils.assertion.CommonResultCode;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * JwtUtil
 * jwt工具类
 *
 * @author : shaoshuai.shao
 * @version : V1.0
 * @date : 2022/11/17 9:13 PM
 */
@Component
public class JwtUtil {

    /**
     * 生成Token
     *
     * @param subject              存储在Token中的内容信息
     * @param tokenExpiredInSecond token 有效时长
     * @param tokenSecret
     * @return 生成的Token
     */
    public static String createToken(String subject, long tokenExpiredInSecond, String tokenSecret) {
        return JWT.create().withSubject(subject).withExpiresAt(new Date(System.currentTimeMillis() + tokenExpiredInSecond * 1000L)).sign(Algorithm.HMAC512(tokenSecret));
    }

    /**
     * 验证Token
     *
     * @param token       待验证的Token
     * @param tokenSecret Token的密钥
     * @return 存储在Token中的subject内容
     */
    public static String validateToken(String token, String tokenSecret) {
        try {
            AssertUtil.notBlank(token, CommonResultCode.PARAM_ILLEGAL, "token为空,token = " + token);
            return JWT.require(Algorithm.HMAC512(tokenSecret)).build().verify(token).getSubject();
        } catch (TokenExpiredException tokenExpiredException) {
            throw new AssertionException(CommonResultCode.JWT_EXPIRED, "JWT已经过期，Token = " + token);
        } catch (Exception exception) {
            throw new AssertionException(CommonResultCode.JWT_ILLEGAL, "JWT格式非法，Token = " + token);
        }
    }

    /**
     * 不校验Token，直接获取Token中的subject内容
     */
    public static String getSubjectWithoutValidate(String token) {
        DecodedJWT decodeJwt = JWT.decode(token);
        return decodeJwt.getSubject();
    }
}
