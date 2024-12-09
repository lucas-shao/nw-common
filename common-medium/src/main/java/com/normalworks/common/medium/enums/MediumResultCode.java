package com.normalworks.common.medium.enums;

import com.normalworks.common.utils.assertion.AssertionResultCode;
import com.normalworks.common.utils.assertion.ErrorLevels;
import org.apache.http.HttpStatus;

/**
 * MediumResultCode
 *
 * @author: lingeng
 * @date: 09/01/2023
 */
public enum MediumResultCode implements AssertionResultCode {
    MEDIUM_IMAGE_TYPE_UNSUPPORTED(ErrorLevels.WARN, HttpStatus.SC_BAD_REQUEST, "图片类型不支持"),

    MEDIUM_GEN_DIGEST_SIZE_FAILED(ErrorLevels.WARN, HttpStatus.SC_BAD_REQUEST, "生成文件摘要和大小失败"),

    MEDIUM_GET_IMAGE_INFO_FAILED(ErrorLevels.WARN, HttpStatus.SC_BAD_REQUEST, "获取图片信息失败");

    private final String errorLevel;
    private final String resultMsg;

    private int httpStatus;

    MediumResultCode(String errorLevel, int httpStatus, String resultMsg) {
        this.errorLevel = errorLevel;
        this.httpStatus = httpStatus;
        this.resultMsg = resultMsg;
    }

    @Override
    public String getResultCode() {
        return this.name();
    }

    @Override
    public String getErrorLevel() {
        return this.errorLevel;
    }

    @Override
    public String getResultMsg() {
        return this.resultMsg;
    }

    @Override
    public int getHttpStatus() {
        return httpStatus;
    }
}
