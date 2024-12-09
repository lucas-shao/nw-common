package com.normalworks.common.clickup.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * ClickUpDefaultTypeConfig
 *
 * @author: lingeng
 * @date: 28/11/2023
 */
public class ClickUpDefaultTypeConfig implements ClickUpTypeConfig {

    private String typeConfig;

    public String getTypeConfig() {
        return typeConfig;
    }

    public void setTypeConfig(String typeConfig) {
        this.typeConfig = typeConfig;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
