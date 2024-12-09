package com.normalworks.common.clickup.model;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * ClickUpTaskUpdated
 *
 * @author: lingeng
 * @date: 28/11/2023
 */
public class ClickUpTaskUpdated implements ClickUpHistoryItem {

    private String id;
    private int type;
    private String date;
    private String field;
    @JSONField(name = "parent_id")
    private String parentId;

    /**
     * 具体数据格式待确定
     */
    private String data;
    private String source;
    private ClickUpUser user;
    private String before;
    private String after;

    /**
     * 如果是customer field有更新，则会有这个字段
     */
    @JSONField(name = "custom_field")
    private ClickUpCustomField customField;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public ClickUpUser getUser() {
        return user;
    }

    public void setUser(ClickUpUser user) {
        this.user = user;
    }

    public String getBefore() {
        return before;
    }

    public void setBefore(String before) {
        this.before = before;
    }

    public String getAfter() {
        return after;
    }

    public void setAfter(String after) {
        this.after = after;
    }

    public ClickUpCustomField getCustomField() {
        return customField;
    }

    public void setCustomField(ClickUpCustomField customField) {
        this.customField = customField;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
