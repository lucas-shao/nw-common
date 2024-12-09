package com.normalworks.common.companieshouse.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * CompaniesHouseConfig
 *
 * @author: lingeng
 * @date: 21/09/2023
 */
@Configuration
public class CompaniesHouseConfig {

    @Value("${companies.house.api.key}")
    private String apiKey;

    @Value("${companies.house.domain.url}")
    private String domainUrl;


    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getDomainUrl() {
        return domainUrl;
    }

    public void setDomainUrl(String domainUrl) {
        this.domainUrl = domainUrl;
    }
}
