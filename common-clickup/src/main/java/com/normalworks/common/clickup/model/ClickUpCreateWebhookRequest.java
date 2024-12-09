package com.normalworks.common.clickup.model;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * ClickUpCreateWebHookRequest
 *
 * @author: lingeng
 * @date: 02/12/2023
 */
public class ClickUpCreateWebhookRequest {

    @JSONField(name = "endpoint")
    private String endpoint;

    @JSONField(name = "events")
    private List<String> events;

    @JSONField(name = "space_id")
    private String spaceId;

    @JSONField(name = "folder_id")
    private String folderId;

    @JSONField(name = "list_id")
    private String listId;

    @JSONField(name = "task_id")
    private String taskId;

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public List<String> getEvents() {
        return events;
    }

    public void setEvents(List<String> events) {
        this.events = events;
    }

    public String getSpaceId() {
        return spaceId;
    }

    public void setSpaceId(String spaceId) {
        this.spaceId = spaceId;
    }

    public String getFolderId() {
        return folderId;
    }

    public void setFolderId(String folderId) {
        this.folderId = folderId;
    }

    public String getListId() {
        return listId;
    }

    public void setListId(String listId) {
        this.listId = listId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
