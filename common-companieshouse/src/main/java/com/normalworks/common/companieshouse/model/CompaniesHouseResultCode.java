package com.normalworks.common.companieshouse.model;

import com.normalworks.common.utils.assertion.AssertionResultCode;

/**
 * CompaniesHouseResultCode
 *
 * @author: lingeng
 * @date: 21/09/2023
 */
public enum CompaniesHouseResultCode implements AssertionResultCode {
    CALL_UK_COMPANIES_HOUSE_FAILED,

    UK_COMPANY_QUERY_PARAM_ILLEGAL,

    ;

    @Override
    public String getResultCode() {
        return null;
    }

    @Override
    public String getErrorLevel() {
        return null;
    }

    @Override
    public String getResultMsg() {
        return null;
    }

    @Override
    public int getHttpStatus() {
        return 0;
    }
}
