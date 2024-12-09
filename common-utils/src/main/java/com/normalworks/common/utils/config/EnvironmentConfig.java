package com.normalworks.common.utils.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * EnvironmentConfig
 * 环境配置
 *
 * @author : shaoshuai
 * @version V1.0
 * @date Date : 2022年04月13日 4:44 下午
 */
@Configuration
public class EnvironmentConfig {

    @Value("${spring.profiles.active}")
    private String env;

    /**
     * 判断是否是线上生产环境
     *
     * @return
     */
    public boolean isProdEnv() {
        return StringUtils.equalsIgnoreCase(env, "prod");
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }
}
