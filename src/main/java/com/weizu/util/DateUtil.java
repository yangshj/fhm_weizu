package com.weizu.util;

import java.util.Calendar;
import java.util.Date;

/**
 * Description:日期时间类
 *
 * @since : 2019/3/26 14:39:22
 **/
public class DateUtil {

    /**
     * 日期相加减
     * @param time 时间字符串 yyyy-MM-dd HH:mm:ss
     * @param num 加的数，-num就是减去
     */
    public static Date yearAddNum(Date time, Integer num) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        calendar.add(Calendar.YEAR, num);
        Date newTime = calendar.getTime();
        return newTime;
    }

    /**
     *
     * @param time 时间
     * @param num 加的数，-num就是减去
     * @return
     */
    public static Date monthAddNum(Date time, Integer num){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        calendar.add(Calendar.MONTH, num);
        Date newTime = calendar.getTime();
        return newTime;
    }

    /**
     *
     * @param time 时间
     * @param num 加的数，-num就是减去
     */
    public static Date dayAddNum(Date time, Integer num){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        calendar.add(Calendar.DAY_OF_MONTH, num);
        Date newTime = calendar.getTime();
        return newTime;
    }

}
