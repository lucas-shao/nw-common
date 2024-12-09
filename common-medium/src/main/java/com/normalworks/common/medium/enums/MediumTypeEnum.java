package com.normalworks.common.medium.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * MediumTypeEnum
 * 媒体类型
 *
 * @author : shaoshuai
 * @version V1.0
 * @date Date : 2021年11月19日 6:28 下午
 */
public enum MediumTypeEnum {

    TEXT("TEXT", "文字"),

    IMAGE("IMAGE", "图片"),

    AUDIO("AUDIO", "音频"),

    VIDEO("VIDEO", "视频"),

    FILE("FILE", "文件，比如PDF/WORD/PPT等"),

    ;

    public static MediumTypeEnum getByValue(String mediumType) {
        for (MediumTypeEnum enumValue : MediumTypeEnum.values()) {
            if (StringUtils.equals(enumValue.getValue(), mediumType)) {
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

    MediumTypeEnum(String value, String memo) {
        this.value = value;
        this.memo = memo;
    }

    private String value;
    private String memo;
}
