package com.normalworks.common.webencrypt.web;

import org.apache.commons.lang3.StringUtils;

/**
 * HttpMethodTypeEnum
 *
 * @author : shaoshuai
 * @version V1.0
 * @date Date : 2022年04月06日 10:56 下午
 */
public enum HttpMethodTypeEnum {

    GET("get:", "GET"),

    POST("post:", "POST"),

    DELETE("delete:", "DELETE"),

    PUT("put:", "PUT"),

    ;

    private String value;
    private String memo;

    HttpMethodTypeEnum(String value, String memo) {
        this.value = value;
        this.memo = memo;
    }

    public static HttpMethodTypeEnum getByValue(String httpMethodType) {
        for (HttpMethodTypeEnum enumValue : HttpMethodTypeEnum.values()) {
            if (StringUtils.equals(enumValue.getValue(), httpMethodType)) {
                return enumValue;
            }
        }
        return null;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
