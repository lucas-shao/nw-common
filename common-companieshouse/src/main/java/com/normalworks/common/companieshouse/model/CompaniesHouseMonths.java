package com.normalworks.common.companieshouse.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * CompaniesHouseMonths
 *
 * @author: lingeng
 * @date: 27/11/2023
 */
public class CompaniesHouseMonths {

    private String months;

    public String getMonths() {
        return months;
    }

    public void setMonths(String months) {
        this.months = months;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
