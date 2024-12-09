package com.normalworks.common.companieshouse.model;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * ForeignCompanyDetails
 *
 * @author: lingeng
 * @date: 27/11/2023
 */
public class ForeignCompanyDetails {

    @JSONField(name = "accounting_requirement")
    private ForeignCompanyAccountingRequirement accountingRequirement;
    private ForeignCompanyAccounts accounts;
    @JSONField(name = "business_activity")
    private String businessActivity;
    @JSONField(name = "company_type")
    private String companyType;
    @JSONField(name = "governed_by")
    private String governedBy;
    @JSONField(name = "is_a_credit_finance_institution")
    private String isACreditFinanceInstitution;
    @JSONField(name = "originating_registry")
    private ForeignCompanyOriginatingRegistry originatingRegistry;
    @JSONField(name = "registration_number")
    private String registrationNumber;

    public ForeignCompanyAccountingRequirement getAccountingRequirement() {
        return accountingRequirement;
    }

    public void setAccountingRequirement(ForeignCompanyAccountingRequirement accountingRequirement) {
        this.accountingRequirement = accountingRequirement;
    }

    public ForeignCompanyAccounts getAccounts() {
        return accounts;
    }

    public void setAccounts(ForeignCompanyAccounts accounts) {
        this.accounts = accounts;
    }

    public String getBusinessActivity() {
        return businessActivity;
    }

    public void setBusinessActivity(String businessActivity) {
        this.businessActivity = businessActivity;
    }

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    public String getGovernedBy() {
        return governedBy;
    }

    public void setGovernedBy(String governedBy) {
        this.governedBy = governedBy;
    }

    public String getIsACreditFinanceInstitution() {
        return isACreditFinanceInstitution;
    }

    public void setIsACreditFinanceInstitution(String isACreditFinanceInstitution) {
        this.isACreditFinanceInstitution = isACreditFinanceInstitution;
    }

    public ForeignCompanyOriginatingRegistry getOriginatingRegistry() {
        return originatingRegistry;
    }

    public void setOriginatingRegistry(ForeignCompanyOriginatingRegistry originatingRegistry) {
        this.originatingRegistry = originatingRegistry;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
