package com.normalworks.common.login.impl;

import com.normalworks.common.login.LoginConfigCallback;
import com.normalworks.common.login.LoginDomainService;
import com.normalworks.common.login.client.LoginCacheClient;
import com.normalworks.common.login.config.LoginConfig;
import com.normalworks.common.login.model.LoginOtpInfo;
import com.normalworks.common.login.utils.JwtUtil;
import com.normalworks.common.utils.AssertUtil;
import com.normalworks.common.utils.DateUtil;
import com.normalworks.common.utils.LoggerUtil;
import com.normalworks.common.utils.OtpUtil;
import com.normalworks.common.utils.assertion.AssertionException;
import com.normalworks.common.utils.assertion.CommonResultCode;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * LoginDomainServiceImpl
 *
 * @author : shaoshuai
 * @version V1.0
 * @date Date : 2021年12月05日 9:12 下午
 */
@Service
public class LoginDomainServiceImpl implements LoginDomainService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginDomainServiceImpl.class);

    @Resource
    private LoginCacheClient loginCacheClient;

    @Resource
    private LoginConfig loginConfig;

    @Override
    public String login(String userId, LoginConfigCallback callback) throws AssertionException {
        String jwtToken = JwtUtil.createToken(userId, callback.getTokenExpiredInSecond(), loginConfig.getTokenSecret());
        loginCacheClient.set(jwtToken, jwtToken, callback.getTokenExpiredInSecond());

        setLastVisitTime(userId, DateUtil.curr(), callback);

        //返回带前缀的token字符串
        return callback.getTokenPrefix() + jwtToken;
    }

    @Override
    public String validateLogin(String token, LoginConfigCallback callback) throws AssertionException {

        AssertUtil.notBlank(token, CommonResultCode.JWT_NOT_EXIST, "jwtToken在请求头中为空，需要重新登录");

        String jwtToken = getJwtToken(token, callback);

        /*
         * 校验阶段
         * 对redis弱依赖
         * 如果redis挂了，则使用jwtToken本身的有效期来校验，校验不通过则直接抛出异常
         */
        String cacheToken;
        try {
            // 从缓存中获取value值，也是jwtToken
            cacheToken = (String) loginCacheClient.get(jwtToken);
        } catch (RuntimeException e) {
            LoggerUtil.error(e, "从redis获取token失败，直接校验jwtToken的有效性。");
            return JwtUtil.validateToken(jwtToken, loginConfig.getTokenSecret());
        }

        /*
         * redis 没挂，但是缓存中没有token，则抛出异常。
         * 因为登出时会删除缓存中的token，所以缓存中没有token，说明已经登出，而且不能有jwtToken来校验
         */
        AssertUtil.notBlank(cacheToken, CommonResultCode.JWT_NOT_EXIST, "缓存中不存在token，token=" + jwtToken);

        /*
         *校验缓存Value的jwtToken
         */
        Date curr = DateUtil.curr();
        try {
            String userId = JwtUtil.validateToken(cacheToken, loginConfig.getTokenSecret());

            setLastVisitTime(userId, curr, callback);

            LoggerUtil.info(LOGGER, "校验通过，更新最近访问时间，token=", jwtToken, ",userId=", userId);

            return userId;
        } catch (AssertionException assertionException) {
            if (assertionException.getResultCode() == CommonResultCode.JWT_EXPIRED) {

                String userId = JwtUtil.getSubjectWithoutValidate(jwtToken);
                Date lastVisitTime = (Date) loginCacheClient.get(getLastVisitTimeCacheKey(userId, callback));

                LoggerUtil.info(LOGGER, "token过期，lastVisitTime=", lastVisitTime, ",autoUpdateTokenIntervalInSecond=", callback.getAutoUpdateTokenIntervalInSecond());

                if (lastVisitTime != null) {

                    /*
                     * n分钟内访问过
                     * 延长token有效期
                     */
                    if (curr.getTime() - lastVisitTime.getTime() < callback.getAutoUpdateTokenIntervalInSecond() * 1000) {

                        LoggerUtil.info(LOGGER, "token过期，但是在访问间隔时长在允许范围内，生成新的token. current=", curr, ",lastVisitTime=", lastVisitTime, ",autoUpdateTokenIntervalInSecond=", callback.getAutoUpdateTokenIntervalInSecond());

                        //生成新的token
                        updateCacheToken(jwtToken, callback.getTokenExpiredInSecond(), userId);

                        //更新最近访问时间
                        setLastVisitTime(userId, curr, callback);

                        return userId;
                    }

                }

                /*
                 * 如果无法自动更新token，直接向上抛出异常
                 */
                LoggerUtil.warn("JWT超时，不允许自动更新token，向上抛出异常", assertionException.getResultCode());
                throw assertionException;
            }

            /*
             * 其他异常继续往上抛
             */
            LoggerUtil.warn("JWT校验失败，向上抛出异常", assertionException.getResultCode());
            throw assertionException;

        }

    }


    @Override
    public String logout(String token, LoginConfigCallback callback) throws AssertionException {

        //不含前缀的token
        String jwtToken = getJwtToken(token, callback);

        // 校验JWT合法性
        String userId;
        try {
            userId = JwtUtil.validateToken(jwtToken, loginConfig.getTokenSecret());
        } catch (AssertionException assertionException) {
            // 如果jwtToken不合法，则继续往上抛异常
            if (assertionException.getResultCode() == CommonResultCode.JWT_EXPIRED) {
                userId = JwtUtil.getSubjectWithoutValidate(jwtToken);
            } else {
                throw assertionException;
            }
        }

        // 删除缓存数据
        boolean result = loginCacheClient.del(jwtToken);
        AssertUtil.isTrue(result, CommonResultCode.CACHE_DELETE_FAILED, "缓存删除失败，key = " + jwtToken);

        return userId;
    }

    @Override
    public LoginOtpInfo generateLoginOtpInfo(String requestId, String loginId, Integer otpDigitNumbers) throws AssertionException {

        // 生成OTP值
        String otpValue = OtpUtil.randomGenerateOtpValue(otpDigitNumbers);

        // 构建LoginOtpInfo
        LoginOtpInfo loginOtpInfo = new LoginOtpInfo();
        loginOtpInfo.setRequestId(requestId);
        loginOtpInfo.setLoginId(loginId);
        loginOtpInfo.setOtpValue(otpValue);

        // 放置缓存
        String loginOtpCacheKey = genLoginOtpCacheKey(requestId);
        loginCacheClient.set(loginOtpCacheKey, loginOtpInfo, loginConfig.getLoginOtpExpire() * 60);

        return loginOtpInfo;
    }

    @Override
    public void checkLoginOtp(String requestId, String loginId, String otpValue) throws AssertionException {

        // 校验OTP值
        String loginOtpCacheKey = genLoginOtpCacheKey(requestId);
        LoginOtpInfo loginOtpInfo = (LoginOtpInfo) loginCacheClient.get(loginOtpCacheKey);
        AssertUtil.notNull(loginOtpInfo, CommonResultCode.LOGIN_OTP_CACHE_NOT_EXIST, "登录OTP缓存不存在，loginOtpCacheKey = " + loginOtpCacheKey);
        AssertUtil.isTrue(StringUtils.equalsIgnoreCase(loginOtpInfo.getOtpValue(), otpValue), CommonResultCode.LOGIN_OTP_NOT_MATCH, "登录OTP不匹配，loginOtpInfo = " + loginOtpInfo + "，otpValue = " + otpValue);
        AssertUtil.isTrue(StringUtils.equalsIgnoreCase(loginOtpInfo.getLoginId(), loginId), CommonResultCode.LOGIN_ID_NOT_MATCH, "登录号不匹配，loginOtpInfo = " + loginOtpInfo + "，loginId = " + loginId);

        LOGGER.info("checkLoginOtp success, requestId = " + requestId + ", loginId = " + loginId + ", otpValue = " + otpValue);
    }

    @Override
    public String loginWithOtp(String requestId, String loginId, String otpValue, String userId, LoginConfigCallback callback) throws AssertionException {
        // 校验OTP值
        checkLoginOtp(requestId, loginId, otpValue);

        // 登录
        return login(userId, callback);
    }

    @Override
    public Date fetchLastVisitTime(String userId, LoginConfigCallback callback) throws AssertionException {

        Date lastVisitTime = (Date) loginCacheClient.get(getLastVisitTimeCacheKey(userId, callback));
        return lastVisitTime;
    }

    private String genLoginOtpCacheKey(String requestId) {
        return loginConfig.getTokenPrefix() + "OTP_" + requestId;
    }

    /**
     * 更新缓存的最近访问时间
     *
     * @param id            id
     * @param lastVisitTime 最近访问时间
     * @param callback      从callback获取自动更新缓存的间隔时间。上一次访问时间的缓存过期时间应该大于这个间隔时间，设置成这个间隔时间的2倍
     */
    private void setLastVisitTime(String id, Date lastVisitTime, LoginConfigCallback callback) {
        loginCacheClient.set(getLastVisitTimeCacheKey(id, callback), lastVisitTime, callback.getAutoUpdateTokenIntervalInSecond() * 2);
    }

    private static String getLastVisitTimeCacheKey(String id, LoginConfigCallback callback) {
        return callback.getTokenPrefix() + id + "_last_visit_time";
    }

    private void updateCacheToken(String jwtToken, long tokenExpiredInSecond, String userId) {
        String newJwtToken = JwtUtil.createToken(userId, tokenExpiredInSecond, loginConfig.getTokenSecret());
        loginCacheClient.set(jwtToken, newJwtToken, tokenExpiredInSecond);

    }

    /**
     * 获取jwtToken ,去掉token前缀
     */
    private static String getJwtToken(String jwtToken, LoginConfigCallback callback) {
        String token = jwtToken.replace(callback.getTokenPrefix(), "");

        LoggerUtil.info(LOGGER, "开始校验jwtToken=", jwtToken, ",token=", token);
        return token;
    }
}
