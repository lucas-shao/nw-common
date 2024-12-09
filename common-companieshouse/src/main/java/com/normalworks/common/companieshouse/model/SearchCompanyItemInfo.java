package com.normalworks.common.companieshouse.model;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * SearchCompanyItemInfo
 * 搜索的单个公司信息
 *
 * @author : shaoshuai.shao
 * @version : V1.0
 * @date : 2023/2/10 5:04 PM
 */
public class SearchCompanyItemInfo {

    /**
     * 公司名称
     * NORMAL WORKS LTD
     */
    @JSONField(name = "title")
    private String companyName;

    /**
     * 公司注册号
     * 14415231
     */
    @JSONField(name = "company_number")
    private String companyNumber;

    /**
     * 公司状态
     * active
     */
    @JSONField(name = "company_status")
    private String companyStatus;

    /**
     * 公司创建日期
     * 2022-10-12
     */
    @JSONField(name = "date_of_creation")
    private String companyCreateDate;

    /**
     * 公司类型
     * ltd
     */
    @JSONField(name = "company_type")
    private String companyType;

    /**
     * 公司注册地址
     */
    @JSONField(name = "address_snippet")
    private String addressSnippet;

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

    public String getCompanyCreateDate() {
        return companyCreateDate;
    }

    public void setCompanyCreateDate(String companyCreateDate) {
        this.companyCreateDate = companyCreateDate;
    }

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    public String getAddressSnippet() {
        return addressSnippet;
    }

    public void setAddressSnippet(String addressSnippet) {
        this.addressSnippet = addressSnippet;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
