package com.normalworks.common.medium.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * MediumIntegrationConfig
 * 媒体集成配置
 *
 * @author : shaoshuai.shao
 * @version : V1.0
 * @date : 2022/12/20 6:22 PM
 */
@Configuration
public class MediumIntegrationConfig {

    @Value("${medium.database.tablePrefix}")
    private String tablePrefix;

    public String getTablePrefix() {
        return tablePrefix;
    }

    public void setTablePrefix(String tablePrefix) {
        this.tablePrefix = tablePrefix;
    }
}
