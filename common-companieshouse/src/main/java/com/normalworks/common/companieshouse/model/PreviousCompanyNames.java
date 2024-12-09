package com.normalworks.common.companieshouse.model;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * PreviousCompanyNames
 *
 * @author: lingeng
 * @date: 27/11/2023
 */
public class PreviousCompanyNames {

    @JSONField(name = "ceased_on")
    private String ceasedOn;
    @JSONField(name = "effective_from")
    private String effectiveFrom;
    private String name;

    public String getCeasedOn() {
        return ceasedOn;
    }

    public void setCeasedOn(String ceasedOn) {
        this.ceasedOn = ceasedOn;
    }

    public String getEffectiveFrom() {
        return effectiveFrom;
    }

    public void setEffectiveFrom(String effectiveFrom) {
        this.effectiveFrom = effectiveFrom;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
