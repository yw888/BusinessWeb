package com.neuedu.utils;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;

/**
 * 时间处理的工具类
 */
public class DateUtil {

    private static final String STANDFORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 将字符串转Date
     * @param datestr 字符串时间
     * @param formatstr 时间格式
     */
    public static Date stringToDate(String datestr, String formatstr){
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(formatstr);
        DateTime dateTime = dateTimeFormatter.parseDateTime(datestr);
        return dateTime.toDate();
    }

    public static Date stringToDate(String datestr){
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(STANDFORMAT);
        DateTime dateTime = dateTimeFormatter.parseDateTime(datestr);
        return dateTime.toDate();
    }

    /**
     * 将Date转字符串
     */

    public static String dateToString(Date date, String formatstr){
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(formatstr);
    }

    public static String dateToString(Date date){
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(STANDFORMAT);
    }
}
