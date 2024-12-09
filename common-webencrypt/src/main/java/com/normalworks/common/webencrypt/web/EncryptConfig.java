package com.normalworks.common.webencrypt.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * EncryptConfig
 * api接口请求参数和响应数据加解密配置
 *
 * @author : shaoshuai
 * @version V1.0
 * @date Date : 2022年04月07日 9:53 上午
 */
@Configuration
public class EncryptConfig {

    /**
     * 开启调试模式，调试模式下不进行加解密操作，用于像Swagger这种在线API测试场景
     */
    @Value("${encrypt.config.debug}")
    private boolean debug;

    /**
     * encrypt表前缀
     * 一般都是以系统开头： pinvo_
     */
    @Value("${encrypt.database.tablePrefix}")
    private String tablePrefix;

    /**
     * 响应数据编码项
     */
    private String responseCharset = "UTF-8";

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public String getResponseCharset() {
        return responseCharset;
    }

    public void setResponseCharset(String responseCharset) {
        this.responseCharset = responseCharset;
    }

    public String getTablePrefix() {
        return tablePrefix;
    }

    public void setTablePrefix(String tablePrefix) {
        this.tablePrefix = tablePrefix;
    }
}
