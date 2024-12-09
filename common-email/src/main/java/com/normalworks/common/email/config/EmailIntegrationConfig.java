package com.normalworks.common.email.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * EmailIntegrationConfig
 * 邮件集成配置
 *
 * @author : shaoshuai.shao
 * @version : V1.0
 * @date : 2023/12/29 5:08 PM
 */
@Configuration
public class EmailIntegrationConfig {

    @Value("${email.database.tablePrefix}")
    private String tablePrefix;

    public String getTablePrefix() {
        return tablePrefix;
    }

    public void setTablePrefix(String tablePrefix) {
        this.tablePrefix = tablePrefix;
    }
}
