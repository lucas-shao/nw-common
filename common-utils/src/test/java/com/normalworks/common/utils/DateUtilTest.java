package com.normalworks.common.utils;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;
import org.threeten.bp.LocalDate;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import static com.normalworks.common.utils.DateUtil.formatMediumDateTime;

/**
 * DateUtilTest
 *
 * @author: lingeng
 * @date: 12/21/22
 */
public class DateUtilTest {

    @Test
    public void getFirstDayOfMonth() {
        Date firstDateOfMonthInDate = DateUtil.getFirstDateOfMonthInDate(DateUtil.parseYYYYMMDD("20241106"));
        System.out.println(firstDateOfMonthInDate);
        Date firstDateOfMonthInDate1 = DateUtil.getFirstDateOfMonthInDate(DateUtil.parseYYYYMMDD("20241201"));
        System.out.println(firstDateOfMonthInDate1);
    }

    @Test
    public void test11() {
        Date date = DateUtil.parseWithLastDateOfMonth("2023", "02");
        System.out.println(date);
    }

    @Test
    public void test() throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = dateFormat.parse("20221222");

        System.out.println(date);

        System.out.println(formatMediumDateTime(date, Locale.CHINA, "Asia/Hong_Kong"));
        System.out.println(formatMediumDateTime(date, Locale.UK, "UTC"));
    }

    @Test
    public void testCompareToDay() {
        Date date1 = DateUtil.curr();
        Date date2 = DateUtils.addHours(date1, 16);
        String date1Str = DateUtil.formatYYYYMMDD(date1);
        String date2Str = DateUtil.formatYYYYMMDD(date2);
        System.out.println(date1Str);
        System.out.println(date2Str);
        boolean b = DateUtil.date1BeforeDate2ByDay(date1, date2);
        System.out.println(date1);
        System.out.println(date2);
        System.out.println("date1 before date2:" + b);
    }

    @Test
    public void test1() {

        System.out.println(DateUtil.getActualMaximumDay("202302"));
        System.out.println(DateUtil.getActualMaximumDay("202308"));
    }

    @Test
    public void test2() {

        //Setup runtime timezone to UTC
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

        printDate();

        TimeZone.setDefault(null);
        printDate();
    }

    private static void printDate() {
        Date date1 = DateUtil.parseDate("2023-08-06T13:26:23Z", DateUtil.TIMESTAMP_UTC_DATE_FORMAT);

        String firstDateOfMonth = DateUtil.getFirstDateOfMonth(date1);
        System.out.println(firstDateOfMonth);

        Date firstDateOfMonthInDate = DateUtil.getFirstDateOfMonthInDate(date1);
        System.out.println(firstDateOfMonthInDate);

        String lastDateOfMonth = DateUtil.getLastDateOfMonth(date1);
        System.out.println(lastDateOfMonth);

        Date lastDateOfMonthInDate = DateUtil.getLastDateOfMonthInDate(date1);
        System.out.println(lastDateOfMonthInDate);
    }

    @Test
    public void test3() {
        LocalDate date = DateUtil.parseYYYYMMDDToLocalDate("20231231", Locale.UK);
        System.out.println(date);
    }
}
