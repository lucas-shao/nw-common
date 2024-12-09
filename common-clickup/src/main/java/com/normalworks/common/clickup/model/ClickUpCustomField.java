package com.normalworks.common.clickup.model;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * ClickUpCustomField
 *
 * @author: lingeng
 * @date: 28/11/2023
 */
public class ClickUpCustomField {

    private String id;
    private String name;

    @JSONField(name = "type")
    private String typeStr;

    /**
     * 具体数据格式待确定
     */
    @JSONField(name = "type_config")
    private String typeConfigStr;

    private ClickUpTypeConfig typeConfig;

    @JSONField(name = "date_created")
    private String dateCreated;
    @JSONField(name = "hide_from_guests")
    private boolean hideFromGuests;

    private String value;

    private List<? extends ClickUpCustomFieldValue> customerFieldValues;

    private boolean required;

    public ClickUpCustomFieldTypeEnum getTypeEnum() {
        return ClickUpCustomFieldTypeEnum.getByClickUpCustomFieldType(typeStr);
    }

    public List<? extends ClickUpCustomFieldValue> getCustomerFieldValues() {
        return customerFieldValues;
    }

    public void setCustomerFieldValues(List<? extends ClickUpCustomFieldValue> customerFieldValues) {
        this.customerFieldValues = customerFieldValues;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypeStr() {
        return typeStr;
    }

    public void setTypeStr(String typeStr) {
        this.typeStr = typeStr;
    }

    public String getTypeConfigStr() {
        return typeConfigStr;
    }

    public void setTypeConfigStr(String typeConfigStr) {
        this.typeConfigStr = typeConfigStr;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public boolean isHideFromGuests() {
        return hideFromGuests;
    }

    public void setHideFromGuests(boolean hideFromGuests) {
        this.hideFromGuests = hideFromGuests;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public ClickUpTypeConfig getTypeConfig() {
        return typeConfig;
    }

    public void setTypeConfig(ClickUpTypeConfig typeConfig) {
        this.typeConfig = typeConfig;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
