package com.normalworks.common.companieshouse.model;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * BranchCompanyDetails
 *
 * @author: lingeng
 * @date: 27/11/2023
 */
public class BranchCompanyDetails {

    @JSONField(name = "business_activity")
    private String businessActivity;
    @JSONField(name = "parent_company_name")
    private String parentCompanyName;
    @JSONField(name = "parent_company_number")
    private String parentCompanyNumber;

    public String getBusinessActivity() {
        return businessActivity;
    }

    public void setBusinessActivity(String businessActivity) {
        this.businessActivity = businessActivity;
    }

    public String getParentCompanyName() {
        return parentCompanyName;
    }

    public void setParentCompanyName(String parentCompanyName) {
        this.parentCompanyName = parentCompanyName;
    }

    public String getParentCompanyNumber() {
        return parentCompanyNumber;
    }

    public void setParentCompanyNumber(String parentCompanyNumber) {
        this.parentCompanyNumber = parentCompanyNumber;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
