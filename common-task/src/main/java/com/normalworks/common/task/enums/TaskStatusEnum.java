package com.normalworks.common.task.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * TaskStatusEnum
 * 任务状态枚举项
 *
 * @author : shaoshuai.shao
 * @version : V1.0
 * @date : 2022/11/11 9:53 PM
 */
public enum TaskStatusEnum {

    RUNNING("RUNNING", "运行中"),

    STOP("STOP", "停止");

    public static TaskStatusEnum getByValue(String loginStatusType) {
        for (TaskStatusEnum enumValue : TaskStatusEnum.values()) {
            if (StringUtils.equals(enumValue.getValue(), loginStatusType)) {
                return enumValue;
            }
        }
        return null;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    TaskStatusEnum(String value, String memo) {
        this.value = value;
        this.memo = memo;
    }

    private String value;
    private String memo;
}
