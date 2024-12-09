package com.normalworks.common.companieshouse.model;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;
import java.util.List;

/**
 * Officer
 *
 * @author: lingeng
 * @date: 21/09/2023
 */
public class CompaniesHouseOfficer {

    @JSONField(name = "address")
    private CompaniesHouseAddress address;

    @JSONField(name = "appointed_before")
    private String appointedBefore;

    @JSONField(name = "appointed_on")
    private Date appointedOn;

    @JSONField(name = "contact_details")
    private CompaniesHouseContactDetails contactDetails;

    @JSONField(name = "country_of_residence")
    private String countryOfResidence;

    @JSONField(name = "date_of_birth")
    private CompaniesHouseDate dateOfBirth;

    @JSONField(name = "etag")
    private String etag;

    @JSONField(name = "former_names")
    private List<CompaniesHousePersonFullName> formerNames;

    @JSONField(name = "identification")
    private CompaniesHouseIdentification identification;

    @JSONField(name = "is_pre_1992_appointment")
    private Boolean isPre1992Appointment;

    @JSONField(name = "links")
    private CompaniesHouseLinks links;

    @JSONField(name = "name")
    private String name;

    @JSONField(name = "nationality")
    private String nationality;

    @JSONField(name = "occupation")
    private String occupation;

    @JSONField(name = "officer_role")
    private String officerRole;

    @JSONField(name = "principal_office_address")
    private CompaniesHouseAddress principalOfficeAddress;

    @JSONField(name = "resigned_on")
    private String resignedOn;

    @JSONField(name = "responsibilities")
    private String responsibilities;

    public CompaniesHouseAddress getAddress() {
        return address;
    }

    public void setAddress(CompaniesHouseAddress address) {
        this.address = address;
    }

    public String getAppointedBefore() {
        return appointedBefore;
    }

    public void setAppointedBefore(String appointedBefore) {
        this.appointedBefore = appointedBefore;
    }

    public Date getAppointedOn() {
        return appointedOn;
    }

    public void setAppointedOn(Date appointedOn) {
        this.appointedOn = appointedOn;
    }

    public CompaniesHouseContactDetails getContactDetails() {
        return contactDetails;
    }

    public void setContactDetails(CompaniesHouseContactDetails contactDetails) {
        this.contactDetails = contactDetails;
    }

    public String getCountryOfResidence() {
        return countryOfResidence;
    }

    public void setCountryOfResidence(String countryOfResidence) {
        this.countryOfResidence = countryOfResidence;
    }

    public CompaniesHouseDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(CompaniesHouseDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public List<CompaniesHousePersonFullName> getFormerNames() {
        return formerNames;
    }

    public void setFormerNames(List<CompaniesHousePersonFullName> formerNames) {
        this.formerNames = formerNames;
    }

    public CompaniesHouseIdentification getIdentification() {
        return identification;
    }

    public void setIdentification(CompaniesHouseIdentification identification) {
        this.identification = identification;
    }

    public Boolean getPre1992Appointment() {
        return isPre1992Appointment;
    }

    public void setPre1992Appointment(Boolean pre1992Appointment) {
        isPre1992Appointment = pre1992Appointment;
    }

    public CompaniesHouseLinks getLinks() {
        return links;
    }

    public void setLinks(CompaniesHouseLinks links) {
        this.links = links;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getOfficerRole() {
        return officerRole;
    }

    public void setOfficerRole(String officerRole) {
        this.officerRole = officerRole;
    }

    public CompaniesHouseAddress getPrincipalOfficeAddress() {
        return principalOfficeAddress;
    }

    public void setPrincipalOfficeAddress(CompaniesHouseAddress principalOfficeAddress) {
        this.principalOfficeAddress = principalOfficeAddress;
    }

    public String getResignedOn() {
        return resignedOn;
    }

    public void setResignedOn(String resignedOn) {
        this.resignedOn = resignedOn;
    }

    public String getResponsibilities() {
        return responsibilities;
    }

    public void setResponsibilities(String responsibilities) {
        this.responsibilities = responsibilities;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
