package com.jdy.base.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * Created by Dale on 2017/9/25.
 * 项目名：xxx
 * 描述：时间格式转换器
 * 1.将sql date 转换成 util date
 * 2.将 util date 转换成 sql date
 * 3.将 sql date 转换成 String
 * 4.将 util date 转换成 String
 */

public class DateUtil {
    private static String defaultFormat = "yyyy-MM-dd";
    private static SimpleDateFormat dateFormat = new SimpleDateFormat(defaultFormat, Locale.CHINA);

    /**
     * 将sql date 转换成 util date
     *
     * @param date sql date
     * @return util date
     */
    public static Date transfrom(java.sql.Date date) {
        return new Date(date.getTime());
    }

    /**
     * 将 util date 转换成 sql date
     *
     * @param date util date
     * @return sql date
     */
    public static java.sql.Date transfrom(Date date) {
        return new java.sql.Date(date.getTime());
    }

    /**
     * 将sql date 转换成 String
     *
     * @param date sql date
     * @return String
     */
    public static String dateFotmat(java.sql.Date date) {
        return dateFotmat(transfrom(date));
    }

    /**
     * 将 util date 转换成 String
     *
     * @param date util date
     * @return String
     */
    public static String dateFotmat(Date date) {
        // 设置了默认格式
        return dateFotmat("yyyy-MM-dd", date);
    }

    /**
     * 将 util date 按照format转换成 String
     *
     * @param format
     * @param date
     * @return
     */
    public static String dateFotmat(String format, Date date) {
        if (format == null) {
            return "unknown";
        }
        dateFormat.applyPattern(getFormat(format));
        return dateFormat.format(date);
    }

    public static String dateFormat(Object value) {
        if (value instanceof Date) {
            return dateFormat.format(value);
        }
        return "unknown";
    }

    /**
     * 将string 转换为util Date
     *
     * @param datesString
     * @return
     */
    public static Date convert(String datesString) {
        try {
            dateFormat.applyPattern(getFormat(datesString));
            return dateFormat.parse(datesString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param format
     * @return
     */
    private static String getFormat(String format) {
        String year = "yyyy", month = "MM", day = "dd", separator;
        separator = format.substring(4, 5);
        if ("年".equals(separator)) {
            defaultFormat = year + "年" + month + "月" + day + "日";
            getMoreAccuracy(format, true, 1);
        } else {
            defaultFormat = year + separator + month + separator + day;
            getMoreAccuracy(format, false, 1);
        }
        return defaultFormat;
    }

    private static void getMoreAccuracy(String format, boolean isChinese, int more) {
        String hour = "HH", minute = "mm", second = "ss";
        if (defaultFormat.compareTo(format) < 0) {
            switch (more) {
                case 1:
                    defaultFormat += " " + hour + (isChinese ? "时" : "");
                    getMoreAccuracy(format, isChinese, 2);
                    break;
                case 2:
                    defaultFormat += (isChinese ? "" : ":") + minute + (isChinese ? "分" : "");
                    getMoreAccuracy(format, isChinese, 3);
                    break;
                case 3:
                    defaultFormat += (isChinese ? "" : ":") + second + (isChinese ? "秒" : ":");
                    break;
            }
        }
    }


    /**
     * 将一种格式转换为另一种格式
     *
     * @param timestamp
     * @return
     */
    public static String dateFormat(String timestamp) {
        if (timestamp == null) {
            return "unknown";
        }
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SS");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = inputFormat.parse(timestamp);
            return outputFormat.format(date);
        } catch (ParseException e) {
            return "unknown";
        }
    }

    /**
     * 验证字符串是否是一个合法的日期格式
     */
    public static boolean isValidDate(String format, String... dates) {
        boolean convertSuccess = true;
        if (dates == null || dates.length == 0) {
            return false;
        }

        for (String date : dates) {
            if (!isValidDate(date, format)) {
                convertSuccess = false;
                break;
            }
        }
        return convertSuccess;
    }


    /**
     * 验证字符串是否是一个合法的日期格式
     */
    public static boolean isValidDate(String date, String format) {
        boolean convertSuccess = true;
        // 指定日期格式
        dateFormat = new SimpleDateFormat(getFormat(format), Locale.CHINA);
        try {
            // 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2015/02/29会被接受，并转换成2015/03/01
            dateFormat.setLenient(false);
            dateFormat.parse(date);
        } catch (Exception e) {
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            convertSuccess = false;
        }
        return convertSuccess;
    }
}
