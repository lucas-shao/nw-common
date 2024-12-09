package com.normalworks.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * StringUtil
 * 字符串工具类
 *
 * @author : shaoshuai.shao
 * @version : V1.0
 * @date : 2022/6/9 10:06 AM
 */
public class StringUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(StringUtil.class);

    /**
     * 特殊字符集合
     */
    private static final String SPECIAL_CHAR_REG_EX = "[\n`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}‘；：”“’。，、？]";

    private static final char[] randomChar = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

    private static final char[] randomNumber = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    /**
     * 生成指定长度的随机字符串
     *
     * @param strLen 字符串长度
     * @return 随机字符串
     */
    public static String generateRandomString(int strLen) {

        String name = "";
        for (int i = 0; i < strLen; i++) {
            name = name + randomChar[(int) (Math.random() * randomChar.length)];
        }
        return name;
    }

    /**
     * 生成指定长度的随机数字串
     *
     * @param numberLen 数字串长度
     * @return 随机数字串
     */
    public static String generateRandomNumber(int numberLen) {

        String number = "";
        for (int i = 0; i < numberLen; i++) {
            number = number + randomNumber[(int) (Math.random() * randomNumber.length)];
        }
        return number;
    }

    /**
     * to String方法，一般用来打印自动生成的类信息，比如DO
     */
    public static String toString(Object obj) {
        if (obj == null) {
            return "NULL";
        } else if (obj instanceof String) {
            return (String) obj;
        } else if (obj instanceof List) {
            StringBuilder sb = new StringBuilder();
            ((List) obj).forEach(item -> sb.append(ToStringBuilder.reflectionToString(obj, ToStringStyle.SHORT_PREFIX_STYLE)));
            return sb.toString();
        } else {
            return ToStringBuilder.reflectionToString(obj, ToStringStyle.SHORT_PREFIX_STYLE);
        }
    }

    /**
     * 固化内容的长度
     *
     * @param textContent 待固化的内容
     * @param length      固化的长度
     * @return 固化后的内容
     */
    public static String fixContentLength(String textContent, int length) {

        if (StringUtils.isBlank(textContent)) {
            return textContent;
        }
        if (StringUtils.length(textContent) <= length) {
            return textContent;
        }
        String fixTextContent = StringUtils.substring(textContent, 0, length) + "...";
        return fixTextContent;
    }

    /**
     * 将原始字符串过滤特殊字符
     *
     * @param originalStr 原始字符串
     * @return 过滤后的字符串
     */
    public static String filterSpecialChar(String originalStr) {
        if (StringUtils.isBlank(originalStr)) {
            return null;
        }
        String filterStr = Pattern.compile(SPECIAL_CHAR_REG_EX).matcher(originalStr).replaceAll("").trim();
        return filterStr;
    }

    /**
     * 过滤文字中的html tag
     *
     * @param inputStr 输入字符串
     * @return 输出字符串
     */
    public static String filterHtmlTag(String inputStr) {
        if (StringUtils.isBlank(inputStr)) {
            return null;
        }

        // 含html标签的字符串
        String htmlStr = inputStr.trim();
        String outputStr = "";
        Pattern p_script;
        Matcher m_script;
        Pattern p_style;
        Matcher m_style;
        Pattern p_html;
        Matcher m_html;
        Pattern p_space;
        Matcher m_space;
        Pattern p_escape;
        Matcher m_escape;


        // 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
        String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";

        // 定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
        String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";

        // 定义HTML标签的正则表达式
        String regEx_html = "<[^>]+>";

        // 定义空格回车换行符
        String regEx_space = "\\s*|\t|\r|\n";

        // 定义转义字符
        String regEx_escape = "&.{2,6}?;";

        // 过滤script标签
        p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
        m_script = p_script.matcher(htmlStr);
        htmlStr = m_script.replaceAll("");

        // 过滤style标签
        p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
        m_style = p_style.matcher(htmlStr);
        htmlStr = m_style.replaceAll("");

        // 过滤html标签
        p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
        m_html = p_html.matcher(htmlStr);
        htmlStr = m_html.replaceAll("");

        // 过滤空格回车标签
        p_space = Pattern.compile(regEx_space, Pattern.CASE_INSENSITIVE);
        m_space = p_space.matcher(htmlStr);
        htmlStr = m_space.replaceAll("");

        // 过滤转义字符
        p_escape = Pattern.compile(regEx_escape, Pattern.CASE_INSENSITIVE);
        m_escape = p_escape.matcher(htmlStr);
        htmlStr = m_escape.replaceAll("");

        outputStr = htmlStr;


        // 返回文本字符串
        return outputStr;
    }

    /**
     * 过滤掉非UTF-8字符的方法
     *
     * @param source 原始字符串
     * @return 过滤掉非UTF8的字符
     */
    static public String filterOffUtf8Mb4(String source) {

        try {
            byte[] bytes = source.getBytes("UTF-8");
            ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
            int i = 0;
            while (i < bytes.length) {
                short b = bytes[i];
                if (b > 0) {
                    buffer.put(bytes[i++]);
                    continue;
                }
                b += 256;
                if ((b ^ 0xC0) >> 4 == 0) {
                    buffer.put(bytes, i, 2);
                    i += 2;
                } else if ((b ^ 0xE0) >> 4 == 0) {
                    buffer.put(bytes, i, 3);
                    i += 3;
                } else if ((b ^ 0xF0) >> 4 == 0) {
                    i += 4;
                }
            }
            buffer.flip();
            return new String(buffer.array(), "utf-8");
        } catch (Exception e) {
            LoggerUtil.error(LOGGER, "过滤非UTF-8字符串方法有误，返回原字符串", e);
            return source;
        }
    }

    /**
     * 去掉换行,使用protobuf之后,直接打印protobuf模型会带换行,影响查看日志效率.
     */
    public static String removeEnter(String text) {
        return text.replaceAll("\r\n", "").replaceAll("\r", "").replaceAll("\n", "");
    }

    /**
     * 将两个字符串trim后比对
     *
     * @param cs1 字符串1
     * @param cs2 字符串2
     * @return 比对结果
     */
    public static boolean equalsAfterTrim(String cs1, String cs2) {
        String trimStr1 = (cs1 == null ? null : cs1.trim());
        String trimStr2 = (cs2 == null ? null : cs2.trim());
        return StringUtils.equals(trimStr1, trimStr2);
    }

    /**
     * 生成MD5摘要
     *
     * @param input
     * @return
     */
    public static String generateDigestByMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < messageDigest.length; i++) {
                sb.append(String.format("%02x", messageDigest[i]));
            }
            return sb.toString();
        } catch (Exception e) {
            LoggerUtil.error(LOGGER, "generateMD5Digest error", e);
            return null;
        }
    }

    /**
     * 判断字符串是否是Double类型
     */
    public static Boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return Boolean.TRUE;
        } catch (Exception e) {
            return Boolean.FALSE;
        }
    }

    /**
     * 判断字符串是否是Float类型
     */
    public static Boolean isFloat(String str) {
        try {
            Float.parseFloat(str);
            return Boolean.TRUE;
        } catch (Exception e) {
            return Boolean.FALSE;
        }
    }
}
