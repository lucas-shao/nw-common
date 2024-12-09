package com.normalworks.common.companieshouse.model;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * ForeignCompanyAccountingRequirement
 *
 * @author: lingeng
 * @date: 27/11/2023
 */
public class ForeignCompanyAccountingRequirement {

    @JSONField(name = "foreign_account_type")
    private String foreignAccountType;
    @JSONField(name = "terms_of_account_publication")
    private String termsOfAccountPublication;

    public String getForeignAccountType() {
        return foreignAccountType;
    }

    public void setForeignAccountType(String foreignAccountType) {
        this.foreignAccountType = foreignAccountType;
    }

    public String getTermsOfAccountPublication() {
        return termsOfAccountPublication;
    }

    public void setTermsOfAccountPublication(String termsOfAccountPublication) {
        this.termsOfAccountPublication = termsOfAccountPublication;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
