package com.normalworks.common.clickup.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * ClickUpItem
 *
 * ClickUp 的 Task，List，Folder，Space都是 Item
 *
 * @author: lingeng
 * @date: 28/11/2023
 */
public class ClickUpItem {

    private String id;
    private String name;
    private boolean access;

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

    public boolean isAccess() {
        return access;
    }

    public void setAccess(boolean access) {
        this.access = access;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
