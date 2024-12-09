package com.normalworks.common.companieshouse.model;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * OfficersResult
 *
 * @author: lingeng
 * @date: 21/09/2023
 */
public class OfficersResult {

    @JSONField(name = "active_count")
    private Integer activeCount;

    @JSONField(name = "etag")
    private String etag;

    @JSONField(name = "items")
    private List<CompaniesHouseOfficer> items;

    @JSONField(name = "items_per_page")
    private Integer itemsPerPage;

    @JSONField(name = "kind")
    private String kind;

    @JSONField(name = "links")
    private CompaniesHouseLinks links;

    @JSONField(name = "resigned_count")
    private Integer resignedCount;

    @JSONField(name = "start_index")
    private Integer startIndex;

    @JSONField(name = "total_results")
    private Integer totalResults;

    public Integer getActiveCount() {
        return activeCount;
    }

    public void setActiveCount(Integer activeCount) {
        this.activeCount = activeCount;
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public List<CompaniesHouseOfficer> getItems() {
        return items;
    }

    public void setItems(List<CompaniesHouseOfficer> items) {
        this.items = items;
    }

    public Integer getItemsPerPage() {
        return itemsPerPage;
    }

    public void setItemsPerPage(Integer itemsPerPage) {
        this.itemsPerPage = itemsPerPage;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public CompaniesHouseLinks getLinks() {
        return links;
    }

    public void setLinks(CompaniesHouseLinks links) {
        this.links = links;
    }

    public Integer getResignedCount() {
        return resignedCount;
    }

    public void setResignedCount(Integer resignedCount) {
        this.resignedCount = resignedCount;
    }

    public Integer getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(Integer startIndex) {
        this.startIndex = startIndex;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
