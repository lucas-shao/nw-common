package com.normalworks.common.clickup.model;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * ClickUpDropDownTypeConfig
 *
 * @author: lingeng
 * @date: 28/11/2023
 */
public class ClickUpDropDownTypeConfig implements ClickUpTypeConfig {

    @JSONField(name = "default")
    private int defaultIndex;
    private List<ClickUpDropDownOption> options;
    
    public int getDefaultIndex() {
        return defaultIndex;
    }

    public void setDefaultIndex(int defaultIndex) {
        this.defaultIndex = defaultIndex;
    }

    public List<ClickUpDropDownOption> getOptions() {
        return options;
    }

    public void setOptions(List<ClickUpDropDownOption> options) {
        this.options = options;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
