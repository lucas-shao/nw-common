package com.normalworks.common.companieshouse.model;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * SearchCompanyResult
 * 搜索公司列表结果
 *
 * @author : shaoshuai.shao
 * @version : V1.0
 * @date : 2023/2/10 5:03 PM
 */
public class SearchCompanyResult {

    @JSONField(name = "items")
    private List<SearchCompanyItemInfo> searchCompanyItemInfoList;

    public List<SearchCompanyItemInfo> getSearchCompanyItemInfoList() {
        return searchCompanyItemInfoList;
    }

    public void setSearchCompanyItemInfoList(List<SearchCompanyItemInfo> searchCompanyItemInfoList) {
        this.searchCompanyItemInfoList = searchCompanyItemInfoList;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
