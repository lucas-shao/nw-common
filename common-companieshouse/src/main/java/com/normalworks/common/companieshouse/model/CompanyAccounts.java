package com.normalworks.common.companieshouse.model;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * CompanyAccounts
 *
 * @author: lingeng
 * @date: 27/11/2023
 */
public class CompanyAccounts {

    @JSONField(name = "accounting_reference_date")
    private CompaniesHouseDate accountingReferenceDate;
    @JSONField(name = "last_accounts")
    private LastAccounts lastAccounts;
    @JSONField(name = "next_due")
    private String nextDue;

    @JSONField(name = "next_accounts")
    private NextAccounts nextAccounts;

    @JSONField(name = "next_made_up_to")
    private String nextMadeUpTo;
    private String overdue;

    public CompaniesHouseDate getAccountingReferenceDate() {
        return accountingReferenceDate;
    }

    public void setAccountingReferenceDate(CompaniesHouseDate accountingReferenceDate) {
        this.accountingReferenceDate = accountingReferenceDate;
    }

    public LastAccounts getLastAccounts() {
        return lastAccounts;
    }

    public void setLastAccounts(LastAccounts lastAccounts) {
        this.lastAccounts = lastAccounts;
    }

    public String getNextDue() {
        return nextDue;
    }

    public void setNextDue(String nextDue) {
        this.nextDue = nextDue;
    }

    public String getNextMadeUpTo() {
        return nextMadeUpTo;
    }

    public void setNextMadeUpTo(String nextMadeUpTo) {
        this.nextMadeUpTo = nextMadeUpTo;
    }

    public String getOverdue() {
        return overdue;
    }

    public void setOverdue(String overdue) {
        this.overdue = overdue;
    }

    public NextAccounts getNextAccounts() {
        return nextAccounts;
    }

    public void setNextAccounts(NextAccounts nextAccounts) {
        this.nextAccounts = nextAccounts;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
