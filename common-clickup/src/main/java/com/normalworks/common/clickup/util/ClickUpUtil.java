package com.normalworks.common.clickup.util;

import com.normalworks.common.clickup.model.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * ClickUpUtil
 */
public class ClickUpUtil {

    /**
     * 根据任务名称获取任务
     */
    public static ClickUpTask getTaskByName(List<ClickUpTask> tasks, String taskName) {
        if (CollectionUtils.isEmpty(tasks)) {
            return null;
        }

        for (ClickUpTask task : tasks) {
            if (StringUtils.equalsIgnoreCase(task.getName(), taskName)) {
                return task;
            }
        }
        return null;
    }

    /**
     * 获取用户类且指定列名的用户
     */
    public static List<ClickUpUser> getUsers(List<ClickUpCustomField> customFields, String fieldName) {
        for (ClickUpCustomField customField : customFields) {
            if (customField.getTypeEnum() == ClickUpCustomFieldTypeEnum.USERS) {
                if (StringUtils.equals(customField.getName(), fieldName)) {
                    return (List<ClickUpUser>) customField.getCustomerFieldValues();
                }
            }
        }
        return null;
    }

    /**
     * 获取下拉框选中的值
     */
    public static String getDropDownValue(List<ClickUpCustomField> customFields, String dateInClickUpFormat) {
        if (CollectionUtils.isEmpty(customFields)) {
            return null;
        }

        for (ClickUpCustomField customField : customFields) {
            if (ClickUpCustomFieldTypeEnum.DROP_DOWN == customField.getTypeEnum()) {
                if (StringUtils.equals(customField.getName(), dateInClickUpFormat)) {
                    int selectedIndex = -1;
                    if (StringUtils.isNotBlank(customField.getValue())) {
                        selectedIndex = Integer.parseInt(customField.getValue());
                    }

                    ClickUpDropDownTypeConfig typeConfig = (ClickUpDropDownTypeConfig) customField.getTypeConfig();
                    for (ClickUpDropDownOption option : typeConfig.getOptions()) {
                        if (option.getOrderIndex() == selectedIndex) {
                            return StringUtils.trim(option.getName());
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * 获取文本框的值
     */
    public static String getTextValue(List<ClickUpCustomField> customFields, String fieldName) {
        if (CollectionUtils.isEmpty(customFields)) {
            return null;
        }

        for (ClickUpCustomField customField : customFields) {
            if (ClickUpCustomFieldTypeEnum.getTexts().contains(customField.getTypeEnum())) {
                if (StringUtils.equals(customField.getName(), fieldName)) {
                    return StringUtils.trim(customField.getValue());
                }
            }
        }
        return null;
    }

    /**
     * 获取Email的值
     */
    public static String getEmailValue(List<ClickUpCustomField> customFields, String fieldName) {
        if (CollectionUtils.isEmpty(customFields)) {
            return null;
        }

        for (ClickUpCustomField customField : customFields) {
            if (ClickUpCustomFieldTypeEnum.EMAIL == customField.getTypeEnum()) {
                if (StringUtils.equals(customField.getName(), fieldName)) {
                    return StringUtils.trim(customField.getValue());
                }
            }
        }
        return null;
    }

    /**
     * 获取电话号码的值
     */
    public static String getPhoneValue(List<ClickUpCustomField> customFields, String fieldName) {
        if (CollectionUtils.isEmpty(customFields)) {
            return null;
        }

        for (ClickUpCustomField customField : customFields) {
            if (ClickUpCustomFieldTypeEnum.PHONE == customField.getTypeEnum()) {
                if (StringUtils.equals(customField.getName(), fieldName)) {
                    return StringUtils.trim(customField.getValue());
                }
            }
        }
        return null;
    }

    /**
     * ClickUp 的时间戳字符串转化为Date
     */
    public static Date parseDate(String dateInClickUp) {
        return new Date(Long.parseLong(dateInClickUp));
    }

}
