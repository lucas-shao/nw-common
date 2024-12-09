package com.normalworks.common.companieshouse.model;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * CompaniesHouseIdentification
 *
 * @author: lingeng
 * @date: 21/09/2023
 */
public class CompaniesHouseIdentification {

    @JSONField(name = "identification_type")
    private String identificationType;

    @JSONField(name = "legal_authority")
    private String legalAuthority;

    @JSONField(name = "legal_form")
    private String legalForm;

    @JSONField(name = "place_registered")
    private String placeRegistered;

    @JSONField(name = "registration_number")
    private String registrationNumber;


    public String getIdentificationType() {
        return identificationType;
    }

    public void setIdentificationType(String identificationType) {
        this.identificationType = identificationType;
    }

    public String getLegalAuthority() {
        return legalAuthority;
    }

    public void setLegalAuthority(String legalAuthority) {
        this.legalAuthority = legalAuthority;
    }

    public String getLegalForm() {
        return legalForm;
    }

    public void setLegalForm(String legalForm) {
        this.legalForm = legalForm;
    }

    public String getPlaceRegistered() {
        return placeRegistered;
    }

    public void setPlaceRegistered(String placeRegistered) {
        this.placeRegistered = placeRegistered;
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
