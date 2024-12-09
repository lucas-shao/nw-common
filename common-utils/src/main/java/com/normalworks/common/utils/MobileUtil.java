package com.normalworks.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * MobileUtil
 * 手机号工具类
 *
 * @author : shaoshuai.shao
 * @version : V1.0
 * @date : 2022/6/14 10:06 AM
 */
public class MobileUtil {

    /**
     * 中国大陆手机号正则表达式
     */
    private static final String CHINESE_MAINLAND_MOBILE_NO_REGEX = "^((13[0-9])|(14[0-9])|(15([0-9]))|(16[0-9])|(17[0-8])|(18[0-9])|(19[0-9]))\\d{8}$";

    /**
     * 测试手机号列表
     * 登录无须验证码
     */
    private static final List<String> TEST_MOBILE_NO_LIST = Arrays.asList("13474651955");

    /**
     * 是否为中国大陆手机号
     *
     * @param mobileNo
     * @return
     */
    public static boolean isChineseMainLandMobileNo(String mobileNo) {
        if (StringUtils.isBlank(mobileNo)) {
            return false;
        }
        if (mobileNo.length() != 11) {
            return false;
        } else {
            Pattern pattern = Pattern.compile(CHINESE_MAINLAND_MOBILE_NO_REGEX);
            Matcher matcher = pattern.matcher(mobileNo);
            return matcher.matches();
        }
    }

    /**
     * 是否是测试用的手机号
     * 无须验证手机验证码
     *
     * @param mobileNo 手机号
     * @return
     */
    public static boolean isTestMobileNo(String mobileNo) {

        return TEST_MOBILE_NO_LIST.contains(mobileNo);
    }

    public static void main(String[] args) {
        String mobileNo = "15057161158";
        System.out.println(isTestMobileNo(mobileNo));
    }
}
