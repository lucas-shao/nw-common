package com.normalworks.common.clickup.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * ClickUpProcessWebhookResult
 *
 * @author: lingeng
 * @date: 02/12/2023
 */
public class ClickUpProcessWebhookResult {

    /**
     * 只有create操作才会返回这个id
     */
    private String id;

    private ClickUpWebhook webhook;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ClickUpWebhook getWebhook() {
        return webhook;
    }

    public void setWebhook(ClickUpWebhook webhook) {
        this.webhook = webhook;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
