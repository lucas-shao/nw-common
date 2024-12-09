package com.normalworks.common.utils;

import java.util.Random;

/**
 * OtpUtil
 * OTP工具类
 *
 * @author : shaoshuai
 * @version V1.0
 * @date Date : 2021年12月07日 2:13 下午
 */
public class OtpUtil {

    /**
     * 随机生成固定位数的一串数字
     *
     * @param digitNumbers 数字的位数
     * @return 一串随机数字
     */
    public static String randomGenerateOtpValue(int digitNumbers) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < digitNumbers; i++) {
            stringBuilder.append(new Random().nextInt(10));
        }
        return stringBuilder.toString();
    }
}
