package com.normalworks.common.task.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * TaskPriorityEnum
 * 任务优先级枚举项
 *
 * @author : shaoshuai.shao
 * @version : V1.0
 * @date : 2024/4/12 11:11
 */
public enum TaskPriorityEnum {

    HIGH("HIGH", "高优先级，有高时效性要求的"),

    MEDIUM("MEDIUM", "中优先级，一般普通的任务默认为中优先级"),

    LOW("LOW", "低优先级，一般后台数据处理比较耗时的"),
    ;

    public static TaskPriorityEnum getByValue(String loginStatusType) {
        for (TaskPriorityEnum enumValue : TaskPriorityEnum.values()) {
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

    TaskPriorityEnum(String value, String memo) {
        this.value = value;
        this.memo = memo;
    }

    private String value;
    private String memo;
}
