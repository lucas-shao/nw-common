package com.normalworks.common.companieshouse.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * ForeignCompanyOriginatingRegistry
 *
 * @author: lingeng
 * @date: 27/11/2023
 */
public class ForeignCompanyOriginatingRegistry {

    private String country;
    private String name;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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
