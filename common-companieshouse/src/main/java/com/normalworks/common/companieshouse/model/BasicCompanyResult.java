package com.normalworks.common.companieshouse.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.normalworks.common.companieshouse.service.CompaniesHouseService;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * BasicCompanyResult
 * 基本的公司信息
 *
 * @author : shaoshuai.shao
 * @version : V1.0
 * @date : 2023/2/10 4:01 PM
 */
public class BasicCompanyResult {

    @JSONField(name = "etag")
    private String etag;

    @JSONField(name = "accounts")
    private CompanyAccounts accounts;
    @JSONField(name = "annual_return")
    private CompanyEventDateInfo annualReturn;
    @JSONField(name = "branch_company_details")
    private BranchCompanyDetails branchCompanyDetails;
    @JSONField(name = "can_file")
    private String canFile;
    @JSONField(name = "company_name")
    private String companyName;
    @JSONField(name = "company_number")
    private String companyNumber;
    @JSONField(name = "company_status")
    private String companyStatus;
    @JSONField(name = "company_status_detail")
    private String companyStatusDetail;
    @JSONField(name = "confirmation_statement")
    private CompanyEventDateInfo confirmationStatement;
    @JSONField(name = "date_of_cessation")
    private String dateOfCessation;
    @JSONField(name = "date_of_creation")
    private String dateOfCreation;
    @JSONField(name = "foreign_company_details")
    private ForeignCompanyDetails foreignCompanyDetails;
    @JSONField(name = "has_been_liquidated")
    private String hasBeenLiquidated;
    @JSONField(name = "has_charges")
    private String hasCharges;
    @JSONField(name = "has_insolvency_history")
    private String hasInsolvencyHistory;
    @JSONField(name = "is_community_interest_company")
    private String isCommunityInterestCompany;
    private String jurisdiction;
    @JSONField(name = "last_full_members_list_date")
    private String lastFullMembersListDate;

    @JSONField(name = "links")
    private CompanyLinks links;

    @JSONField(name = "previous_company_names")
    private List<PreviousCompanyNames> previousCompanyNames;
    @JSONField(name = "registered_office_is_in_dispute")
    private String registeredOfficeIsInDispute;
    @JSONField(name = "service_address")
    private CompaniesHouseAddress serviceAddress;
    @JSONField(name = "sic_codes")
    private List<String> sicCodes;
    @JSONField(name = "super_secure_managing_officer_count")
    private String superSecureManagingOfficerCount;
    private String type;
    @JSONField(name = "undeliverable_registered_office_address")
    private String undeliverableRegisteredOfficeAddress;
    @JSONField(name = "registered_office_address")
    private CompaniesHouseAddress registeredOfficeAddress;

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public CompanyAccounts getAccounts() {
        return accounts;
    }

    public void setAccounts(CompanyAccounts accounts) {
        this.accounts = accounts;
    }

    public CompanyEventDateInfo getAnnualReturn() {
        return annualReturn;
    }

    public void setAnnualReturn(CompanyEventDateInfo annualReturn) {
        this.annualReturn = annualReturn;
    }

    public BranchCompanyDetails getBranchCompanyDetails() {
        return branchCompanyDetails;
    }

    public void setBranchCompanyDetails(BranchCompanyDetails branchCompanyDetails) {
        this.branchCompanyDetails = branchCompanyDetails;
    }

    public String getCanFile() {
        return canFile;
    }

