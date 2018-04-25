package com.jdy.base.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtil {
    private static String formatString = "yyyy-MM-dd";
    private static DateFormat dateFormat = DateFormat.getInstance();

    public static Date convert(java.sql.Date date) {
        return new Date(date.getTime());
    }

    public static java.sql.Date convert(Date date) {
        return new java.sql.Date(date.getTime());
    }

    public static String dateFotmat(java.sql.Date date) {
        return dateFormat(date);
    }

    public static String dateFormat(Date date) {
        return dateFormat(formatString, date);
    }

    public static String dateFormat(String format, Date date) {
        return dateFormat.dateFormat(format, date);
    }

    public static String dateFormat(Object value) {
        return dateFormat.dateFormat(value);
    }

    public static Date convert(String dateString) {
        return dateFormat.convert(dateString);
    }

    public static String dateFormat(String current, String target) {
        return dateFormat(target, convert(current));
    }

    public static int compare(Date current, Date target) {
        return compare(current, target, formatString);
    }

    public static int compare(Date current, Date target, String format) {
//        return convert(dateFormat(format, current)).compareTo(convert(dateFormat(format, target)));
        return dateFormat(format, current).compareTo(dateFormat(format, target));
    }

    public static boolean isValidDate(String format, String... dates) {
        return dateFormat.isValidDate(format, dates);
    }

    static class DateFormat {
        private SimpleDateFormat simpleDateFormat;

        DateFormat() {
            simpleDateFormat = new SimpleDateFormat(formatString, Locale.CHINA);
        }

        public static DateFormat getInstance() {
            return new DateFormat();
        }

        Date convert(String dateString) {
            try {
                simpleDateFormat.applyPattern(getFormat(dateString));
                return simpleDateFormat.parse(dateString);
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
        }

        String dateFormat(String current, String target) {
            if (current == null || target == null) {
                throw new NullPointerException("The current format or target format is Null!");
            }
            return dateFormat(target, convert(current));
        }

        String dateFormat(String format, Date date) {
            if (format == null) {
                throw new NullPointerException("The format string is Null!");
            }
            simpleDateFormat.applyPattern(formatString = getFormat(format));
            return simpleDateFormat.format(date);
        }

        String dateFormat(Object value) {
            if (value instanceof Date) {
                return simpleDateFormat.format(value);
            }
            return "unknown";
        }

        String getFormat(String format) {
            if (format == null) {
                throw new NullPointerException("The format string is Null");
            }
            char[] charArray = format.toCharArray();
            char[] array = { 'y', 'M', 'd', 'H', 'm', 's' };
            int count = 0;
            StringBuffer buffer = new StringBuffer();
            for (int i = 0; i < charArray.length; i++) {
                char c = charArray[i];
                if (c >= '0' && c <= '9') {
                    buffer.append(array[count]);
                } else {
                    buffer.append(c);
                    count++;
                }
            }
            return buffer.toString();
        }

        boolean isValidDate(String format, String... dates) {
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

        boolean isValidDate(String date, String format) {
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
}
