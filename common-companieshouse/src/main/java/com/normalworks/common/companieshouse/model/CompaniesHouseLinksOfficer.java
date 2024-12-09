package com.normalworks.common.companieshouse.model;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * CompaniesHouseLinksOfficer
 *
 * @author: lingeng
 * @date: 21/09/2023
 */
public class CompaniesHouseLinksOfficer {

    @JSONField(name = "appointments")
    private String appointments;

    public String getAppointments() {
        return appointments;
    }

    public void setAppointments(String appointments) {
        this.appointments = appointments;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
