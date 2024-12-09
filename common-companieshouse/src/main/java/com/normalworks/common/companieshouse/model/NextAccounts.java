package com.normalworks.common.companieshouse.model;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * NextAccounts
 *
 * @author: lingeng
 * @date: 13/12/2023
 */
public class NextAccounts {

    @JSONField(name = "due_on")
    private String dueOn;

    @JSONField(name = "period_start_on")
    private String periodStartOn;

    @JSONField(name = "overdue")
    private Boolean overdue;

    @JSONField(name = "period_end_on")
    private String periodEndOn;

    public String getDueOn() {
        return dueOn;
    }

    public void setDueOn(String dueOn) {
        this.dueOn = dueOn;
    }

    public String getPeriodStartOn() {
        return periodStartOn;
    }

    public void setPeriodStartOn(String periodStartOn) {
        this.periodStartOn = periodStartOn;
    }

    public Boolean getOverdue() {
        return overdue;
    }

    public void setOverdue(Boolean overdue) {
        this.overdue = overdue;
    }

    public String getPeriodEndOn() {
        return periodEndOn;
    }

    public void setPeriodEndOn(String periodEndOn) {
        this.periodEndOn = periodEndOn;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
