package com.normalworks.common.clickup.model;

/**
 * ClickUpWebHookEventEnum
 *
 * @author: lingeng
 * @date: 28/11/2023
 */
public enum ClickUpWebHookEventEnum {
    TASK_CREATED("taskCreated", null),

    TASK_UPDATED("taskUpdated", ClickUpTaskUpdated.class),

    TASK_DELETED("taskDeleted", null),

    TASK_PRIORITY_UPDATED("taskPriorityUpdated", null),

    TASK_STATUS_UPDATED("taskStatusUpdated", null),

    TASK_ASSIGNEE_UPDATED("taskAssigneeUpdated", null),

    TASK_DUE_DATE_UPDATED("taskDueDateUpdated", null),

    TASK_TAG_UPDATED("taskTagUpdated", null),

    TASK_MOVED("taskMoved", null),

    TASK_COMMENT_POSTED("taskCommentPosted", null),

    TASK_COMMENT_UPDATED("taskCommentUpdated", null),

    TASK_TIME_ESTIMATE_UPDATED("taskTimeEstimateUpdated", null),

    TASK_TIME_TRACKED_UPDATED("taskTimeTrackedUpdated", null);

    private final String clickUpEvent;
    private final Class<? extends ClickUpHistoryItem> historyItemClass;

    ClickUpWebHookEventEnum(String clickUpEvent, Class<? extends ClickUpHistoryItem> historyItemClass) {
        this.clickUpEvent = clickUpEvent;
        this.historyItemClass = historyItemClass;
    }

    public static ClickUpWebHookEventEnum getByClickUpEvent(String clickUpEvent) {
        for (ClickUpWebHookEventEnum temp : values()) {
            if (temp.clickUpEvent.equals(clickUpEvent)) {
                return temp;
            }
        }
        return null;
    }

    public String getClickUpEvent() {
        return clickUpEvent;
    }

    public Class<? extends ClickUpHistoryItem> getHistoryItemClass() {
        return historyItemClass;
    }
}
