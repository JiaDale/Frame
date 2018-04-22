package com.jdy.base.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

public class DateUtil {
    private static String format = "yyyy-MM-dd";
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.CHINA);

    public static Date convert(java.sql.Date date) {
        return new Date(date.getTime());
    }

    public static java.sql.Date convert(Date date) {
        return new java.sql.Date(date.getTime());
    }

    public static String dateFotmat(java.sql.Date date) {
        return dateFormat(convert(date));
    }

    public static String dateFormat(Date date) {
        return dateFormat(format, date);
    }

    public static String dateFormat(String format, Date date) {
        if (format == null) {
            throw new NullPointerException("The format string is Null!");
        }
        simpleDateFormat.applyPattern(getFormat(format));
        return simpleDateFormat.format(date);
    }

    public static String dateFormat(Object value) {
        if (value instanceof Date) {
            return simpleDateFormat.format(value);
        }
        return "unknown";
    }

    public static Date convert(String dateString) {
        try {
            simpleDateFormat.applyPattern(getFormat(dateString));
            return simpleDateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean isContainsChinese(String chinese) {
        return Pattern.compile("[\u4e00-\u9fa5]").matcher(chinese).find();
    }


    private static String getFormat(String format) {
        if (format == null) {
            throw new NullPointerException("The format string is Null");
        }

        if (isContainsChinese(format))
            setChineseFormat(format);
        else {
            getOtherFormat(format);
        }
        return DateUtil.format;
    }

    private static void getOtherFormat(String format) {
        String separator;
        StringBuffer tempFormat = new StringBuffer();
        if (format.length() > 4) {
            separator = format.substring(4, 5);
            tempFormat.append("yyyy");
        } else {
            for (int i = 0; i < format.length(); i++) {
                tempFormat.append("y");
            }
            DateUtil.format = tempFormat.toString();
            return;
        }

        if (tempFormat.length() < format.length()) {
            tempFormat.append(separator + "MM");
        }

        if (tempFormat.length() < format.length()) {
            tempFormat.append(separator + "dd");
        }

        if (tempFormat.length() < format.length()) {
            tempFormat.append(" HH");
        }

        if (tempFormat.length() < format.length()) {
            tempFormat.append(" :mm");
        }

        if (tempFormat.length() < format.length()) {
            tempFormat.append(" :ss");
        }
        DateUtil.format = tempFormat.toString();
    }

    private static void setChineseFormat(String format) {
        if (format.contains("年"))
            DateUtil.format = "yyyy年";

        if (format.contains("月"))
            DateUtil.format += "MM月";

        if (format.contains("日") || format.contains("号"))
            DateUtil.format += "dd日 ";

        if (format.contains("时"))
            DateUtil.format += "dd时 ";

        if (format.contains("分"))
            DateUtil.format += "dd分 ";

        if (format.contains("秒"))
            DateUtil.format += "dd秒 ";
    }

    public static String dateFormat(String current, String target) {
        if (current == null || target == null) {
            throw new NullPointerException("The current format or target format is Null!");
        }

        return dateFormat(target, convert(current));
    }


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


    public static boolean isValidDate(String date, String format) {
        boolean convertSuccess = true;
        simpleDateFormat = new SimpleDateFormat(getFormat(format), Locale.CHINA);
        try {
            simpleDateFormat.setLenient(false);
            simpleDateFormat.parse(date);
        } catch (Exception e) {
            convertSuccess = false;
        }
        return convertSuccess;
    }
}
