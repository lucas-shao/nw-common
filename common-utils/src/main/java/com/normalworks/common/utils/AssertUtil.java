package com.normalworks.common.utils;

import com.normalworks.common.utils.assertion.AssertionException;
import com.normalworks.common.utils.assertion.AssertionResultCode;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.Map;

/**
 * AssertUtil
 * 断言工具类
 *
 * @author : shaoshuai
 * @version V1.0
 * @date Date : 2021年11月07日 2:59 下午
 */
public class AssertUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(AssertUtil.class);

    /**
     * 异常类Class名称
     */
    private final static String exceptionClassName = AssertionException.class.getName();

    /**
     * 异常对象构造方法
     */
    private static Constructor constructor;

    static {
        Class exceptionClassTmp = null;

        // 1. 加载异常类
        try {
            exceptionClassTmp = Class.forName(exceptionClassName);
        } catch (Throwable e) {
            throw new IllegalArgumentException("loading exceptionClass failed![exceptionClassName=" + exceptionClassName + "]", e);
        }

        // 必须是AssertException的子类
        if (!AssertionException.class.isAssignableFrom(exceptionClassTmp)) {
            throw new IllegalArgumentException("illegal exceptionClass type, must be the subclass of AssertionException![exceptionClassName=" + exceptionClassName + "]");
        }

        Constructor constructorTmp = null;

        // 2. 获取构造方法
        try {
            constructorTmp = exceptionClassTmp.getConstructor(AssertionResultCode.class, String.class);
        } catch (Throwable e) {
            throw new IllegalArgumentException("constructor method not found![exceptionClassName=" + exceptionClassName + "]", e);
        }

        // 3. 缓存反射结果
        constructor = constructorTmp;
    }

    /**
     * 断言对象为非空，否则抛出指定的错误信息
     *
     * @param str        断言字符串
     * @param resultCode 错误码
     * @param objs       任意个异常描述信息的参数
     */
    public static void notBlank(final String str, final AssertionResultCode resultCode, final Object... objs) {
        isTrue(StringUtils.isNotBlank(str), resultCode, objs);
    }

    /**
     * 断言对象非null，否则抛出指定错误信息
     *
     * @param object     待检测对象
     * @param resultCode 错误码
     * @param objs       任意个异常描述信息的参数
     */
    public static void notNull(final Object object, final AssertionResultCode resultCode, final Object... objs) {

        isTrue(object != null, resultCode, objs);
    }

    /**
     * 断言对象集合非空，否则抛出指定错误信息
     *
     * @param collection 待检查集合
     * @param resultCode 错误码
     * @param objs       任意个异常描述信息的参数
     */
    public static void notEmpty(final Collection collection, final AssertionResultCode resultCode, final Object... objs) {
        isTrue((collection != null) && (!collection.isEmpty()), resultCode, objs);
    }

    /**
     * 断言对象Map非空，否则抛出指定错误信息
     *
     * @param map        待检查集合Map
     * @param resultCode 错误码
     * @param objs       任意个异常描述信息的参数
     */
    public static void notEmpty(final Map map, final AssertionResultCode resultCode, final Object... objs) {
        isTrue((map != null) && (!map.isEmpty()), resultCode, objs);
    }

    /**
     * 断言对象为空，否则抛出指定的错误信息
     *
     * @param str        断言字符串
     * @param resultCode 错误码
     * @param objs       任意个异常描述信息的参数
     */
    public static void isBlank(final String str, final AssertionResultCode resultCode, final Object... objs) {
        isTrue(StringUtils.isBlank(str), resultCode, objs);
    }

    /**
     * 断言对象为null，否则抛出指定错误信息
     *
     * @param object     待检测对象
     * @param resultCode 错误码
     * @param objs       任意个异常描述信息的参数
     */
    public static void isNull(final Object object, final AssertionResultCode resultCode, final Object... objs) {
        isTrue(object == null, resultCode, objs);
    }

    /**
     * 断言两个字符串相等，如果两个都为null会判定为不相等
     *
     * @param value1
     * @param value2
     * @param resultCode
     * @param objs
     */
    public static void equals(final String value1, final String value2, final AssertionResultCode resultCode, final Object... objs) {
        isTrue(StringUtils.equals(value1, value2), resultCode, objs);
    }

    public static void equals(Long value1, Long value2, AssertionResultCode resultCode, final Object... objs) {
        isTrue(value1 != null && value1.equals(value2), resultCode, objs);
    }

    public static void equals(Integer value1, Integer value2, AssertionResultCode resultCode, final Object... objs) {
        isTrue(value1 != null && value1.equals(value2), resultCode, objs);
    }

    public static void equals(Object value1, Object value2, AssertionResultCode resultCode, final Object... objs) {
        isTrue(value1 != null && value1.equals(value2), resultCode, objs);
    }

    /**
     * 断言指定对象在容器中，否则抛出指定错误信息
     *
     * @param base       待查对象
     * @param collection 容器集合
     * @param resultCode 错误码
     * @param objs       任意个异常描述信息的参数
     */
    public static void contains(final Object base, final Collection collection, final AssertionResultCode resultCode, final Object... objs) {
        notEmpty(collection, resultCode, objs);
        isTrue(collection.contains(base), resultCode, objs);
    }

    /**
     * 断言表达式的值为true，否则抛出指定错误信息
     *
     * @param expValue   断言表达式
     * @param resultCode 错误码
     * @param objs       任意个异常描述信息的参数
     */
    public static void isTrue(final boolean expValue, final AssertionResultCode resultCode, final Object... objs) {

        if (!expValue) {

            AssertionException exception = null;
            String logString = getLogString(objs);
            String resultMsg = StringUtils.isBlank(logString) ? resultCode.getResultMsg() : logString;

            try {
                exception = (AssertionException) constructor.newInstance(resultCode, resultMsg);
            } catch (Throwable e) {
                throw new IllegalArgumentException("AssertUtil has not been initialized correctly![exceptionClassName=" + exceptionClassName + ",resultCode=" + resultCode + ",resultMsg=" + resultMsg + "]", e);
            }

            throw exception;
        }
    }

    /**
     * 生成输出到日志的字符串
     *
     * @param objs 任意个输出到日志的参数
     * @return 日志字符串
     */
    private static String getLogString(Object... objs) {
        StringBuilder log = new StringBuilder();

        for (Object o : objs) {
            log.append(o);
        }
        return log.toString();
    }

}
