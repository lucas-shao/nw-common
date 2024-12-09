package com.normalworks.common.chat;

import com.normalworks.common.utils.assertion.AssertionResultCode;
import com.normalworks.common.utils.assertion.ErrorLevels;
import org.apache.http.HttpStatus;

public enum ChatResultCode implements AssertionResultCode {
    MESSAGES_IS_EMPTY("Message list is empty"),

    MESSAGE_IS_NULL("Message is null"),

    MESSAGE_ID_IS_NOT_NULL("Message id is should be null."),

    REQUEST_ID_IS_BLANK("Request id is blank."),

    SENDER_ID_IS_BLANK("Sender id is blank."),

    RECEIVER_ID_IS_BLANK("Receiver id is blank."),

    TOPIC_IS_BLANK("Topic is blank."),

    USER_ID_1_IS_BLANK("User id 1 is blank."),

    USER_ID_2_IS_BLANK(" User id 2 is blank."),

    MAX_SEND_TIME_IS_NULL("Max send time is null."),

    LIMIT_IS_ILLEGAL("Limit is illegal."),

    READ_TIME_IS_NOT_NULL("Read time should be null.");

    private final String errorLevel;
    private final String resultMsg;
    private final int httpStatus;

    ChatResultCode(String message) {
        this.errorLevel = ErrorLevels.WARN;
        this.httpStatus = HttpStatus.SC_BAD_REQUEST;
        this.resultMsg = message;
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
        return this.httpStatus;
    }
}
