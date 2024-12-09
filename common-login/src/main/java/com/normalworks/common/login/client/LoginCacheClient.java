package com.normalworks.common.login.client;

/**
 * LoginCacheClient
 * 登录缓存服务
 *
 * @author : shaoshuai.shao
 * @version : V1.0
 * @date : 2022/11/17 9:30 PM
 */
public interface LoginCacheClient {

    /**
     * 普通缓存放入并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true 成功 false 失败
     */
    boolean set(String key, Object value, long time);

    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    Object get(String key);

    /**
     * 删除缓存
     *
     * @param key 键
     * @return 删除结果
     */
    boolean del(String key);
}
