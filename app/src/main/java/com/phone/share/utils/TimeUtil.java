package com.phone.share.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * TimeUtil
 *
 * @author WangZhenKai
 * @since 2021/1/4
 */
public class TimeUtil {
    private static ThreadLocal<SimpleDateFormat> dateLocal = new ThreadLocal<>();
    private static final String EN_FORMAT_DATE_TIME = "yyyy-MM-dd HH:mm:ss";

    /**
     * 时间戳转换日期
     *
     * @param stamp 参数
     * @return 返回转换的日期时间
     */
    public static String stampToTime(long stamp) {
        String sd = getDateFormat(EN_FORMAT_DATE_TIME).format(new Date(stamp));
        return sd;
    }

    private static SimpleDateFormat getDateFormat(String format) {
        if (dateLocal.get() == null) {
            dateLocal.set(new SimpleDateFormat(format, Locale.CHINA));
        }
        return dateLocal.get();
    }
}
