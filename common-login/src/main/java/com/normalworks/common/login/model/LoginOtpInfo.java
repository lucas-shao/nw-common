package com.normalworks.common.login.model;

import com.normalworks.common.utils.model.BaseModel;

/**
 * LoginOtpInfo
 * 登录Otp信息
 *
 * @author : shaoshuai.shao
 * @version : V1.0
 * @date : 2024/2/1 2:57 PM
 */
public class LoginOtpInfo extends BaseModel {

    /**
     * 申请Otp的请求Id
     **/
    private String requestId;

    /**
     * 请求OTP登录的登录号ID
     **/
    private String loginId;

    /**
     * Otp值
     **/
    private String OtpValue;

    public String getRequestId() {
        return requestId == null ? null : requestId.trim();
    }

    public void setRequestId(String requestId) {
        this.requestId = (requestId == null) ? null : requestId.trim();
    }

    public String getLoginId() {
        return loginId == null ? null : loginId.trim();
    }

    public void setLoginId(String loginId) {
        this.loginId = (loginId == null) ? null : loginId.trim();
    }

    public String getOtpValue() {
        return OtpValue == null ? null : OtpValue.trim();
    }

    public void setOtpValue(String otpValue) {
        OtpValue = (otpValue == null) ? null : otpValue.trim();
    }
}