    public void setCanFile(String canFile) {
        this.canFile = canFile;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyNumber() {
        return companyNumber;
    }

    public void setCompanyNumber(String companyNumber) {
        this.companyNumber = companyNumber;
    }

    public String getCompanyStatus() {
        return companyStatus;
    }

    public void setCompanyStatus(String companyStatus) {
        this.companyStatus = companyStatus;
    }

    public String getCompanyStatusDetail() {
        return companyStatusDetail;
    }

    public void setCompanyStatusDetail(String companyStatusDetail) {
        this.companyStatusDetail = companyStatusDetail;
    }

    public CompanyEventDateInfo getConfirmationStatement() {
        return confirmationStatement;
    }

    public void setConfirmationStatement(CompanyEventDateInfo confirmationStatement) {
        this.confirmationStatement = confirmationStatement;
    }

    public String getDateOfCessation() {
        return dateOfCessation;
    }

    public void setDateOfCessation(String dateOfCessation) {
        this.dateOfCessation = dateOfCessation;
    }

    public String getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(String dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public ForeignCompanyDetails getForeignCompanyDetails() {
        return foreignCompanyDetails;
    }

    public void setForeignCompanyDetails(ForeignCompanyDetails foreignCompanyDetails) {
        this.foreignCompanyDetails = foreignCompanyDetails;
    }

    public String getHasBeenLiquidated() {
        return hasBeenLiquidated;
    }

    public void setHasBeenLiquidated(String hasBeenLiquidated) {
        this.hasBeenLiquidated = hasBeenLiquidated;
    }

    public String getHasCharges() {
        return hasCharges;
    }

    public void setHasCharges(String hasCharges) {
        this.hasCharges = hasCharges;
    }

    public String getHasInsolvencyHistory() {
        return hasInsolvencyHistory;
    }

    public void setHasInsolvencyHistory(String hasInsolvencyHistory) {
        this.hasInsolvencyHistory = hasInsolvencyHistory;
    }

    public String getIsCommunityInterestCompany() {
        return isCommunityInterestCompany;
    }

    public void setIsCommunityInterestCompany(String isCommunityInterestCompany) {
        this.isCommunityInterestCompany = isCommunityInterestCompany;
    }

    public String getJurisdiction() {
        return jurisdiction;
    }

    public void setJurisdiction(String jurisdiction) {
        this.jurisdiction = jurisdiction;
    }

    public String getLastFullMembersListDate() {
        return lastFullMembersListDate;
    }

    public void setLastFullMembersListDate(String lastFullMembersListDate) {
        this.lastFullMembersListDate = lastFullMembersListDate;
    }

    public CompanyLinks getLinks() {
        return links;
    }

    public void setLinks(CompanyLinks links) {
        this.links = links;
    }

    public List<PreviousCompanyNames> getPreviousCompanyNames() {
        return previousCompanyNames;
    }

    public void setPreviousCompanyNames(List<PreviousCompanyNames> previousCompanyNames) {
        this.previousCompanyNames = previousCompanyNames;
    }

    public String getRegisteredOfficeIsInDispute() {
        return registeredOfficeIsInDispute;
    }

    public void setRegisteredOfficeIsInDispute(String registeredOfficeIsInDispute) {
        this.registeredOfficeIsInDispute = registeredOfficeIsInDispute;
    }

    public CompaniesHouseAddress getServiceAddress() {
        return serviceAddress;
    }

    public void setServiceAddress(CompaniesHouseAddress serviceAddress) {
        this.serviceAddress = serviceAddress;
    }

    public List<String> getSicCodes() {
        return sicCodes;
    }

    public void setSicCodes(List<String> sicCodes) {
        this.sicCodes = sicCodes;
    }

    public String getSuperSecureManagingOfficerCount() {
        return superSecureManagingOfficerCount;
    }

    public void setSuperSecureManagingOfficerCount(String superSecureManagingOfficerCount) {
        this.superSecureManagingOfficerCount = superSecureManagingOfficerCount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUndeliverableRegisteredOfficeAddress() {
        return undeliverableRegisteredOfficeAddress;
    }

    public void setUndeliverableRegisteredOfficeAddress(String undeliverableRegisteredOfficeAddress) {
        this.undeliverableRegisteredOfficeAddress = undeliverableRegisteredOfficeAddress;
    }

    public CompaniesHouseAddress getRegisteredOfficeAddress() {
        return registeredOfficeAddress;
    }

    public void setRegisteredOfficeAddress(CompaniesHouseAddress registeredOfficeAddress) {
        this.registeredOfficeAddress = registeredOfficeAddress;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
