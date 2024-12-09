package com.normalworks.common.clickup.model;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * ClickUpBookkeepingClientTask
 *
 * @author: lingeng
 * @date: 28/11/2023
 */
public class ClickUpTask {

    private String id;
    @JSONField(name = "custom_id")
    private String customId;
    @JSONField(name = "custom_item_id")
    private Long customItemId;
    private String name;
    @JSONField(name = "text_content")
    private String textContent;
    private String description;
    private ClickUpTaskStatus status;

    @JSONField(name = "orderindex")
    private String orderIndex;
    @JSONField(name = "date_created")
    private String dateCreated;
    @JSONField(name = "date_updated")
    private String dateUpdated;
    @JSONField(name = "date_closed")
    private String dateClosed;
    @JSONField(name = "date_done")
    private String dateDone;
    private boolean archived;
    private ClickUpUser creator;
    private List<ClickUpUser> assignees;
    private List<String> watchers;
    private List<String> checklists;
    private List<String> tags;
    private String parent;
    private String priority;
    @JSONField(name = "due_date")
    private String dueDate;
    @JSONField(name = "start_date")
    private String startDate;
    private String points;
    @JSONField(name = "time_estimate")
    private String timeEstimate;
    @JSONField(name = "custom_fields")
    private List<ClickUpCustomField> customFields;
    private List<String> dependencies;
    @JSONField(name = "linked_tasks")
    private List<String> linkedTasks;
    private List<String> locations;
    @JSONField(name = "team_id")
    private String teamId;
    private String url;
    private ClickUpItem list;
    private ClickUpItem project;
    private ClickUpItem folder;
    private ClickUpItem space;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomId() {
        return customId;
    }

    public void setCustomId(String customId) {
        this.customId = customId;
    }

    public Long getCustomItemId() {
        return customItemId;
    }

    public void setCustomItemId(Long customItemId) {
        this.customItemId = customItemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ClickUpTaskStatus getStatus() {
        return status;
    }

    public void setStatus(ClickUpTaskStatus status) {
        this.status = status;
    }

    public String getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(String orderIndex) {
        this.orderIndex = orderIndex;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(String dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public String getDateClosed() {
        return dateClosed;
    }

    public void setDateClosed(String dateClosed) {
        this.dateClosed = dateClosed;
    }

    public String getDateDone() {
        return dateDone;
    }

    public void setDateDone(String dateDone) {
        this.dateDone = dateDone;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public ClickUpUser getCreator() {
        return creator;
    }

    public void setCreator(ClickUpUser creator) {
        this.creator = creator;
    }

    public List<ClickUpUser> getAssignees() {
        return assignees;
    }

    public void setAssignees(List<ClickUpUser> assignees) {
        this.assignees = assignees;
    }

    public List<String> getWatchers() {
        return watchers;
    }

    public void setWatchers(List<String> watchers) {
        this.watchers = watchers;
    }

    public List<String> getChecklists() {
        return checklists;
    }

    public void setChecklists(List<String> checklists) {
        this.checklists = checklists;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getTimeEstimate() {
        return timeEstimate;
    }

    public void setTimeEstimate(String timeEstimate) {
        this.timeEstimate = timeEstimate;
    }

    public List<ClickUpCustomField> getCustomFields() {
        return customFields;
    }

    public void setCustomFields(List<ClickUpCustomField> customFields) {
        this.customFields = customFields;
    }

    public List<String> getDependencies() {
        return dependencies;
    }

    public void setDependencies(List<String> dependencies) {
        this.dependencies = dependencies;
    }

    public List<String> getLinkedTasks() {
        return linkedTasks;
    }

    public void setLinkedTasks(List<String> linkedTasks) {
        this.linkedTasks = linkedTasks;
    }

    public List<String> getLocations() {
        return locations;
    }

    public void setLocations(List<String> locations) {
        this.locations = locations;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ClickUpItem getList() {
        return list;
    }

    public void setList(ClickUpItem list) {
        this.list = list;
    }

    public ClickUpItem getProject() {
        return project;
    }

    public void setProject(ClickUpItem project) {
        this.project = project;
    }

    public ClickUpItem getFolder() {
        return folder;
    }

    public void setFolder(ClickUpItem folder) {
        this.folder = folder;
    }

    public ClickUpItem getSpace() {
        return space;
    }

    public void setSpace(ClickUpItem space) {
        this.space = space;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
