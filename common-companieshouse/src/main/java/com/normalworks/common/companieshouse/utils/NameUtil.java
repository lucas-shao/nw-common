package com.normalworks.common.companieshouse.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * NameUtil
 * 名称工具类
 *
 * @author : shaoshuai.shao
 * @version : V1.0
 * @date : 2023/11/27 10:25 AM
 */
public class NameUtil {

    /**
     * 获取名称中的名
     * <p>
     * YE, Pengcheng 。 其中YE为lastname，Pengcheng为firstname
     * DU, Mi, Dr 。 其中DU为lastname，Mi为firstname，Dr为title
     *
     * @param name
     * @return
     */
    public static String fetchFirstName(String name) {

        if (StringUtils.isBlank(name)) {
            return "";
        }

        String firstNameAndTitle = StringUtils.substringAfter(name, ",");
        String firstName = StringUtils.trim(StringUtils.substringBefore(firstNameAndTitle, ","));

        return firstName;
    }

    /**
     * 获取名称中的姓
     * <p>
     * YE, Pengcheng 。 其中YE为lastname，Pengcheng为firstname
     * DU, Mi, Dr 。 其中DU为lastname，Mi为firstname，Dr为title
     *
     * @param name
     * @return
     */
    public static String fetchLastName(String name) {

        if (StringUtils.isBlank(name)) {
            return "";
        }

        String lastName = StringUtils.trim(StringUtils.substringBefore(name, ","));

        return lastName;
    }

    public static void main(String[] args) {
        System.out.println(fetchFirstName("YE, Pengcheng"));
        System.out.println(fetchLastName("YE, Pengcheng"));

        System.out.println(fetchFirstName("DU, Mi, Dr"));
        System.out.println(fetchLastName("DU, Mi, Dr"));
    }
}
