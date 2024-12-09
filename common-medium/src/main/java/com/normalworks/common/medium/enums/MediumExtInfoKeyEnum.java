package com.normalworks.common.medium.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * MediumExtInfoKeyEnum
 * 媒体扩展信息KEY枚举
 *
 * @author : shaoshuai
 * @version V1.0
 * @date Date : 2022年01月01日 12:00 下午
 */
public enum MediumExtInfoKeyEnum {

    COVER_IMAGE_URL("COVER_IMAGE_URL", "封面首图URL"),
    ;

    public static MediumExtInfoKeyEnum getByValue(String mediumExtInfoKey) {
        for (MediumExtInfoKeyEnum enumValue : MediumExtInfoKeyEnum.values()) {
            if (StringUtils.equals(enumValue.getValue(), mediumExtInfoKey)) {
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

    MediumExtInfoKeyEnum(String value, String memo) {
        this.value = value;
        this.memo = memo;
    }

    private String value;
    private String memo;
}
