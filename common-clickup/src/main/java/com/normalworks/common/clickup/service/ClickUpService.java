package com.normalworks.common.clickup.service;

import com.alibaba.fastjson.JSON;
import com.normalworks.common.clickup.config.ClickUpConfig;
import com.normalworks.common.clickup.model.*;
import com.normalworks.common.utils.LoggerUtil;
import com.normalworks.common.utils.assertion.CommonResultCode;
import com.normalworks.common.utils.httpclient.HttpClientUtil;
import com.normalworks.common.utils.httpclient.HttpRequestCallback;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClickUpService
 *
 * @author: lingeng
 * @date: 28/11/2023
 */
@Service
public class ClickUpService {

    private static final Logger LOGGER = LoggerFactory.getLogger("COMMON-INTEGRATION");

    @Resource
    private ClickUpConfig clickUpConfig;

    public ClickUpResponse<ClickUpProcessWebhookResult> createWebhook(String teamId, ClickUpCreateWebhookRequest request) {

        return HttpClientUtil.postJson(clickUpConfig.getUrl() + "/team/" + teamId + "/webhook", null, initHeaderWithToken(), JSON.toJSONString(request), new HttpRequestCallback<ClickUpResponse<ClickUpProcessWebhookResult>>() {
            @Override
            public boolean success(HttpResponse response) {
                return response.getStatusLine().getStatusCode() == HttpStatus.SC_OK;
            }

            @Override
            public ClickUpResponse<ClickUpProcessWebhookResult> processSuccessResponse(HttpResponse response) throws IOException {
                String json = EntityUtils.toString(response.getEntity());
                LoggerUtil.info(LOGGER, "success response=", json);
                ClickUpProcessWebhookResult clickUpProcessWebHookResult = JSON.parseObject(json, ClickUpProcessWebhookResult.class);
                ClickUpResponse<ClickUpProcessWebhookResult> clickUpResponse = new ClickUpResponse<>();
                clickUpResponse.setData(clickUpProcessWebHookResult);
                clickUpResponse.setResultCode(CommonResultCode.SUCCESS.getResultCode());
                return clickUpResponse;
            }

            @Override
            public ClickUpResponse<ClickUpProcessWebhookResult> processFailureResponse(HttpResponse response) throws IOException {
                String json = EntityUtils.toString(response.getEntity());
                LoggerUtil.info(LOGGER, "fail response=", json);

                ClickUpResponse<ClickUpProcessWebhookResult> clickUpResponse = new ClickUpResponse<>();
                clickUpResponse.setResultCode(CommonResultCode.THIRD_PARTY_EXCEPTION.getResultCode());
                return clickUpResponse;
            }
        });
    }

    public ClickUpResponse<ClickUpQueryWebhooksResult> getWebhook(String teamId) {

        return HttpClientUtil.get(clickUpConfig.getUrl() + "/team/" + teamId + "/webhook", null, initHeaderWithToken(), new HttpRequestCallback<ClickUpResponse<ClickUpQueryWebhooksResult>>() {
            @Override
            public boolean success(HttpResponse response) {
                return response.getStatusLine().getStatusCode() == HttpStatus.SC_OK;
            }

            @Override
            public ClickUpResponse<ClickUpQueryWebhooksResult> processSuccessResponse(HttpResponse response) throws IOException {
                String json = EntityUtils.toString(response.getEntity());
                LoggerUtil.info(LOGGER, "success response=", json);
                ClickUpQueryWebhooksResult clickUpWebhooks = JSON.parseObject(json, ClickUpQueryWebhooksResult.class);
                ClickUpResponse<ClickUpQueryWebhooksResult> clickUpResponse = new ClickUpResponse<>();
                clickUpResponse.setData(clickUpWebhooks);
                clickUpResponse.setResultCode(CommonResultCode.SUCCESS.getResultCode());
                return clickUpResponse;
            }

            @Override
            public ClickUpResponse<ClickUpQueryWebhooksResult> processFailureResponse(HttpResponse response) throws IOException {
                String json = EntityUtils.toString(response.getEntity());
                LoggerUtil.info(LOGGER, "fail response=", json);
                ClickUpResponse<ClickUpQueryWebhooksResult> clickUpResponse = new ClickUpResponse<>();
                clickUpResponse.setResultCode(CommonResultCode.THIRD_PARTY_EXCEPTION.getResultCode());
                return clickUpResponse;
            }
        });
    }

    /**
     * 删除webhook订阅
     */
    public ClickUpResponse<Void> deleteWebhook(String webhookId) {

        return HttpClientUtil.delete(clickUpConfig.getUrl() + "/webhook/" + webhookId, null, initHeaderWithToken(), new HttpRequestCallback<ClickUpResponse<Void>>() {
            @Override
            public boolean success(HttpResponse response) {
                return response.getStatusLine().getStatusCode() == HttpStatus.SC_OK;
            }

            @Override
            public ClickUpResponse<Void> processSuccessResponse(HttpResponse response) throws IOException {
                String json = EntityUtils.toString(response.getEntity());
                LoggerUtil.info(LOGGER, "success response=", json);
                ClickUpResponse<Void> clickUpResponse = new ClickUpResponse<>();
                clickUpResponse.setResultCode(CommonResultCode.SUCCESS.getResultCode());
                return clickUpResponse;
            }

            @Override
            public ClickUpResponse<Void> processFailureResponse(HttpResponse response) throws IOException {
                String json = EntityUtils.toString(response.getEntity());
                LoggerUtil.info(LOGGER, "fail response=", json);
                ClickUpResponse<Void> clickUpResponse = new ClickUpResponse<>();
                clickUpResponse.setResultCode(CommonResultCode.THIRD_PARTY_EXCEPTION.getResultCode());
                return clickUpResponse;
            }
        });
    }

