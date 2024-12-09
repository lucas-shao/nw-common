package com.normalworks.common.utils;

import org.slf4j.MDC;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * ContextUtil
 * 应用上下文工具类
 *
 * @author : shaoshuai.shao
 * @version : V1.0
 * @date : 2022/11/22 10:54 PM
 */
public class ContextUtil {

    /**
     * 整个应用调用的跟踪ID
     */
    public static final String TRACE_ID = "TRACE_ID";

    /**
     * 整个应用服务调用的起始时间
     */
    public static final String SERVICE_CALL_TIME = "SERVICE_CALL_TIME";

    private static ThreadLocal<Map<String, Object>> ctx = new ThreadLocal<Map<String, Object>>() {

        @Override
        protected Map<String, Object> initialValue() {
            return new HashMap<>();
        }
    };

    /**
     * 上下文初始化
     */
    public static void init() {

        ContextUtil.put(ContextUtil.TRACE_ID, UUID.randomUUID().toString());
        ContextUtil.put(ContextUtil.SERVICE_CALL_TIME, new Date());
        putLogMDC();
    }


    public static Object get(String key) {
        return ctx.get().get(key);
    }

    public static void put(String key, Object obj) {
        ctx.get().put(key, obj);
    }

    public static void putTraceId(String traceId) {
        ctx.get().put(TRACE_ID, traceId);
        putLogMDC();
    }

    public static Date getTime() {
        return (Date) ctx.get().get(SERVICE_CALL_TIME);
    }

    /**
     * 清理线程上下文
     * 必须要在finally里面调用
     */
    public static void clear() {
        ctx.remove();
    }


    private static void putLogMDC() {
        // 放置用于log4j日志打印
        MDC.put(ContextUtil.TRACE_ID, (String) ContextUtil.get(ContextUtil.TRACE_ID));
    }

    public static void main(String[] args) throws InterruptedException {

        new Thread(new Runnable() {
            @Override
            public void run() {
                ContextUtil.put(SERVICE_CALL_TIME, new Date());
                System.out.println(ContextUtil.get(SERVICE_CALL_TIME));
                ContextUtil.clear();
                ContextUtil.clear();
                System.out.println(ContextUtil.get(SERVICE_CALL_TIME));
            }
        }, "A").start();

        Thread.sleep(1000);

        new Thread(new Runnable() {
            @Override
            public void run() {
                ContextUtil.put(SERVICE_CALL_TIME, new Date());
                System.out.println(ContextUtil.get(SERVICE_CALL_TIME));
                ContextUtil.clear();
            }
        }, "B").start();
    }
}
