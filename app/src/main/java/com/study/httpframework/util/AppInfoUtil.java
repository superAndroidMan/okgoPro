package com.study.httpframework.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by superman on 2017/10/20.
 */

public class AppInfoUtil {

    /**
     * 获取当前手机系统语言。
     *
     * @return 返回当前系统语言。例如：当前设置的是“中文-中国”，则返回“zh-CN”
     */
    public static String getSystemLanguage() {
        return Locale.getDefault().getLanguage();
    }

    /**
     * 获取当前系统上的语言列表(Locale列表)
     *
     * @return 语言列表
     */
    public static Locale[] getSystemLanguageList() {
        return Locale.getAvailableLocales();
    }

    /**
     * 获取当前手机系统版本号
     *
     * @return 系统版本号
     */
    public static String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取手机型号
     *
     * @return 手机型号
     */
    public static String getSystemModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取手机厂商
     *
     * @return 手机厂商
     */
    public static String getDeviceBrand() {
        return android.os.Build.BRAND;
    }

//    /**
//     * 获取手机IMEI(需要“android.permission.READ_PHONE_STATE”权限)
//     *
//     * @return  手机IMEI
//     */
//    public static String getIMEI(Context ctx) {
//        TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Activity.TELEPHONY_SERVICE);
//        if (tm != null) {
//            return tm.getDeviceId();
//        }
//        return null;
//    }

    /**
     * 获取版本信息
     *
     * @return
     */
    public static String[] getVersionInfo(Context context) {
        String[] version = new String[2];

        PackageManager packageManager = context.getPackageManager();

        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            version[0] = String.valueOf(packageInfo.versionCode);
            version[1] = packageInfo.versionName;
            return version;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return version;
    }