    public ClickUpPayload processWebhook(String payload, String signature) {

        ClickUpPayload clickUpPayload = JSON.parseObject(payload, ClickUpPayload.class);
        ClickUpWebHookEventEnum eventEnum = ClickUpWebHookEventEnum.getByClickUpEvent(clickUpPayload.getEvent());
        if (eventEnum != null && eventEnum.getHistoryItemClass() != null) {
            List<ClickUpHistoryItem> historyItems = (List<ClickUpHistoryItem>) JSON.parseArray(clickUpPayload.getHistoryItemsStr(), eventEnum.getHistoryItemClass());
            clickUpPayload.setHistoryItems(historyItems);
        }
        return clickUpPayload;
    }

    public ClickUpResponse<ClickUpTaskQueryResult> queryTasks(String listId, boolean includeSubtasks) {
        Map<String, String> headers = initHeaderWithToken();

        Map<String, String> params = new HashMap<>();
        params.put("subtasks", String.valueOf(includeSubtasks));

        return HttpClientUtil.get(clickUpConfig.getUrl() + "/list/" + listId + "/task", params, headers, new HttpRequestCallback<ClickUpResponse<ClickUpTaskQueryResult>>() {
            @Override
            public boolean success(HttpResponse response) {
                return response.getStatusLine().getStatusCode() == HttpStatus.SC_OK;
            }

            @Override
            public ClickUpResponse<ClickUpTaskQueryResult> processSuccessResponse(HttpResponse response) throws IOException {

                String json = EntityUtils.toString(response.getEntity());
                LoggerUtil.info(LOGGER, "success response=", json);

                ClickUpResponse<ClickUpTaskQueryResult> clickUpResponse = new ClickUpResponse<>();
                ClickUpTaskQueryResult data = JSON.parseObject(json, ClickUpTaskQueryResult.class);
                for (ClickUpTask task : data.getTasks()) {
                    if (!CollectionUtils.isEmpty(task.getCustomFields())) {
                        for (ClickUpCustomField customField : task.getCustomFields()) {
                            if (customField.getTypeEnum() == ClickUpCustomFieldTypeEnum.DROP_DOWN) {
                                customField.setTypeConfig(JSON.parseObject(customField.getTypeConfigStr(), ClickUpDropDownTypeConfig.class));
                            } else if (customField.getTypeEnum() == ClickUpCustomFieldTypeEnum.USERS) {
                                customField.setCustomerFieldValues(JSON.parseArray(customField.getValue(), ClickUpUser.class));
                            }
                        }
                    }
                    LoggerUtil.info(LOGGER, "ClickUp task=", JSON.toJSONString(task));
                }

                clickUpResponse.setData(data);
                clickUpResponse.setResultCode(CommonResultCode.SUCCESS.getResultCode());
                return clickUpResponse;
            }

            @Override
            public ClickUpResponse<ClickUpTaskQueryResult> processFailureResponse(HttpResponse response) throws IOException {
                ClickUpResponse<ClickUpTaskQueryResult> clickUpResponse = new ClickUpResponse<>();
                clickUpResponse.setResultCode(CommonResultCode.THIRD_PARTY_EXCEPTION.getResultCode());
                return clickUpResponse;
            }
        });
    }

    /**
     * 创建任务评论
     */
    public ClickUpResponse<Void> createTaskComment(String taskId, String comment) {
        Map<String, String> headers = initHeaderWithToken();

        Map<String, String> params = new HashMap<>();
        params.put("comment_text", comment);
        params.put("notify_all", "false");

        return HttpClientUtil.postJson(clickUpConfig.getUrl() + "/task/" + taskId + "/comment", null, headers, JSON.toJSONString(params), new HttpRequestCallback<ClickUpResponse<Void>>() {
            @Override
            public boolean success(HttpResponse response) {
                return response.getStatusLine().getStatusCode() == HttpStatus.SC_OK;
            }

            @Override
            public ClickUpResponse<Void> processSuccessResponse(HttpResponse response) throws IOException {
                String json = EntityUtils.toString(response.getEntity());
                LoggerUtil.info(LOGGER, "success response=", json);
                ClickUpResponse<Void> clickUpResponse = new ClickUpResponse<>();
                clickUpResponse.setResultCode(CommonResultCode.SUCCESS.getResultCode());
                return clickUpResponse;
            }

            @Override
            public ClickUpResponse<Void> processFailureResponse(HttpResponse response) throws IOException {
                ClickUpResponse<Void> clickUpResponse = new ClickUpResponse<>();
                clickUpResponse.setResultCode(CommonResultCode.THIRD_PARTY_EXCEPTION.getResultCode());
                return clickUpResponse;
            }
        });
    }

    private Map<String, String> initHeaderWithToken() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", clickUpConfig.getToken());
        return headers;
    }
}
