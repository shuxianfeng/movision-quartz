package com.movision.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 */
public class DateUtil {

    private static Logger log = LoggerFactory.getLogger(DateUtil.class);

    public static String date2Str(Date target, SimpleDateFormat pattern) {
        if (null == target || null == pattern) {
            return "";
        }
        String targetStr = "";
        try {
            targetStr = pattern.format(target);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return targetStr;
    }

	/**
	 * 获取间隔时间
	 * @param str1 开始时间
	 * @param str2 结束时间
	 * @return
	 */
    public static long getDistanceHours(String str1, String str2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startDate;
        Date endDate;

        long elapsedHours = 0;
        try {
            startDate = df.parse(str1);
            endDate = df.parse(str2);
            //milliseconds
            long different = endDate.getTime() - startDate.getTime();
            long secondsInMilli = 1000;
            long minutesInMilli = secondsInMilli * 60;
            long hoursInMilli = minutesInMilli * 60;
            elapsedHours = different / hoursInMilli;

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return elapsedHours;
    }

    /**
     * 两个时间之间相差距离多少天
     *
     * @param str1 时间参数 1：
     * @param str2 时间参数 2：
     * @return 相差天数
     */
    public static Long getDistanceDays(String str1, String str2) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date one;
        Date two;
        Long days = 0L;
        try {
            one = df.parse(str1);
            two = df.parse(str2);
            Long time1 = one.getTime();
            Long time2 = two.getTime();
            Long diff;
            if (time1 < time2) {
                diff = time2 - time1;
            } else {
                diff = time1 - time2;
            }
            days = diff / (1000 * 60 * 60 * 24);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return days;
    }

    /**
     * 两个时间相差距离多少天多少小时多少分多少秒
     *
     * @param str1 时间参数 1 格式：1990-01-01 12:00:00
     * @param str2 时间参数 2 格式：2009-01-01 12:00:00
     * @return long[] 返回值为：{天, 时, 分, 秒}
     */
    public static long[] getDistanceTimes(String str1, String str2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date one;
        Date two;
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        try {
            one = df.parse(str1);
            two = df.parse(str2);
            long time1 = one.getTime();
            long time2 = two.getTime();
            long diff;
            if (time1 < time2) {
                diff = time2 - time1;
            } else {
                diff = time1 - time2;
            }
            day = diff / (24 * 60 * 60 * 1000);
            hour = (diff / (60 * 60 * 1000) - day * 24);
            min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
            sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new long[]{day, hour, min, sec};
    }

    /**
     * 两个时间相差距离多少天多少小时多少分多少秒
     *
     * @param str1 时间参数 1 格式：1990-01-01 12:00:00
     * @param str2 时间参数 2 格式：2009-01-01 12:00:00
     * @return String 返回值为：xx天xx小时xx分xx秒
     */
    public static String getDistanceTime(String str1, String str2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date one;
        Date two;
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        try {
            one = df.parse(str1);
            two = df.parse(str2);
            long time1 = one.getTime();
            long time2 = two.getTime();
            long diff;
            if (time1 < time2) {
                diff = time2 - time1;
            } else {
                diff = time1 - time2;
            }
            day = diff / (24 * 60 * 60 * 1000);
            hour = (diff / (60 * 60 * 1000) - day * 24);
            min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
            sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return day + "天" + hour + "小时" + min + "分" + sec + "秒";
    }

    /**
     * 计算时间差 分钟
     *
     * @param start
     * @param end
     * @return
     */
    public static Long getDistanceMinute(String start, String end) throws ParseException {

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date one = format.parse(start);
        Date two = format.parse(end);

        Long time1 = one.getTime();
        Long time2 = two.getTime();

        return (time2 - time1) / 1000 / 60;
    }

    public static String date2Str(Date target, String pattern) {
        if (null == target) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String targetStr = "";
        try {
            targetStr = sdf.format(target);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return targetStr;
    }

    /**
     * 获取当天日期，返回值的时分秒均为0
     *
     * @return
     */
    public static Date getCurDate() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);

        return cal.getTime();
    }

    public static String getCurrentTime() {
        String returnStr = null;
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        returnStr = f.format(date);
        return returnStr;
    }

    /**
     * 计算活动距离结束剩余的天数
     *
     * @param now   当前系统时间
     * @param begin 活动开始时间
     * @param end   活动结束时间
     * @return
     * @throws ParseException
     */
    public static int activeEndDays(Date now, Date begin, Date end) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();

        //第一步先给这个结束时间的日加1天----因为数据中存的时间默认为00:00:00结束，所以加1天，这样可以修正正常结束日期的晚上24点才结束
        cal.setTime(sdf.parse(sdf.format(end)));
        cal.add(Calendar.DAY_OF_MONTH, 1);
        end = cal.getTime();

        int enddays = 0;
        if (now.before(begin)) {
            log.info("活动还未开始");
            enddays = -1;//活动还未开始返回-1
        } else if (end.before(now)) {
            log.info("活动已结束");
            enddays = 0;//活动已结束返回0
        } else if (begin.before(now) && now.before(end)) {
            try {
                log.info("计算活动剩余结束天数");
                Date a = sdf.parse(sdf.format(now));
                Date b = sdf.parse(sdf.format(end));
                cal.setTime(a);
                long time1 = cal.getTimeInMillis();
                cal.setTime(b);
                long time2 = cal.getTimeInMillis();
                long between_days = (time2 - time1) / (1000 * 3600 * 24);
                enddays = Integer.parseInt(String.valueOf(between_days));
            } catch (Exception e) {
                log.error("计算活动剩余结束天数失败");
                e.printStackTrace();
            }
        }
        return enddays;
    }


    public static void main(String[] args) {

        /*String start = "2016-06-16 13:55:40";
        String end = "2016-06-17 13:55:40";
        long hours = getDistanceHours(start,end);
        System.out.println(hours);*/
        System.out.println(getCurrentTime());
    }
}
