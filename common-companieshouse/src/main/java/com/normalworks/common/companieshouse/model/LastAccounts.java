package com.normalworks.common.companieshouse.model;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * LastAccounts
 *
 * @author: lingeng
 * @date: 27/11/2023
 */
public class LastAccounts {

    @JSONField(name = "made_up_to")
    private String madeUpTo;
    private String type;

    public String getMadeUpTo() {
        return madeUpTo;
    }

    public void setMadeUpTo(String madeUpTo) {
        this.madeUpTo = madeUpTo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
