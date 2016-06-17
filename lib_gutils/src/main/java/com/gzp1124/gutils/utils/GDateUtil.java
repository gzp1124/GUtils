package com.gzp1124.gutils.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具
 * author：高志鹏 on 16/5/18 18:01
 * email:imbagaozp@163.com
 */
public class GDateUtil {
    public final static String DATE_FORMAT = "yyyy-MM-dd";
    public final static String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public final static String DATE_FORMAT_CN = "yyyy年MM月dd日";
    public final static String TIME_FORMAT_CN = "yyyy年MM月dd日 HH:mm:ss";
    public final static String MONTH_FORMAT = "yyyy-MM";
    public final static String DAY_FORMAT = "yyyyMMdd";

    /**
     * 获取当前时间戳
     * @return
     */
    public static long getsCurrentTimestamp(){
        return System.currentTimeMillis();
    }

    /**
     * 获取指定时间的时间戳
     * @param date
     * @return
     */
    public static long getSpecifiedTimestamp(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.getTimeInMillis();
    }

    /**
     * 获取指定时间的时间戳
     * @param date 2016-01-02
     * @param format "yyyy-MM-dd"
     * @return
     */
    public static long getSpecifiedTimestamp(String date,String format){
        Calendar cal = Calendar.getInstance();
        cal.setTime(getFormatDate(date,format));
        return cal.getTimeInMillis();
    }

    /**https://github.com/gzp1124/KJFrameForAndroid.git
     * 获取指定时间戳的日期对象
     * @param timestamp
     * @return
     */
    public static Date getFormatTimestampToDate(long timestamp){
        return new Date(timestamp);
    }

    /**
     * 得到重新格式化后的日期
     *
     * @param currDate 当前的日期对象
     * @param oldFormat 当前日期的格式化方式
     * @param newFormat 想要的新的格式化方式
     * @return Date 返回重新格式化后的日期对象，如默认的yyyy-MM-dd 重新格式化为 yyyy年MM月dd日
     */
    public static Date getFormatDateToDate(Date currDate,String oldFormat,String newFormat){
        return getFormatDate(getFormatDate(currDate,oldFormat),newFormat);
    }

    /**
     * 根据格式得到格式化后的日期
     *
     * @param currDate 要格式化的日期
     * @param format 日期格式，如yyyy-MM-dd
     * @see java.text.SimpleDateFormat#parse(java.lang.String)
     * @return Date 返回格式化后的日期，格式由参数<code>format</code>
     *         定义，如yyyy-MM-dd，如2006-02-15
     */
    public static Date getFormatDate(String currDate, String format) {
        if (currDate == null) {
            return null;
        }
        SimpleDateFormat dtFormatdB = null;
        try {
            dtFormatdB = new SimpleDateFormat(format);
            return dtFormatdB.parse(currDate);
        } catch (Exception e) {
            dtFormatdB = new SimpleDateFormat(DATE_FORMAT);
            try {
                return dtFormatdB.parse(currDate);
            } catch (Exception ex) {
            }
        }
        return null;
    }

    /**
     * 根据格式得到格式化后的日期
     *
     * @param currDate 要格式化的日期
     * @param format 日期格式，如yyyy-MM-dd
     * @see java.text.SimpleDateFormat#format(java.util.Date)
     * @return String 返回格式化后的日期，格式由参数<code>format</code>
     *         定义，如yyyy-MM-dd，如2006-02-15
     */
    public static String getFormatDate(java.util.Date currDate, String format) {
        if (currDate == null) {
            return "";
        }
        SimpleDateFormat dtFormatdB = null;
        try {
            dtFormatdB = new SimpleDateFormat(format);
            return dtFormatdB.format(currDate);
        } catch (Exception e) {
            dtFormatdB = new SimpleDateFormat(DATE_FORMAT);
            try {
                return dtFormatdB.format(currDate);
            } catch (Exception ex) {
            }
        }
        return null;
    }

    /**
     * 将2007-12-1变成2007-12-01。将2007-9-1变为2007-09-01。
     *
     * @param date
     * @return
     */
    public static String getFormatDateV2(String date) {
        if (date == null) {
            return null;
        }

        String[] datearr = date.split("-");
        if (datearr == null || datearr.length != 3) {
            return date;
        }

        StringBuffer ret = new StringBuffer();
        ret.append(datearr[0]);
        ret.append("-");
        ret.append(Integer.parseInt(datearr[1]) < 10 ? "0" + Integer.parseInt(datearr[1]) : datearr[1]);
        ret.append("-");
        ret.append(Integer.parseInt(datearr[2]) < 10 ? "0" + Integer.parseInt(datearr[2]) : datearr[2]);
        return ret.toString();
    }
}
