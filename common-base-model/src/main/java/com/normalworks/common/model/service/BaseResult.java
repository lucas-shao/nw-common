package com.normalworks.common.model.service;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * BaseResult
 * 服务返回基类
 *
 * @author : shaoshuai
 * @version V1.0
 * @date Date : 2021年10月31日 5:29 下午
 */
public class BaseResult implements Serializable {

    private static final long serialVersionUID = 4639244211024468273L;

    /**
     * 业务调用是否成功
     **/
    protected boolean success;

    /**
     * 业务调用结果码
     **/
    protected String readableResultCode;

    /**
     * http状态码
     */
    protected int httpStatus;

    /**
     * 扩展信息，用于存储异常详细信息等
     */
    protected Map<String, String> extInfo;

    /**
     * 结果详细信息，用于跟外部系统交互使用，切忌设置内部数据等不合适的信息
     */
    protected String detailResultMessage;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getReadableResultCode() {
        return readableResultCode;
    }

    public void setReadableResultCode(String readableResultCode) {
        this.readableResultCode = readableResultCode;
    }

    public Map<String, String> getExtInfo() {
        return extInfo;
    }

    public void setExtInfo(Map<String, String> extInfo) {
        this.extInfo = extInfo;
    }

    public void putExtInfo(String key, String value) {
        if (extInfo == null) {
            extInfo = new HashMap<>();
        }
        this.extInfo.put(key, value);
    }

    public String getDetailResultMessage() {
        return detailResultMessage;
    }

    public void setDetailResultMessage(String detailResultMessage) {
        this.detailResultMessage = detailResultMessage;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }


}
