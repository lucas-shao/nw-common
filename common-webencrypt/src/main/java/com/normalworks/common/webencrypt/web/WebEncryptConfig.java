package com.normalworks.common.webencrypt.web;

import java.lang.annotation.*;

/**
 * ControllerMethodType
 *
 * @author: lingeng
 * @date: 6/24/22
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WebEncryptConfig {

    /**
     * 是否需要加密
     */
    boolean encrypt() default true;

    /**
     * 是否需要解密
     */
    boolean decrypt() default true;
}
