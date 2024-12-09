package com.normalworks.common.shorturlcode;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * ShortUrlCodeConfig
 *
 * @author: lingeng
 * @date: 07/03/2023
 */
@Configuration
public class ShortUrlCodeConfig {

    /**
     * 短链码长度
     */
    @Value("${shorturlcode.config.shortCodeLength}")
    private int shortCodeLength;

    /**
     * 生成的短链码如果在数据库已经存在，尝试重新生成的次数
     */
    @Value("${shorturlcode.config.tryMaxTime}")
    private int tryMaxTimes;

    /**
     * 表前缀
     */
    @Value("${shorturlcode.config.tablePrefix}")
    private String tablePrefix;

    public int getShortCodeLength() {
        return shortCodeLength;
    }

    public void setShortCodeLength(int shortCodeLength) {
        this.shortCodeLength = shortCodeLength;
    }

    public int getTryMaxTimes() {
        return tryMaxTimes;
    }

    public void setTryMaxTimes(int tryMaxTimes) {
        this.tryMaxTimes = tryMaxTimes;
    }

    public String getTablePrefix() {
        return tablePrefix;
    }

    public void setTablePrefix(String tablePrefix) {
        this.tablePrefix = tablePrefix;
    }
}
