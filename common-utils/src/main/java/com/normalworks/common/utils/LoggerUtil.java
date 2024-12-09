package com.normalworks.common.utils;

import com.normalworks.common.utils.assertion.AssertionException;
import com.normalworks.common.utils.assertion.ErrorLevels;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * LoggerUtil
 *
 * @author: lingeng
 * @date: 5/27/22
 */
public class LoggerUtil {

    private final static Logger DEFAULT_LOGGER = LoggerFactory.getLogger(LoggerUtil.class);

    public static final int STACK_TRACE_INDEX = 3;

    public static void debug(Logger logger, Object... objs) {
        try {
            String sb = constructLogText(objs);
            logger.debug(sb);
        } catch (Exception e) {
            DEFAULT_LOGGER.error("打印日志异常。", e);
        }
    }

    public static void info(Logger logger, Object... objs) {
        try {
            String sb = constructLogText(objs);
            logger.info(sb);
        } catch (Exception e) {
            DEFAULT_LOGGER.error("打印日志异常。", e);
        }
    }

    public static void warn(Logger logger, Exception ex, Object... objs) {
        try {
            String sb = constructLogText(objs);
            logger.warn(sb, ex.getMessage());
        } catch (Exception e) {
            DEFAULT_LOGGER.error("打印日志异常。", e);
        }
    }

    public static void warn(Logger logger, Object... objs) {
        try {
            String sb = constructLogText(objs);
            logger.warn(sb);
        } catch (Exception e) {
            DEFAULT_LOGGER.error("打印日志异常。", e);
        }
    }

    public static void warn(Object... objs) {
        try {
            String sb = constructLogText(objs);
            DEFAULT_LOGGER.warn(sb);
        } catch (Exception e) {
            DEFAULT_LOGGER.error("打印日志异常。", e);
        }
    }

    public static void error(Logger logger, Object... objs) {
        try {
            String sb = constructLogText(objs);
            logger.error(sb);
        } catch (Exception e) {
            DEFAULT_LOGGER.error("打印日志异常。", e);
        }
    }

    public static void error(Logger logger, Exception ex, Object... objs) {
        try {
            String sb = constructLogText(objs);
            logger.error(sb, ex);
        } catch (Exception e) {
            DEFAULT_LOGGER.error("打印日志异常。", e);
        }
    }

    /**
     * Logger:com.normalworks.common.utils.LoggerUtil.class
     */
    public static void error(Object... objs) {
        try {
            String sb = constructLogText(objs);
            DEFAULT_LOGGER.error(sb);
        } catch (Exception e) {
            DEFAULT_LOGGER.error("打印日志异常。", e);
        }
    }

    /**
     * Logger:com.normalworks.common.utils.LoggerUtil.class
     */
    public static void error(Exception ex, Object... objs) {

        try {
            String sb = constructLogText(objs);
            DEFAULT_LOGGER.error(sb, ex);
        } catch (Exception e) {
            DEFAULT_LOGGER.error("打印日志异常。", e);
        }
    }


    public static void log(Logger logger, AssertionException ex, Object... objs) {

        try {
            String sb = constructLogText(ex, objs);

            if (ex.getResultCode().getErrorLevel() == ErrorLevels.WARN) {
                logger.warn(sb);
            } else {
                logger.error(sb, ex);
            }
        } catch (Exception e) {
            DEFAULT_LOGGER.error("打印日志异常。", e);
        }
    }

    /**
     * 构造日志文本
     */
    private static String constructLogText(Object... objs) {
        StringBuilder sb = new StringBuilder();
        String fullClassName = Thread.currentThread().getStackTrace()[STACK_TRACE_INDEX].getClassName();
        String[] fullNames = fullClassName.split("\\.");
        String simpleClassName = fullNames[fullNames.length - 1];
        String methodName = Thread.currentThread().getStackTrace()[STACK_TRACE_INDEX].getMethodName();
        sb.append(simpleClassName).append(".").append(methodName).append(":");

        for (Object obj : objs) {
            if (obj == null) {
                sb.append("NULL");
            } else {
                sb.append(obj);
            }
        }

        return StringUtil.removeEnter(sb.toString());
    }

}
