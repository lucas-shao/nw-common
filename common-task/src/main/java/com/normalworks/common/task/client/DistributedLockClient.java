package com.normalworks.common.task.client;

/**
 * DistributedLockClient
 * 分布式锁工具类接口
 *
 * @author : shaoshuai.shao
 * @version : V1.0
 * @date : 2022/11/11 4:16 PM
 */
public interface DistributedLockClient {

    /**
     * 上锁
     *
     * @param key  锁的key
     * @param time 默认上锁时间 单位秒
     * @return 获取锁成功or失败
     */
    boolean lock(String key, long time);

    /**
     * 释放锁
     *
     * @param key 锁的key
     * @return 释放锁是否成功
     */
    boolean releaseLock(String key);
}
