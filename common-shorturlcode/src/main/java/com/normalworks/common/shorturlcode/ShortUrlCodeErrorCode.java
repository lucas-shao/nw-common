package com.normalworks.common.shorturlcode;

import com.normalworks.common.utils.assertion.AssertionResultCode;
import com.normalworks.common.utils.assertion.ErrorLevels;
import org.apache.http.HttpStatus;

/**
 * ShortUrlCodeErrorCode
 *
 * @author: lingeng
 * @date: 07/03/2023
 */
public enum ShortUrlCodeErrorCode implements AssertionResultCode {
    SHORT_URL_CODE_NOT_EXIST(ErrorLevels.WARN, HttpStatus.SC_BAD_REQUEST, "短链码不存在"),

    GENERATE_SHORT_CODE_ERROR(ErrorLevels.WARN, HttpStatus.SC_BAD_REQUEST, "生成短码失败");

    private String errorLevel;
    private String resultMsg;
    private int httpStatus;

    ShortUrlCodeErrorCode(String errorLevel, int httpStatus, String resultMsg) {
        this.errorLevel = errorLevel;
        this.httpStatus = httpStatus;
        this.resultMsg = resultMsg;
    }

    @Override
    public String getResultCode() {
        return name();
    }

    @Override
    public String getErrorLevel() {
        return errorLevel;
    }

    @Override
    public String getResultMsg() {
        return resultMsg;
    }

    @Override
    public int getHttpStatus() {
        return httpStatus;
    }
}
