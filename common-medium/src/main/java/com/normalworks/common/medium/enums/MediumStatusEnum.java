package com.normalworks.common.medium.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * MediumStatusEnum
 * 媒体状态
 *
 * @author : shaoshuai
 * @version V1.0
 * @date Date : 2021年11月19日 6:36 下午
 */
public enum MediumStatusEnum {

    NOT_AUDIT("NOT_AUDIT", "未审核"),

    AUDITING("AUDITING", "机器审核中"),

    MANUAL_AUDITING("MANUAL_AUDITING", "人工审核中"),

    AUDIT_FAILED("AUDIT_FAILED", "审核失败"),

    RELEASE("RELEASE", "已发布"),

    REMOVED("REMOVED", "已被删除"),

    ;

    public static MediumStatusEnum getByValue(String mediumStatus) {
        for (MediumStatusEnum enumValue : MediumStatusEnum.values()) {
            if (StringUtils.equals(enumValue.getValue(), mediumStatus)) {
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

    MediumStatusEnum(String value, String memo) {
        this.value = value;
        this.memo = memo;
    }

    private String value;
    private String memo;
}
