package com.normalworks.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.threeten.bp.Instant;
import org.threeten.bp.LocalDate;
import org.threeten.bp.OffsetDateTime;
import org.threeten.bp.ZoneId;
import org.threeten.bp.format.DateTimeFormatter;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import static java.util.Calendar.MILLISECOND;

/**
 * DateUtil
 * 日期时间工具类
 *
 * @author : shaoshuai
 * @version V1.0
 * @date Date : 2021年12月28日 2:10 下午
 */
public final class DateUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(DateUtil.class);

    /**
     * 注意注意！！！
     * yyyy必须小写，不能写成大写的YYYY，否则会导致日期转换出现问题
     * 之前出现过bug Sun Dec 31 00:00:00 CST 2023 ， 就转化为2024-12-31
     */
    public static final String YYYYMMDD = "yyyyMMdd";
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String YYYYMM = "yyyyMM";
    public static final String YYYY = "yyyy";
    public static final String MMMM_YYYY = "MMMM yyyy";

    /**
     * 比如 Jan 2024
     */
    public static final String MMM_YYYY = "MMM yyyy";

    public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";

    // 请求时间戳格式。日期格式按照ISO8601标准表示，并需要使用UTC时间。格式为yyyy-MM-DDThh:mm:ssZ。
    // 例如，2018-05-10T12:00:00Z（为北京时间2018年5月10日20点0分0秒）
    public static final String TIMESTAMP_UTC_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    // UTC毫秒时间格式
    // 例如，2017-08-09T22:00:19.652Z
    public static final String TIMESTAMP_UTC_DATE_WITH_MILLISECOND_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.s'Z'";
    public static final String DEFAULT_TIME_ZONE = "UTC";

    /**
     * 返回不带毫秒的Date
     * <p>
     * 由于mysql的timestamp只保留到秒，对毫秒会做进位处理，所以会导致一个时间存到数据库再查出来之后，秒不一致
     * 另外也是对new Date()的统一收口
     *
     * @return
     */
    public static Date curr() {
        Calendar calendar = initInstance(new Date());
        calendar.set(MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 返回带毫秒的Date
     * <p>
     * 一般用于系统统计处理耗时等不需要存储到数据库的场景
     *
     * @return
     */
    public static Date currWithMills() {
        return new Date();
    }

    /*****************************************************/
    /******************* Formatter ***********************/
    /*****************************************************/

    /**
     * 将日期格式转为文本类型
     *
     * @param time 日期
     * @return
     */
    public static String formatDate(Date time) {
        return formatDate(time, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 将日期格式转为文本类型
     *
     * @param time       日期
     * @param dateFormat 日期格式
     * @return
     */
    public static String formatDate(Date time, String dateFormat) {
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat, Locale.UK);
        formatter.setTimeZone(TimeZone.getTimeZone(DEFAULT_TIME_ZONE));
        return formatter.format(time);
    }

    /**
     * 将时间转化为类似格式「January 2024」
     *
     * @param date
     * @return
     */
    public static String formatMMMMyyyy(Date date) {
        return formatDate(date, MMMM_YYYY);
    }

    public static String formatYYYYMMDD(Date date) {
        return formatDate(date, YYYYMMDD);
    }

    public static String formatFromYYYYMMDDToYYYY_MM_DD(String dataStr) {
        Date date = parseYYYYMMDD(dataStr);
        return formatDate(date, YYYY_MM_DD);
    }

    public static String formatFromYYYY_MM_DDToYYYYMMDD(String dataStr) {
        Date date = parseDate(dataStr, YYYY_MM_DD);
        return formatYYYYMMDD(date);
    }

    public static String formatYYYYMM(Date date) {
        return formatDate(date, YYYYMM);
    }

    public static String formatYYYY_MM_DD(Date date) {
        return formatDate(date, YYYY_MM_DD);
    }

    public static String formatYYYY_MM_DD_HH_MM(Date date) {
        return formatDate(date, YYYY_MM_DD_HH_MM);
    }

    /******************************************************/
    /******************* Parser ****************************/
    /******************************************************/


    /**
     * 字符串通过SimpleDateFormat转Date类型，并且带时区信息
     */
    public static Date parseDateWithSimpleDateFormatAndTimeZone(String dateStr, String format, String timeZone) {

        if (StringUtils.isBlank(dateStr) || StringUtils.isBlank(format)) {
            return null;
        }

        try {
            SimpleDateFormat formatter = new SimpleDateFormat(format);
            formatter.setTimeZone(TimeZone.getTimeZone(timeZone));
            return formatter.parse(dateStr);
        } catch (Exception exception) {
            LOGGER.warn("时间格式转化存在异常，dateStr = " + dateStr + " , format =" + format, exception);
            return null;
        }
    }

    /**
     * 字符串转Date类型
     * <p>
     * Deprecated: 请使用带Locale入参的parseDate方法
     *
     * @param dateStr 时间字符串
     * @param format  时间格式
     * @return Date类型
     */
    public static Date parseDate(String dateStr, String format) {
        return parseDateWithSimpleDateFormatAndTimeZone(dateStr, format, "UTC");
    }


    public static Date parseYYYYMMDD(String dateStr, String timeZone) {
        return parseDateWithSimpleDateFormatAndTimeZone(dateStr, YYYYMMDD, timeZone);
    }

    /**
     * 月份转Date类型
     * <p>
     * 为了避免犹豫时区问题，导致将Date转回月份时跨月，会转成对应月份的15日
     *
     * @param dateStr yyyyMM
     * @return
     */
    public static Date parseYYYYMMToMidMonth(String dateStr) {
        return parseDateWithSimpleDateFormatAndTimeZone(dateStr + "15", YYYYMMDD, DEFAULT_TIME_ZONE);
    }

    public static Date parseYYYYMMDD(String dateStr) {
        return parseDateWithSimpleDateFormatAndTimeZone(dateStr, YYYYMMDD, DEFAULT_TIME_ZONE);
    }


    /**
     * 从baseDate当天的0点0时0分开始，增加天数，小时数，分钟数，秒数
     *
     * @param baseDate   基数日期
     * @param addDays    增加天数
     * @param addHours   增加小时数
     * @param addMinutes 增加分钟数
     * @param addSeconds 增加秒数
     * @return
     */
    public static Date generateDateWithInc(Date baseDate, int addDays, int addHours, int addMinutes, int addSeconds) {

        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone(DEFAULT_TIME_ZONE));
            String baseDayStr = simpleDateFormat.format(baseDate);
            Date baseDay = DateUtils.parseDate(baseDayStr, "yyyy-MM-dd");
            Date baseDayAddDays = DateUtils.addDays(baseDay, addDays);
            Date baseDayAddDaysAddHours = DateUtils.addHours(baseDayAddDays, addHours);
            Date baseDayAddDaysAddHoursAndAddMinutes = DateUtils.addMinutes(baseDayAddDaysAddHours, addMinutes);
            return DateUtils.addSeconds(baseDayAddDaysAddHoursAndAddMinutes, addSeconds);
        } catch (Exception exception) {
            LOGGER.warn("时间增量存在异常，baseDate = " + baseDate + " , addDays =" + addDays + " , addHours = " + addHours + " , addMinutes = " + addMinutes + " , addSeconds = " + addSeconds, exception);
            return null;
        }
    }

    /**
     * 获取统计日期（20220314）
     *
     * @return
     */
    public static String obtainSummaryDate(Date date) {

        return formatDate(date, YYYYMMDD);
    }

    /**
     * 获取昨天的统计日期（20220315）
     *
     * @return 统计日期
     */
    public static String obtainYesterdaySummaryDate() {

        Date yesterday = generateDateWithInc(new Date(), -1, 0, 0, 0);
        return formatDate(yesterday, YYYYMMDD);
    }

    /**
     * 获取指定月份
     *
     * @param dateStr yyyyMM
     * @param amount  加多少个月，可以是负数，表示减
     * @return yyyyMM
     */
    public static String addMonth(String dateStr, int amount) {
        Date date = parseYYYYMMToMidMonth(dateStr);
        Date nextMonth = DateUtils.addMonths(date, amount);
        return formatYYYYMM(nextMonth);
    }

    /**
     * 获取指定日期的下一个月
     *
     * @param dateStr yyyyMM
     * @return 获取月份的第一天0时0分0秒
     */
    public static Date parseFirstDateOfMonthStr(String dateStr) {
        return getFirstDateOfMonthInDate(parseYYYYMMToMidMonth(dateStr));
    }

    /**
     * 获取指定日期的最后一天
     *
     * @param dateStr yyyyMM
     * @return 获取月份的最后一天23时59分59秒
     */
    public static Date parseLastDateOfMonthStr(String dateStr) {
        return getLastDateOfMonthInDate(parseYYYYMMToMidMonth(dateStr));
    }

    /**
     * 获取第二天的日期，出参、入参都是YYYYMMDD格式
     *
     * @param date
     * @return
     */
    public static String getTomorrow(String date) {

        Date tomorrowDate = getTomorrowDate(date);
        return formatYYYYMMDD(tomorrowDate);
    }


    public static String getYesterday(String date) {
        Date date1 = parseYYYYMMDD(date);
        Date yesterday = generateDateWithInc(date1, -1, 0, 0, 0);

        return formatYYYYMMDD(yesterday);
    }

    /**
     * 获取第二天的Date，时分秒都是0
     *
     * @param date
     * @return
     */
    public static Date getTomorrowDate(String date) {
        Date date1 = parseYYYYMMDD(date);

        generateDateWithInc(date1, 1, 0, 0, 0);

        Calendar calendar = initInstance(date1);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Date tomorrowDate = calendar.getTime();
        return tomorrowDate;
    }

    /**
     * 计算两个日期间的间隔天数
     *
     * @param dayOne 日期1
     * @param dayTwo 日期2
     * @return 间隔天数
     */
    public static Long calculateIntervalDays(Date dayOne, Date dayTwo) {

        try {
            // 首先按照日期远近，做日期的规整
            Date startDay = null;
            Date endDay = null;
            if (dayOne.before(dayTwo)) {
                startDay = dayOne;
                endDay = dayTwo;
            } else {
                startDay = dayTwo;
                endDay = dayOne;
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            long startDateTime = dateFormat.parse(dateFormat.format(startDay)).getTime();
            long endDateTime = dateFormat.parse(dateFormat.format(endDay)).getTime();
            return Long.valueOf((endDateTime - startDateTime) / (1000 * 3600 * 24));

        } catch (Exception exception) {

            LOGGER.error("时间转化出现异常", exception);
            return null;
        }
    }

    /**
     * 按指定时区计算两个日期的间隔天数
     */
    public static Long calculateIntervalDays(Date dayOne, Date dayTwo, String timeZone) {

        try {
            // 首先按照日期远近，做日期的规整
            Date startDay = null;
            Date endDay = null;
            if (dayOne.before(dayTwo)) {
                startDay = dayOne;
                endDay = dayTwo;
            } else {
                startDay = dayTwo;
                endDay = dayOne;
            }

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
            long startDateTime = dateFormat.parse(dateFormat.format(startDay)).getTime();
            long endDateTime = dateFormat.parse(dateFormat.format(endDay)).getTime();
            return Long.valueOf((endDateTime - startDateTime) / (1000 * 3600 * 24));

        } catch (Exception exception) {
            LOGGER.error("时间转化出现异常,dayOne=" + dayOne + ",dayTwo=" + dayTwo + ",timeZone=" + timeZone, exception);
            return null;
        }
    }

    /**
     * 返回 YYYYMMDD 00:00:00
     *
     * @param dateStr YYYYMMDD
     * @return
     */
    public static Date parseYYYYMMDDBegin(String dateStr) {
        Date date = parseYYYYMMDD(dateStr);
        return date;
    }

    /**
     * 返回 YYYYMMDD 23:59:59
     *
     * @param dateStr YYYYMMDD
     * @return
     */
    public static Date parseYYYYMMDDEnd(String dateStr) {

        return generateDateWithInc(parseYYYYMMDD(dateStr), 0, 23, 59, 59);
    }

    /**
     * 按数据库格式（YYYY-MM-dd），获取日期当月最早的一天
     *
     * @param date
     * @return
     */
    public static String getFirstDateOfMonthInDBFormat(Date date) {
        Calendar calendar = initInstance(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        return formatDate(calendar.getTime(), YYYY_MM_DD);
    }

    /**
     * 按数据库格式（YYYY-MM-dd），获取日期当月最晚的一天
     *
     * @param date
     * @return
     */
    public static String getLastDateOfMonthInDBFormat(Date date) {
        Calendar calendar = initInstance(date);
        int maximumDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        calendar.set(Calendar.DAY_OF_MONTH, maximumDay);

        return formatDate(calendar.getTime(), YYYY_MM_DD);
    }

    /**
     * 按数据库格式，获取日期
     *
     * @param date
     * @return
     */
    public static String formatForDBFormat(Date date) {
        return formatDate(date, YYYY_MM_DD);
    }

    /**
     * format utc 日期
     *
     * @param date
     * @return
     */
    public static String formatUtcDate(Date date) {
        SimpleDateFormat utcDateFormat = new SimpleDateFormat(TIMESTAMP_UTC_DATE_FORMAT);
        utcDateFormat.setTimeZone(TimeZone.getTimeZone(DEFAULT_TIME_ZONE));
        return utcDateFormat.format(date);
    }

    /**
     * parse utc 日期
     * 例如：2018-05-10T12:00:00Z
     */
    public static Date parseUtcDate(String utcDateStr) {

        try {
            SimpleDateFormat utcDateFormat = new SimpleDateFormat(TIMESTAMP_UTC_DATE_FORMAT);
            utcDateFormat.setTimeZone(TimeZone.getTimeZone(DEFAULT_TIME_ZONE));

            return utcDateFormat.parse(utcDateStr);

        } catch (Exception exception) {

            LOGGER.error("解析UTC日期失败", exception);
            return null;
        }
    }

    /**
     * parse utc 日期，带毫秒
     * 例如：2017-08-09T22:00:19.652Z
     */
    public static Date parseUtcDateWithMillisecond(String utcDateStr) {

        try {
            SimpleDateFormat utcDateFormat = new SimpleDateFormat(TIMESTAMP_UTC_DATE_WITH_MILLISECOND_FORMAT);
            utcDateFormat.setTimeZone(TimeZone.getTimeZone(DEFAULT_TIME_ZONE));

            return utcDateFormat.parse(utcDateStr);

        } catch (Exception exception) {

            LOGGER.error("解析UTC日期失败", exception);
            return null;
        }
    }

    /**
     * A时间是否小于B时间
     */
    public static boolean aSmallerThanB(Date a, Date b) {
        return a.getTime() < b.getTime();
    }

    /**
     * A时间是否小于等于B时间
     */
    public static boolean aSmallerOrEqualThanB(Date a, Date b) {
        return a.getTime() <= b.getTime();
    }

    /**
     * 当前日期
     */
    public static String currYYYYMMDD() {
        return formatYYYYMMDD(curr());
    }

    /**
     * 计算该日期离当前还有多少毫秒，结果大于等于0
     *
     * @param date 未来的日期
     * @return 毫秒数
     */
    public static long calculateTimeLeftInMillisecond(Date date) {

        long left = date.getTime() - DateUtil.curr().getTime();
        if (left < 0) {
            left = 0;
        }
        return left;
    }

    /**
     * 将日期进行本地化输出，含时间信息
     * 例如：21 Dec 2022, 16:00:00
     *
     * @param date   日期
     * @param locale 本地化
     * @return 本地化输出
     */
    public static String formatMediumDateTimeByLocale(Date date, Locale locale) {
        return SimpleDateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM, locale).format(date);
    }

    /**
     * 将日期进行本地化输出，含时间信息
     * 例如：21 Dec 2022
     *
     * @param date   日期
     * @param locale 本地化
     * @return 本地化输出
     */
    public static String formatMediumDateByLocale(Date date, Locale locale) {
        return SimpleDateFormat.getDateInstance(DateFormat.MEDIUM, locale).format(date);
    }

    /**
     * 将日期进行本地化输出，含时间信息
     * 例如：21 Dec 2022, 16:00:00
     *
     * @param date   日期
     * @param locale 本地化
     * @return 本地化输出
     */
    public static String formatMediumDateTime(Date date, Locale locale, String timezone) {
        DateFormat dateFormat = SimpleDateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM, locale);
        dateFormat.setTimeZone(TimeZone.getTimeZone(timezone));
        return dateFormat.format(date);
    }

    /**
     * 将日期进行本地化输出，含时间信息
     * 例如：21 Dec 2022
     *
     * @param date   日期
     * @param locale 本地化
     * @return 本地化输出
     */
    public static String formatMediumDate(Date date, Locale locale, String timezone) {
        DateFormat dateFormat = SimpleDateFormat.getDateInstance(DateFormat.MEDIUM, locale);
        dateFormat.setTimeZone(TimeZone.getTimeZone(timezone));
        return dateFormat.format(date);
    }

    /**
     * 将字符串日期转化为Date类型
     * <p>
     * 例如：
     * 26/3/2021
     * 31/08/2020
     * 24-Feb-22
     * 29.06.2021
     * May 27th, 2020
     *
     * @param timeZone
     * @return
     */
    public static Date parseDateWithTimeZone(String dateStr, String timeZone) {

        // 尝试日期类型Sample：29.06.22
        // 注意每个相似类型 yy 一定要在yyyy的前面先匹配
        Date date = parseDateWithSimpleDateFormatAndTimeZone(dateStr, "dd.MM.yy", timeZone);
        if (date != null) {
            return date;
        }

        // 尝试日期类型Sample：29.06.2021
        date = parseDateWithSimpleDateFormatAndTimeZone(dateStr, "dd.MM.yyyy", timeZone);
        if (date != null) {
            return date;
        }

        // 尝试日期类型Sample：9.6.21
        // 注意每个相似类型 yy 一定要在yyyy的前面先匹配
        date = parseDateWithSimpleDateFormatAndTimeZone(dateStr, "d.M.yy", timeZone);
        if (date != null) {
            return date;
        }

        // 尝试日期类型Sample：9.6.2021
        date = parseDateWithSimpleDateFormatAndTimeZone(dateStr, "d.M.yyyy", timeZone);
        if (date != null) {
            return date;
        }

        // 尝试日期类型Sample：24-Feb-22
        // 注意每个相似类型 yy 一定要在yyyy的前面先匹配
        date = parseDateWithSimpleDateFormatAndTimeZone(dateStr, "dd-MMM-yy", timeZone);
        if (date != null) {
            return date;
        }

        // 尝试日期类型Sample：24-Feb-2022
        date = parseDateWithSimpleDateFormatAndTimeZone(dateStr, "dd-MMM-yyyy", timeZone);
        if (date != null) {
            return date;
        }

        // 尝试日期类型Sample：31/08/22
        // 注意每个相似类型 yy 一定要在yyyy的前面先匹配
        date = parseDateWithSimpleDateFormatAndTimeZone(dateStr, "dd/MM/yy", timeZone);
        if (date != null) {
            return date;
        }

        // 尝试日期类型Sample：31/08/2020
        date = parseDateWithSimpleDateFormatAndTimeZone(dateStr, "dd/MM/yyyy", timeZone);
        if (date != null) {
            return date;
        }

        // 尝试日期类型Sample：29-01-21
        // 注意每个相似类型 yy 一定要在yyyy的前面先匹配
        date = parseDateWithSimpleDateFormatAndTimeZone(dateStr, "dd-MM-yy", timeZone);
        if (date != null) {
            return date;
        }

        // 尝试日期类型Sample：29-01-2021
        date = parseDateWithSimpleDateFormatAndTimeZone(dateStr, "dd-MM-yyyy", timeZone);
        if (date != null) {
            return date;
        }

        // 尝试日期类型Sample：22-01-01
        // 注意每个相似类型 yy 一定要在yyyy的前面先匹配
        date = parseDateWithSimpleDateFormatAndTimeZone(dateStr, "yy-MM-dd", timeZone);
        if (date != null) {
            return date;
        }

        // 尝试日期类型Sample：2022-01-01
        date = parseDateWithSimpleDateFormatAndTimeZone(dateStr, "yyyy-MM-dd", timeZone);
        if (date != null) {
            return date;
        }

        // 尝试日期类型Sample：May 27th, 22
        // 注意每个相似类型 yy 一定要在yyyy的前面先匹配
        date = parseDateWithSimpleDateFormatAndTimeZone(dateStr, "MMM dd',' yy", timeZone);
        if (date != null) {
            return date;
        }

        // 尝试日期类型Sample：May 27th, 2020
        date = parseDateWithSimpleDateFormatAndTimeZone(dateStr, "MMM dd',' yyyy", timeZone);
        if (date != null) {
            return date;
        }

        // 尝试日期类型Sample：08/31/22
        // 注意每个相似类型 yy 一定要在yyyy的前面先匹配
        date = parseDateWithSimpleDateFormatAndTimeZone(dateStr, "MM/dd/yy", timeZone);
        if (date != null) {
            return date;
        }

        // 尝试日期类型Sample：08/31/2020
        date = parseDateWithSimpleDateFormatAndTimeZone(dateStr, "MM/dd/yyyy", timeZone);
        if (date != null) {
            return date;
        }

        // 尝试日期类型Sample：15 December 22
        // 注意每个相似类型 yy 一定要在yyyy的前面先匹配
        date = parseDateWithSimpleDateFormatAndTimeZone(dateStr, "dd MMMM yy", timeZone);
        if (date != null) {
            return date;
        }

        // 尝试日期类型Sample：15 December 2022
        date = parseDateWithSimpleDateFormatAndTimeZone(dateStr, "dd MMMM yyyy", timeZone);
        if (date != null) {
            return date;
        }

        // 尝试日期类型Sample：5 December 22
        // 注意每个相似类型 yy 一定要在yyyy的前面先匹配
        date = parseDateWithSimpleDateFormatAndTimeZone(dateStr, "d MMMM yy", timeZone);
        if (date != null) {
            return date;
        }

        // 尝试日期类型Sample：5 December 2022
        date = parseDateWithSimpleDateFormatAndTimeZone(dateStr, "d MMMM yyyy", timeZone);
        if (date != null) {
            return date;
        }

        LOGGER.error("出现不支持转化的日期格式：dateStr = " + dateStr);

        return null;
    }

    public static OffsetDateTime parseToOffsetDateTime(Date date) {

        OffsetDateTime offsetDateTime = null;
        if (date != null) {
            Instant instant = Instant.ofEpochMilli(date.getTime());
            offsetDateTime = OffsetDateTime.ofInstant(instant, ZoneId.systemDefault());
        }

        return offsetDateTime;
    }

    public static Date parseToDate(OffsetDateTime offsetDateTime) {
        java.time.Instant instant = java.time.Instant.ofEpochSecond(offsetDateTime.toEpochSecond());
        return Date.from(instant);
    }

    public static String formatToYYYYMMDD(LocalDate localDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(YYYYMMDD);
        return localDate.format(formatter);
    }

    public static LocalDate parseYYYYMMDDToLocalDate(String date, Locale locale) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern(YYYYMMDD, locale));
    }

    /**
     * @param date YYYYmm
     * @return 指定年份、月份的最大天数
     */
    public static String getActualMaximumDay(String date) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(DEFAULT_TIME_ZONE));
        calendar.set(Calendar.YEAR, Integer.parseInt(date.substring(0, 4)));
        calendar.set(Calendar.MONTH, Integer.parseInt(date.substring(4, 6)) - 1);
        return String.valueOf(calendar.getActualMaximum(Calendar.DAY_OF_MONTH));

    }

    /**
     * 获取对应的月份英文名称
     *
     * @param month 12
     * @return December
     */
    public static String getMonthString(int month) {
        DateFormatSymbols dfs = new DateFormatSymbols();
        return dfs.getMonths()[month - 1];
    }

    public static String getFirstDateOfMonth(Date date) {
        Date date1 = getFirstDateOfMonthInDate(date);
        return formatYYYYMMDD(date1);
    }

    public static Date getFirstDateOfMonthInDate(Date date1) {
        Calendar calendar = initInstance(date1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    public static String getLastDateOfMonth(Date date1) {
        Date date = getLastDateOfMonthInDate(date1);
        return formatYYYYMMDD(date);
    }

    public static Date getLastDateOfMonthInDate(Date date1) {
        Calendar calendar = initInstance(date1);
        int currentDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        int maxDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        Date date = generateDateWithInc(date1, maxDayOfMonth - currentDayOfMonth, 23, 59, 59);
        return DateUtils.addMilliseconds(date, 999);
    }

    /**
     * @param yearAndMonth yyyyMM
     * @return 该月份最后一天最后一秒
     */
    public static Date parseWithLastDateOfMonth(String yearAndMonth) {
        Date date = parseDate(yearAndMonth, YYYYMM);
        return getLastDateOfMonthInDate(date);
    }

    public static Date parseWithLastDateOfMonth(String year, String month) {
        Date date = parseDate(year + month, YYYYMM);
        return getLastDateOfMonthInDate(date);
    }

    /**
     * 获取当月最后一个工作日
     */
    public static Date getLastWorkingDateOfMonthInDate(Date date) {
        Date lastDateOfMonth = getLastDateOfMonthInDate(date);
        Calendar calendar = initInstance(lastDateOfMonth);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == Calendar.SATURDAY) {
            calendar.add(Calendar.DAY_OF_MONTH, -1);
        } else if (dayOfWeek == Calendar.SUNDAY) {
            calendar.add(Calendar.DAY_OF_MONTH, -2);
        }
        return calendar.getTime();
    }

    /**
     * 只比较日期，不比较时间
     *
     * @return date1 比 date2 大:true, 否则:false
     */
    public static boolean date1BeforeDate2ByDay(Date date1, Date date2) {
        String date1Str = formatYYYYMMDD(date1);
        String date2Str = formatYYYYMMDD(date2);
        return parseYYYYMMDD(date1Str).before(parseYYYYMMDD(date2Str));
    }

    /**
     * 只比较日期，不比较时间
     */
    public static boolean date1BeforeOrEqualsDate2ByDay(Date date1, Date date2) {
        String date1Str = formatYYYYMMDD(date1);
        String date2Str = formatYYYYMMDD(date2);
        return !parseYYYYMMDD(date1Str).after(parseYYYYMMDD(date2Str));
    }

    private static Calendar initInstance(Date date1) {
        Calendar instance = Calendar.getInstance(TimeZone.getTimeZone(DEFAULT_TIME_ZONE));
        instance.setTime(date1);
        return instance;
    }

    public static void main(String[] args) {
        System.out.println(formatMMMMyyyy(new Date()));
    }

}
