package com.normalworks.common.companieshouse.service;

import com.alibaba.fastjson.JSON;
import com.normalworks.common.companieshouse.config.CompaniesHouseConfig;
import com.normalworks.common.companieshouse.model.BasicCompanyResult;
import com.normalworks.common.companieshouse.model.CompaniesHouseResultCode;
import com.normalworks.common.companieshouse.model.OfficersResult;
import com.normalworks.common.companieshouse.model.SearchCompanyResult;
import com.normalworks.common.utils.LoggerUtil;
import com.normalworks.common.utils.assertion.AssertionException;
import com.normalworks.common.utils.httpclient.HttpClientUtil;
import com.normalworks.common.utils.httpclient.HttpRequestCallback;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.util.Base64Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * UkCompaniesHouseClientImpl
 *
 * @author : shaoshuai.shao
 * @version : V1.0
 * @date : 2023/2/10 4:55 PM
 */
@Service
public class CompaniesHouseService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompaniesHouseService.class);

    @Resource
    private CompaniesHouseConfig companiesHouseConfig;

    /**
     * 通过公司注册号查询公司基本信息
     */
    private static final String BASIC_COMPANY_INFO_QUERY_URL = "/company/";

    /**
     * 通过公司名称模糊搜索公司信息
     */
    private static final String COMPANY_SEARCH_URL = "/search/companies";


    public OfficersResult queryOfficers(String companyNo) {

        return HttpClientUtil.get(companiesHouseConfig.getDomainUrl() + BASIC_COMPANY_INFO_QUERY_URL + companyNo + "/officers", null, initAuthHeaders(), new HttpRequestCallback<OfficersResult>() {
            @Override
            public boolean success(HttpResponse response) {
                return response.getStatusLine().getStatusCode() == 200;
            }

            @Override
            public OfficersResult processSuccessResponse(HttpResponse response) throws IOException {
                String json = EntityUtils.toString(response.getEntity());
                LoggerUtil.info(LOGGER, "queryOfficers success response=", json);
                return JSON.parseObject(json, OfficersResult.class);
            }

            @Override
            public OfficersResult processFailureResponse(HttpResponse response) throws IOException {
                String json = EntityUtils.toString(response.getEntity());
                LoggerUtil.error("queryOfficers fail response=", json);
                return null;
            }
        });
    }

    public BasicCompanyResult queryByCompanyNo(String companyNo) {

        return HttpClientUtil.get(companiesHouseConfig.getDomainUrl() + BASIC_COMPANY_INFO_QUERY_URL + companyNo, null, initAuthHeaders(), new HttpRequestCallback<>() {
            @Override
            public boolean success(HttpResponse response) {
                // 404表示没有数据
                return httpStatusIs200(response) || httpStatusIs404(response);
            }

            @Override
            public BasicCompanyResult processSuccessResponse(HttpResponse response) throws IOException {

                if (httpStatusIs404(response)) {
                    LOGGER.info("查询成功，queryByCompanyNo查询数据为空");
                    return null;
                }

                String json = EntityUtils.toString(response.getEntity());
                LOGGER.info("查询成功，queryByCompanyNo返回原始结果如下 json = " + json);

                BasicCompanyResult basicCompanyResult = JSON.parseObject(json, BasicCompanyResult.class);
                LOGGER.info("查询成功，queryByCompanyNo返回结果：" + basicCompanyResult);
                return basicCompanyResult;
            }

            @Override
            public BasicCompanyResult processFailureResponse(HttpResponse response) throws IOException {
                String json = EntityUtils.toString(response.getEntity());
                LOGGER.warn("调用英国Companies House接口失败，返回原始结果如下 json = " + json);
                throw new AssertionException(CompaniesHouseResultCode.CALL_UK_COMPANIES_HOUSE_FAILED);
            }
        });
    }

    public SearchCompanyResult searchCompanyByCompanyName(String companyName) {

        if (StringUtils.isBlank(companyName)) {
            LOGGER.warn("调用英国Companies House接口，companyName为空");
            throw new AssertionException(CompaniesHouseResultCode.UK_COMPANY_QUERY_PARAM_ILLEGAL);
        }

        Map<String, String> urlParams = new HashMap<>();
        urlParams.put("q", StringUtils.trim(companyName));

        return HttpClientUtil.get(companiesHouseConfig.getDomainUrl() + COMPANY_SEARCH_URL, urlParams, initAuthHeaders(), new HttpRequestCallback<>() {
            @Override
            public boolean success(HttpResponse response) {
                // 404表示没有该数据，正常返回即可
                return httpStatusIs200(response) || httpStatusIs404(response);
            }

            @Override
            public SearchCompanyResult processSuccessResponse(HttpResponse response) throws IOException {

                if (httpStatusIs404(response)) {
                    LOGGER.info("查询成功，searchCompanyByCompanyName查询数据为空");
                    return null;
                }

                String json = EntityUtils.toString(response.getEntity());
                LOGGER.info("查询成功，searchCompanyByCompanyName返回原始结果如下 json = " + json);

                SearchCompanyResult searchCompanyResult = JSON.parseObject(json, SearchCompanyResult.class);
                LOGGER.info("查询成功，searchCompanyByCompanyName返回结果：" + searchCompanyResult);
                return searchCompanyResult;
            }

            @Override
            public SearchCompanyResult processFailureResponse(HttpResponse response) throws IOException {
                String json = EntityUtils.toString(response.getEntity());
                LOGGER.warn("调用英国Companies House接口失败，返回原始结果如下 json = " + json);
                throw new AssertionException(CompaniesHouseResultCode.CALL_UK_COMPANIES_HOUSE_FAILED);
            }
        });
    }

    /**
     * 初始化auth header
     *
     * @return auth header
     */
    private Map<String, String> initAuthHeaders() {
        Map<String, String> headers = new HashMap<>();
        String token = Base64Util.encode(companiesHouseConfig.getApiKey() + ":");
        headers.put("Authorization", "Basic " + token);
        return headers;
    }

    private static boolean httpStatusIs200(HttpResponse response) {
        return response.getStatusLine().getStatusCode() == HttpStatus.SC_OK;
    }

    private static boolean httpStatusIs404(HttpResponse response) {
        return response.getStatusLine().getStatusCode() == HttpStatus.SC_NOT_FOUND;
    }
}
