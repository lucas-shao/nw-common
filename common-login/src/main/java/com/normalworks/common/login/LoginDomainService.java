package com.normalworks.common.login;

import com.normalworks.common.login.model.LoginOtpInfo;
import com.normalworks.common.utils.assertion.AssertionException;

import java.util.Date;

/**
 * LoginDomainService
 * 登录领域服务
 *
 * @author : shaoshuai
 * @version V1.0
 * @date Date : 2021年12月04日 5:10 下午
 */
public interface LoginDomainService {

    /**
     * 登录服务
     *
     * @return 返回含前缀的token
     */
    String login(String userId, LoginConfigCallback callback) throws AssertionException;

    /**
     * 验证登录
     *
     * @param token 含前缀的token
     */
    String validateLogin(String token, LoginConfigCallback callback) throws AssertionException;


    /**
     * 登出服务，将JWT在内部缓存失效掉
     *
     * @param token    含前缀的token
     * @param callback
     * @return
     * @throws AssertionException
     */
    String logout(String token, LoginConfigCallback callback) throws AssertionException;

    /**
     * 生成登录的OTP信息
     *
     * @param requestId       请求ID
     * @param loginId         登录号ID
     * @param otpDigitNumbers OTP位数
     * @return 登录的OTP信息
     * @throws AssertionException
     */
    LoginOtpInfo generateLoginOtpInfo(String requestId, String loginId, Integer otpDigitNumbers) throws AssertionException;

    /**
     * 校验登录的OTP的正确性
     *
     * @param requestId 请求ID
     * @param loginId   登录号ID
     * @param otpValue  OTP值
     * @throws AssertionException
     */
    void checkLoginOtp(String requestId, String loginId, String otpValue) throws AssertionException;

    /**
     * 使用OTP登录
     */
    String loginWithOtp(String requestId, String loginId, String otpValue, String userId, LoginConfigCallback callback) throws AssertionException;

    /**
     * 获取用户最近访问时间
     */
    Date fetchLastVisitTime(String userId, LoginConfigCallback callback) throws AssertionException;
}
