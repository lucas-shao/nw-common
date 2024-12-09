package com.normalworks.common.companieshouse.model;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Address
 * 地址信息
 *
 * @author : shaoshuai.shao
 * @version : V1.0
 * @date : 2023/2/10 4:12 PM
 */
public class CompaniesHouseAddress {

    /**
     * 邮编
     */
    @JSONField(name = "postal_code")
    private String postalCode;

    /**
     * 国家 England
     */
    @JSONField(name = "country")
    private String country;

    /**
     * 城市地区 London
     */
    @JSONField(name = "locality")
    private String locality;

    /**
     * 地址1
     */
    @JSONField(name = "address_line_1")
    private String addressLine1;

    /**
     * 地址2
     */
    @JSONField(name = "address_line_2")
    private String addressLine2;

    @JSONField(name = "care_of")
    private String careOf;

    @JSONField(name = "poBox")
    private String poBox;

    @JSONField(name = "region")
    private String region;

    @JSONField(name = "premises")
    private String premises;

    public String getCareOf() {
        return careOf;
    }

    public void setCareOf(String careOf) {
        this.careOf = careOf;
    }

    public String getPoBox() {
        return poBox;
    }

    public void setPoBox(String poBox) {
        this.poBox = poBox;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getPremises() {
        return premises;
    }

    public void setPremises(String premises) {
        this.premises = premises;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
