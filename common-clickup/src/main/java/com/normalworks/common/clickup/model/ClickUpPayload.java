package com.normalworks.common.clickup.model;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * ClickUpPayload
 *
 * @author: lingeng
 * @date: 28/11/2023
 */
public class ClickUpPayload {

    @JSONField(name = "event")
    private String event;

    @JSONField(name = "history_items")
    private String historyItemsStr;

    private List<ClickUpHistoryItem> historyItems;

    @JSONField(name = "task_id")
    private String taskId;

    @JSONField(name = "webhook_id")
    private String webhookId;

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getHistoryItemsStr() {
        return historyItemsStr;
    }

    public void setHistoryItemsStr(String historyItemsStr) {
        this.historyItemsStr = historyItemsStr;
    }

    public List<ClickUpHistoryItem> getHistoryItems() {
        return historyItems;
    }

    public void setHistoryItems(List<ClickUpHistoryItem> historyItems) {
        this.historyItems = historyItems;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getWebhookId() {
        return webhookId;
    }

    public void setWebhookId(String webhookId) {
        this.webhookId = webhookId;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
