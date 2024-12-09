package com.normalworks.common.companieshouse.model;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * CompanyEventDateInfo
 *
 * @author: lingeng
 * @date: 27/11/2023
 */
public class CompanyEventDateInfo {

    @JSONField(name = "last_made_up_to")
    private String lastMadeUpTo;
    @JSONField(name = "next_due")
    private String nextDue;
    @JSONField(name = "next_made_up_to")
    private String nextMadeUpTo;
    private String overdue;

    public String getLastMadeUpTo() {
        return lastMadeUpTo;
    }

    public void setLastMadeUpTo(String lastMadeUpTo) {
        this.lastMadeUpTo = lastMadeUpTo;
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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
