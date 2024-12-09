package com.normalworks.common.companieshouse.model;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * ForeignCompanyAccounts
 *
 * @author: lingeng
 * @date: 27/11/2023
 */
public class ForeignCompanyAccounts {

    @JSONField(name = "account_period_from:")
    private CompaniesHouseDate accountPeriodFrom;
    @JSONField(name = "account_period_to")
    private CompaniesHouseDate accountPeriodTo;
    @JSONField(name = "must_file_within")
    private CompaniesHouseMonths mustFileWithin;


    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
