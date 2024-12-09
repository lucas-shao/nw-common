package com.normalworks.common.clickup.model;

import java.util.Set;

/**
 * ClickUpCustomFieldTypeEnum
 *
 * @author: lingeng
 * @date: 28/11/2023
 */
public enum ClickUpCustomFieldTypeEnum {

    DROP_DOWN("drop_down"),

    USERS("users"),

    TEXT("text"),

    SHORT_TEXT("short_text"),

    EMAIL("email"),

    PHONE("phone"),

    UNKNOWN("unknown");

    private final String clickUpCustomFieldType;

    ClickUpCustomFieldTypeEnum(String clickUpCustomFieldType) {
        this.clickUpCustomFieldType = clickUpCustomFieldType;
    }

    public static ClickUpCustomFieldTypeEnum getByClickUpCustomFieldType(String clickUpCustomFieldType) {
        for (ClickUpCustomFieldTypeEnum temp : values()) {
            if (temp.clickUpCustomFieldType.equals(clickUpCustomFieldType)) {
                return temp;
            }
        }
        return UNKNOWN;
    }

    public static Set<ClickUpCustomFieldTypeEnum> getTexts() {
        return Set.of(TEXT, SHORT_TEXT);
    }

    public String getClickUpCustomFieldType() {
        return clickUpCustomFieldType;
    }
}
