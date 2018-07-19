package com.summerzhou.storm.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期处理工具类
 */
public class DataUtils {
    /**
     * 返回当前日期 yyyy-MM-dd
     * @return
     */
    public static String getYMDDate(){
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = simpleDateFormat.format(date);
        return format;
    }

    /**
     * 返回当前日期 yyyy-MM-dd hh:mm:ss
     * @return
     */
    public static String getWholeDate(){
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = simpleDateFormat.format(date);
        return format;
    }

    public static void main(String[] args) {
        System.out.println(DataUtils.getYMDDate());
        System.out.println(DataUtils.getWholeDate());
        System.out.println(DataUtils.getTime());
    }

    /**
     * 返回当前时间 HH:mm:ss
     * @return
     */
    public static String getTime() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        String format = simpleDateFormat.format(date);
        return format;
    }
}