    /**
     * 获取app当前的渠道号或application中指定的meta-data
     *
     * @return 如果没有获取成功(没有对应值 ， 或者异常)，则返回值为空
     */
    public static String getAppMetaData(Context context, String key) {
        if (context == null || TextUtils.isEmpty(key)) {
            return null;
        }
        String channelNumber = null;
        try {
            PackageManager packageManager = context.getPackageManager();
            if (packageManager != null) {
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {
                        channelNumber = applicationInfo.metaData.getString(key);
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return channelNumber;
    }


    /**
     * 得到签名过的时间戳
     *
     * @return
     */
    public static String getCurrentTimeSignStr() {
        String nowDate = getNowDate();
        String sign = "ynby-" + nowDate + "-hzxcx";

        return md5(sign);
    }

    /**
     * 得到当前时间 格式为 2018-01-01 10：10：10
     *
     * @return
     */
    public static String getNowDate() {
        String temp_str = "";
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        temp_str = sdf.format(dt);
        return temp_str;
    }

    public static String timeStamp2Date(String seconds, String format) {
        if(seconds == null || seconds.isEmpty() || seconds.equals("null")){
            return "";
        }
        if(format == null || format.isEmpty()){
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.valueOf(seconds+"000")));
    }
    public static String timeStampDate(String millSeconds, String format) {
        if(millSeconds == null || millSeconds.isEmpty() || millSeconds.equals("null")){
            return "";
        }
        if(format == null || format.isEmpty()){
            format = "yyyy-MM-dd  HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.valueOf(millSeconds)));
    }
    public static String timeStampDate2(String millSeconds, String format) {
        if(millSeconds == null || millSeconds.isEmpty() || millSeconds.equals("null")){
            return "";
        }
        if(format == null || format.isEmpty()){
            format = "yyyy年MM月dd日  HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.valueOf(millSeconds)));
    }

    public static String getDateTimeStr(Date date) {
        String temp_str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        temp_str = sdf.format(date);
        return temp_str;
    }

    public static String getMonthAndDayDateStr(String date) {
        String temp_str = "";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date parseDate = dateFormat.parse(date);
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
            temp_str = sdf.format(parseDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return temp_str;
    }

    /**
     * 得到当前时间 格式为 2018-01-01
     *
     * @return
     */
    public static String getNowDateNoMinute() {
        String temp_str = "";
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        temp_str = sdf.format(dt);
        return temp_str;
    }

    public static String geCurenttTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    public static String geSelectTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        return format.format(date);
    }

    public static String geCurenttTime2(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(date);
    }

    public static String geCurenttTime3(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("MM月dd日 HH:mm");
        return format.format(date);
    }

    /**
     * MD5加密
     *
     * @param string
     * @return
     */
    public static String md5(String string) {
        if (TextUtils.isEmpty(string)) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(string.getBytes());
            StringBuilder result = new StringBuilder();
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result.append(temp);
            }
            return result.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getweekDayByNum(String weekday) {
        switch (Integer.parseInt(weekday)) {
            case 1:
                return "星期一";
            case 2:
                return "星期二";
            case 3:
                return "星期三";
            case 4:
                return "星期四";
            case 5:
                return "星期五";
            case 6:
                return "星期六";
            case 0:
                return "星期日";
            default:
                return "";
        }
    }

    public static String getweekDayByNum2(String weekday) {
        try {

            switch (Integer.parseInt(weekday)) {
                case 1:
                    return "周一";
                case 2:
                    return "周二";
                case 3:
                    return "周三";
                case 4:
                    return "周四";
                case 5:
                    return "周五";
                case 6:
                    return "周六";
                case 0:
                    return "周日";
                default:
                    return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getTimeRange(String timeRange) {
        switch (timeRange) {
            case "0":
                return "上午";
            case "1":
                return "下午";
            case "2":
                return "晚上";
            default:
                return "";
        }
    }

    public static String getFormatdate(String dateStr) {
        if (TextUtils.isEmpty(dateStr)) return "";
        try {
            String[] split = dateStr.split("\\.");
            return split[0]+"-"+split[1]+"-"+split[2];
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }
    public static String getFormatdate2(String dateStr) {
        if (TextUtils.isEmpty(dateStr)) return "";
        try {
            String[] split = dateStr.split("-");
            return split[0]+"."+split[1]+"."+split[2];
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取格式：2018.09.20 周一  上午 的字符串
     * @param date
     * @param weekday
     * @param timeRange
     * @return
     */
    public static String getTimeRangeFormatDateStr(String date, String weekday, String timeRange){
        String dateStr = getFormatdate2(date);
        String weekdayStr = getweekDayByNum2(weekday);
        String timeRangeStr = getTimeRange(timeRange);
        return dateStr+"   "+weekdayStr+"   "+timeRangeStr;
    }
    /**
     * 计算2个日期之间相差的  相差多少年
     * 比如：2011-02-02 到  2017-03-02 相差 6年
     *
     * @param fromDate
     * @param toDate
     * @return
     */
    public static int dayComparePrecise(Date fromDate, Date toDate) {
        if (fromDate == null || toDate == null) {
            return 0;
        }
        Calendar from = Calendar.getInstance();
        from.setTime(fromDate);
        Calendar to = Calendar.getInstance();
        to.setTime(toDate);

        int fromYear = from.get(Calendar.YEAR);
        int toYear = to.get(Calendar.YEAR);
        int year = toYear - fromYear;
        return year;
    }

    /**
     * 倒计时formate
     *
     * @param second
     * @return 返回 0:0:0
     */
    public static String getDownTimeFormat(int second) {
        if (second < 10) {
            return "00:0" + second;
        }
        if (second < 60) {
            return "00:" + second;
        }
        if (second < 3600) {
            int minute = second / 60;
            second = second - minute * 60;
            if (minute < 10) {
                if (second < 10) {
                    return "0" + minute + ":0" + second;
                }
                return "0" + minute + ":" + second;
            }
            if (second < 10) {
                return minute + ":0" + second;
            }
            return minute + ":" + second;
        }
        int hour = second / 3600;
        int minute = (second - hour * 3600) / 60;
        second = second - hour * 3600 - minute * 60;
        if (hour < 10) {
            if (minute < 10) {
                if (second < 10) {
                    return "0" + hour + ":0" + minute + ":0" + second;
                }
                return "0" + hour + ":0" + minute + ":" + second;
            }
            if (second < 10) {
                return "0" + hour + minute + ":0" + second;
            }
            return "0" + hour + minute + ":" + second;
        }
        if (minute < 10) {
            if (second < 10) {
                return hour + ":0" + minute + ":0" + second;
            }
            return hour + ":0" + minute + ":" + second;
        }
        if (second < 10) {
            return hour + minute + ":0" + second;
        }
        return hour + minute + ":" + second;

    }


    public static String getDownTimeFormatChinese(int second) {

        if (second < 10) {
            return "00分0" + second + "秒";
        }
        if (second < 60) {
            return "00分" + second + "秒";
        }

        if (second < 3600) {
            int minute = second / 60;
            second = second - minute * 60;
            if (minute < 10) {
                if (second < 10) {
                    return "0" + minute + "分0" + second + "秒";
                }
                return "0" + minute + "分" + second + "秒";
            }
            if (second < 10) {
                return minute + "分0" + second + "秒";
            }
            return minute + "分" + second + "秒";
        }

        return "";

    }

    /**
     * 根据日期获取 星期 （2019-05-06 ——> 星期一）
     *
     * @param datetime
     * @return
     */
    public static String dateToWeek(String datetime) {

        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        Date date;
        try {
            date = f.parse(datetime);
            cal.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //一周的第几天
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    public static SpannableString getSpanString(String textString, String content, String color) {
        SpannableString spannableString = new SpannableString(textString);
        ForegroundColorSpan backgroundColorSpan =
                new ForegroundColorSpan(Color.parseColor(color));
        spannableString.setSpan(backgroundColorSpan,
                content.length(), textString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        return spannableString;
    }

    public static boolean isSameDay(Date date1, Date date2) {
        if (date1 != null && date2 != null) {
            Calendar cal1 = Calendar.getInstance();
            cal1.setTime(date1);
            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(date2);
            return isSameDay(cal1, cal2);
        } else {
            throw new IllegalArgumentException("The date must not be null");
        }
    }

    @SuppressLint("WrongConstant")
    public static boolean isSameDay(Calendar cal1, Calendar cal2) {
        if (cal1 != null && cal2 != null) {
            return cal1.get(0) == cal2.get(0) && cal1.get(1) == cal2.get(1) && cal1.get(6) == cal2.get(6);
        } else {
            throw new IllegalArgumentException("The date must not be null");
        }
    }

}
