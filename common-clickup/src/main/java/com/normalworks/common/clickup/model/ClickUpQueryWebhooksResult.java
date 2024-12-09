package com.normalworks.common.clickup.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

public class ClickUpQueryWebhooksResult {

    private List<ClickUpWebhook> webhooks;

    public List<ClickUpWebhook> getWebhooks() {
        return webhooks;
    }

    public void setWebhooks(List<ClickUpWebhook> webhooks) {
        this.webhooks = webhooks;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
