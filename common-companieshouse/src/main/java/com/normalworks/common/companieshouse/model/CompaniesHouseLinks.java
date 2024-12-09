package com.normalworks.common.companieshouse.model;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * CompaniesHouseLinks
 *
 * @author: lingeng
 * @date: 21/09/2023
 */
public class CompaniesHouseLinks {

    @JSONField(name = "officer")
    private CompaniesHouseLinksOfficer officer;

    @JSONField(name = "self")
    private String self;

    public CompaniesHouseLinksOfficer getOfficer() {
        return officer;
    }

    public void setOfficer(CompaniesHouseLinksOfficer officer) {
        this.officer = officer;
    }

    public String getSelf() {
        return self;
    }

    public void setSelf(String self) {
        this.self = self;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
