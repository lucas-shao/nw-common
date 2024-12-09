package com.normalworks.common.sequence;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * SequenceConfig
 * seq的配置数据
 *
 * @author : shaoshuai.shao
 * @version : V1.0
 * @date : 2022/11/16 7:47 PM
 */
@Configuration
public class SequenceConfig {

    /**
     * seq表前缀
     * 一般都是以系统开头： pinvo_
     */
    @Value("${sequence.database.tablePrefix}")
    private String tablePrefix;

    public String getTablePrefix() {
        return tablePrefix;
    }

    public void setTablePrefix(String tablePrefix) {
        this.tablePrefix = tablePrefix;
    }
}
