package com.normalworks.common.companieshouse.model;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * CompaniesHousePersonFullName
 *
 * @author: lingeng
 * @date: 21/09/2023
 */
public class CompaniesHousePersonFullName {

    /**
     * first name
     */
    @JSONField(name = "forenames")
    private String forenames;


    /**
     * last name
     */
    @JSONField(name = "surname")
    private String surname;


    public String getForenames() {
        return forenames;
    }

    public void setForenames(String forenames) {
        this.forenames = forenames;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
