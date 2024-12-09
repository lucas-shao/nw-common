package com.normalworks.common.utils.assertion;

import org.apache.http.HttpStatus;

/**
 * CommonResultCode
 *
 * @author: lingeng
 * @date: 8/26/22
 */
public enum CommonResultCode implements AssertionResultCode {

    SUCCESS(ErrorLevels.INFO, HttpStatus.SC_OK, "biz success"),

    THIRD_PARTY_EXCEPTION(ErrorLevels.ERROR, HttpStatus.SC_INTERNAL_SERVER_ERROR, "第三方调用异常"),

    SYSTEM_EXCEPTION(ErrorLevels.ERROR, HttpStatus.SC_INTERNAL_SERVER_ERROR, "发生系统异常"),

    UNKNOWN_EXCEPTION(ErrorLevels.ERROR, HttpStatus.SC_INTERNAL_SERVER_ERROR, "发生未知异常"),

    IDEMPOTENT(ErrorLevels.WARN, HttpStatus.SC_OK, "幂等成功"),

    PARAM_ILLEGAL(ErrorLevels.WARN, HttpStatus.SC_BAD_REQUEST, "参数异常"),

    JWT_EXPIRED(ErrorLevels.WARN, HttpStatus.SC_BAD_REQUEST, "JWT已过期"),

    JWT_ILLEGAL(ErrorLevels.WARN, HttpStatus.SC_BAD_REQUEST, "JWT格式不正确"),

    JWT_NOT_EXIST(ErrorLevels.WARN, HttpStatus.SC_BAD_REQUEST, "JWT不存在"),

    JWT_CACHE_NOT_EXIST(ErrorLevels.WARN, HttpStatus.SC_BAD_REQUEST, "缓存中不存在JWT"),

    CACHE_DELETE_FAILED(ErrorLevels.ERROR, HttpStatus.SC_INTERNAL_SERVER_ERROR, "缓存删除失败"),

    RETRY_TASK_EXECUTOR_IS_NULL(ErrorLevels.ERROR, HttpStatus.SC_INTERNAL_SERVER_ERROR, "重试任务执行器不存在"),

    UPLOAD_MEDIUM_FAILED(ErrorLevels.ERROR, HttpStatus.SC_INTERNAL_SERVER_ERROR, "上传媒体文件失败"),

    IMAGE_PROCESS_FAILED(ErrorLevels.ERROR, HttpStatus.SC_INTERNAL_SERVER_ERROR, "图片处理失败"),

    PDF_PROCESS_FAILED(ErrorLevels.ERROR, HttpStatus.SC_INTERNAL_SERVER_ERROR, "PDF处理失败"),

    ACCESS_EMAIL_SERVER_FAILED(ErrorLevels.ERROR, HttpStatus.SC_INTERNAL_SERVER_ERROR, "访问邮件服务器失败"),

    ACCESS_EMAIL_SERVER_AUTH_FAILED(ErrorLevels.WARN, HttpStatus.SC_BAD_REQUEST, "访问邮件服务器认证失败"),

    FETCH_EMAIL_MESSAGE_FAILED(ErrorLevels.WARN, HttpStatus.SC_BAD_REQUEST, "获取邮件信息失败"),

    LOGIN_OTP_CACHE_NOT_EXIST(ErrorLevels.WARN, HttpStatus.SC_BAD_REQUEST, "登录OTP缓存不存在"),

    LOGIN_OTP_NOT_MATCH(ErrorLevels.WARN, HttpStatus.SC_BAD_REQUEST, "登录OTP不匹配"),

    LOGIN_ID_NOT_MATCH(ErrorLevels.WARN, HttpStatus.SC_BAD_REQUEST, "登录号不匹配"),

    ZIP_DIR_FAILED(ErrorLevels.WARN, HttpStatus.SC_BAD_REQUEST, "压缩目录失败"),

    APPEND_FILE_FAILED(ErrorLevels.WARN, HttpStatus.SC_INTERNAL_SERVER_ERROR, "追加文件失败"),

    GOOGLE_FIREBASE_INIT_FAILED(ErrorLevels.WARN, HttpStatus.SC_INTERNAL_SERVER_ERROR, "Google Firebase初始化失败"),
    ;

    private final String errorLevel;
    private final String resultMsg;

    private int httpStatus;

    CommonResultCode(String errorLevel, int httpStatus, String resultMsg) {
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
