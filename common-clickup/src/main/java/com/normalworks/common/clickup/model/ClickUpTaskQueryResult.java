package com.normalworks.common.clickup.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * ClickUpBookkeepingClientTaskQueryResult
 *
 * @author: lingeng
 * @date: 28/11/2023
 */
public class ClickUpTaskQueryResult {

    private List<ClickUpTask> tasks;

    public List<ClickUpTask> getTasks() {
        return tasks;
    }

    public void setTasks(List<ClickUpTask> tasks) {
        this.tasks = tasks;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
