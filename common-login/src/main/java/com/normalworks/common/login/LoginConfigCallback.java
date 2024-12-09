package com.normalworks.common.login;

public interface LoginConfigCallback {

    /**
     * token 超时时长
     */
    long getTokenExpiredInSecond();

    /**
     * 自动更新token的时间间隔
     * 默认是token 超时时长的2倍
     */
    default long getAutoUpdateTokenIntervalInSecond() {
        return getTokenExpiredInSecond() * 2;
    }

    /**
     * token 前缀
     * 如果有多种场景，可以通过前缀区分场景
     */
    String getTokenPrefix();
}
