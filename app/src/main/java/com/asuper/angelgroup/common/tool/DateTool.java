package com.asuper.angelgroup.common.tool;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * @author wangfei
 * @date 2015年10月15日
 */
public class DateTool {

    public static final String DATE_TIME_FORMAT_PATTERN_1 = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_TIME_FORMAT_PATTERN_2 = "yyyyMMddHHmmss";
    public static final String DATE_TIME_FORMAT_PATTERN_3 = "yyyyMMdd";
    public static final String DATE_TIME_FORMAT_PATTERN_4 = "yyyy-MM-dd";
    public static final String DATE_TIME_FORMAT_PATTERN_5 = "HH:mm:ss";
    public static final String DATE_TIME_FORMAT_PATTERN_6 = "HH:mm";

    /**
     * 把时间戳转成时间字符串
     */
    public static String parseTime(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        return sdf.format(new Date(timestamp));
    }

    /**
     * 把时间戳转成时间字符串
     */
    public static String L2S(String timestamp) {
        if (timestamp == null || timestamp.equals(""))
            return "";
        Long tsp = Long.parseLong(timestamp);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT_PATTERN_1);
        return sdf.format(new Date(tsp));
    }

    /**
     * 把时间戳转成时间字符串
     */
    public static String L2SEndDay(String timestamp) {
        if (timestamp == null || timestamp.equals(""))
            return "";
        Long tsp = Long.parseLong(timestamp);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT_PATTERN_4);
        return sdf.format(new Date(tsp));
    }

    /**
     * 把时间戳转成时间字符串
     */
    public static String L2SEndDay2(String timestamp) {
        if (timestamp == null || timestamp.equals(""))
            return "";
        Long tsp = Long.parseLong(timestamp);
        SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
        return sdf.format(new Date(tsp));
    }

    /**
     * 把时间戳转成时间字符串
     */
    public static String L6SEndDay(String timestamp) {
        Long tsp = Long.parseLong(timestamp);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT_PATTERN_6);
        return sdf.format(new Date(tsp));
    }

    /**
     * 把时间戳转成时间字符串
     */
    public static String L2SByDay(String timestamp) {
        if (timestamp == null || "".equals(timestamp))
            return "0.0";
        Long tsp = Long.parseLong(timestamp);
        Date date = new Date(tsp);
        int m = date.getMonth() + 1;
        int day = date.getDate();
        return m + "." + day;
    }

    /**
     * 把时间戳转成时间字符串
     */
    public static String L2SByDay2(String timestamp) {
        Long tsp = 0l;
        try {
            tsp = Long.parseLong(timestamp);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        Date date = new Date(tsp);
        int m = date.getMonth() + 1;
        int day = date.getDate();
        int hour = date.getHours();
        int min = date.getMinutes();
        return m + "月" + day + "日" + " " + (hour < 10 ? "0" + hour : hour)
                + ":" + (min < 10 ? "0" + min : min);
    }

    /**
     * 把时间戳转成时间字符串
     */
    public static String L2SByDay3(String timestamp) {
        Long tsp = 0l;
        try {
            tsp = Long.parseLong(timestamp);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        Date date = new Date(tsp);
        int m = date.getMonth() + 1;
        int day = date.getDate();
        int hour = date.getHours();
        int min = date.getMinutes();
        return m + "月" + day + "日";
    }

    /**
     * 把时间戳转成月份
     */
    public static Integer getMonth(String timestamp) {
        Long tsp = Long.parseLong(timestamp);
        Date date = new Date(tsp);
        int m = date.getMonth() + 1;
        return m;
    }

    public static Date addHour(Date date, int addHours) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, addHours);
        Date nowDate = calendar.getTime();

        return nowDate;
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int daysBetween(Date smdate, Date bdate)
            throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        smdate = sdf.parse(sdf.format(smdate));
        bdate = sdf.parse(sdf.format(bdate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 使用指定的格式返回当前系统时间
     *
     * @param s 时间格式
     * @return
     */
    public static String getSystemDateTime(String s) {
        try {
            SimpleDateFormat simpledateformat = new SimpleDateFormat(s);
            return simpledateformat.format(new Date()).toString();
        } catch (Exception exception) {
        }
        return null;
    }

    /**
     * 按给定参数返回Date对象
     *
     * @param DateTime 时间对象格式为("yyyy-MM-dd HH:mm:ss");
     * @return
     */
    public static Date getDate(String DateTime) {
        SimpleDateFormat simpledateformat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        return simpledateformat.parse(DateTime, new ParsePosition(0));
    }


    public static String getFormattedDate(Date someDate, String format) {
        if (someDate == null) {
            return "";
        }
        SimpleDateFormat simpledateformat = new SimpleDateFormat(format);
        return simpledateformat.format(someDate).toString();
    }

    public static Date addMonth(Date date, int addMonths) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, addMonths);
        Date nowDate = calendar.getTime();

        return nowDate;
    }

    public static Date addDay(Date date, int addDays) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, addDays);
        Date nowDate = calendar.getTime();

        return nowDate;
    }

    /**
     * 获得2时间的间隔时间 (单位ms)
     *
     * @param time1
     * @param time2
     * @return
     */
    public static long getIntervalTime(Date time1, Date time2) {
        return time1.getTime() - time2.getTime();
    }

    public static int getWkForYear(Date date) {
        // SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        // Date date = sdf.parse(dt);
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.setTime(date);
        return cal.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * 以指定格式获取当前时间的前一天
     */
    public static String getThedayBefore(String format) {
        Calendar c = Calendar.getInstance(Locale.CHINA);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH) - 1;
        SimpleDateFormat formats = new SimpleDateFormat(format, Locale.CHINA);
        c.set(year, month, day);

        return formats.format(c.getTime());
    }

    public static int getWkForQuarterly(Date date) {
        // SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        // Date date = sdf.parse(dt);
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.setTime(date);
        int weekNumQ = 0;
        int mo = cal.get(Calendar.MONTH);
        int month = mo + 1;
        if (month == 1 || month == 2 || month == 3) {
            if (month == 1) {
                weekNumQ = cal.get(Calendar.WEEK_OF_MONTH);
            }
            if (month == 2) {
                weekNumQ = cal.get(Calendar.WEEK_OF_MONTH) + 5;
            }
            if (month == 3) {
                weekNumQ = cal.get(Calendar.WEEK_OF_MONTH) + 9;
            }
        }
        if (month == 4 || month == 5 || month == 6) {
            if (month == 4) {
                weekNumQ = cal.get(Calendar.WEEK_OF_MONTH);
            }
            if (month == 5) {
                weekNumQ = cal.get(Calendar.WEEK_OF_MONTH) + 5;
            }
            if (month == 6) {
                weekNumQ = cal.get(Calendar.WEEK_OF_MONTH) + 9;
            }
        }
        if (month == 7 || month == 8 || month == 9) {
            if (month == 7) {
                weekNumQ = cal.get(Calendar.WEEK_OF_MONTH);
            }
            if (month == 8) {
                weekNumQ = cal.get(Calendar.WEEK_OF_MONTH) + 5;
            }
            if (month == 9) {
                weekNumQ = cal.get(Calendar.WEEK_OF_MONTH) + 9;
            }
        }
        if (month == 10 || month == 11 || month == 12) {
            if (month == 10) {
                weekNumQ = cal.get(Calendar.WEEK_OF_MONTH);
            }
            if (month == 11) {
                weekNumQ = cal.get(Calendar.WEEK_OF_MONTH) + 5;
            }
            if (month == 12) {
                weekNumQ = cal.get(Calendar.WEEK_OF_MONTH) + 9;
            }
        }
        return weekNumQ;
    }

    /**
     * 计算两日期相隔天数
     */
    public static int dateDiff(long d1, long d2) {
        long diff = Math.abs(d1 - d2);

        diff /= 3600 * 1000 * 24;
        return (int) diff;
    }

    public static String getDateStr(String dateTime, String format) {
        SimpleDateFormat simpledateformat = new SimpleDateFormat(format);
        return simpledateformat.format(simpledateformat.parse(dateTime,
                new ParsePosition(0)));
    }

    /**
     * 格式化时间
     *
     * @param gapTime
     * @return
     */
    public static String formatTimeToString(int gapTime) {

        Calendar time = Calendar.getInstance();
        time.add(Calendar.HOUR_OF_DAY, gapTime);
        SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return d.format(time.getTime());
    }

    /**
     * 按给定参数返回String类型指定形式 时间对象格式为Date
     *
     * @return String
     * @author zhufengli
     * @time 2012-9-28
     */
    public static String getDateTime(Date dateTime, String format) {
        if (dateTime != null) {
            SimpleDateFormat simpledateformat = new SimpleDateFormat(format);
            return simpledateformat.format(dateTime);
        } else {
            return "";
        }
    }

    public static String getDateTime(Date dateTime, String yearLength,
                                     String type) {
        if (dateTime != null) {

            if ("1".equals(type)) { // 日编

                if ("2".equals(yearLength)) {
                    SimpleDateFormat simpledateformat = new SimpleDateFormat(
                            "yyMMdd");
                    return simpledateformat.format(dateTime);
                } else if ("4".equals(yearLength)) {
                    SimpleDateFormat simpledateformat = new SimpleDateFormat(
                            "yyyyMMdd");
                    return simpledateformat.format(dateTime);
                }
            } else if ("2".equals(type)) { // 月编
                if ("2".equals(yearLength)) {
                    SimpleDateFormat simpledateformat = new SimpleDateFormat(
                            "yyMM");
                    return simpledateformat.format(dateTime);
                } else if ("4".equals(yearLength)) {
                    SimpleDateFormat simpledateformat = new SimpleDateFormat(
                            "yyyyMM");
                    return simpledateformat.format(dateTime);
                }
            } else if ("3".equals(type)) { // 季编

                Calendar c = Calendar.getInstance();
                int m = c.get(Calendar.MONTH) + 1; // 月份
                int s = getSeasonByMonth(m);
                if ("2".equals(yearLength)) {
                    SimpleDateFormat simpledateformat = new SimpleDateFormat(
                            "yy-" + s);
                    return simpledateformat.format(dateTime);
                } else if ("4".equals(yearLength)) {
                    SimpleDateFormat simpledateformat = new SimpleDateFormat(
                            "yyyy-" + s);
                    return simpledateformat.format(dateTime);
                }
            } else if ("4".equals(type)) { // 年编
                if ("2".equals(yearLength)) {
                    SimpleDateFormat simpledateformat = new SimpleDateFormat(
                            "yy");
                    return simpledateformat.format(dateTime);
                } else if ("4".equals(yearLength)) {
                    SimpleDateFormat simpledateformat = new SimpleDateFormat(
                            "yyyy");
                    return simpledateformat.format(dateTime);
                }
            } else if ("5".equals(type)) {// 流水号
                return "";
            }
        } else {
            return null;
        }
        return null;
    }

    private static int getSeasonByMonth(int month) {
        if (month > 0 && month < 4) {
            return 1;
        } else if (month > 3 && month < 7) {
            return 2;
        } else if (month > 6 && month < 10) {
            return 3;
        } else if (month > 10 && month < 13) {
            return 4;
        }
        return 0;
    }

    /**
     * 按给定参数返回Date对象 时间对象格式为指定形式
     *
     * @return Date
     * @author zhufengli
     * @time 2012-9-28
     */
    public static Date getDate(String dateTime, String format) {
        SimpleDateFormat simpledateformat = new SimpleDateFormat(format);
        return simpledateformat.parse(dateTime, new ParsePosition(0));
    }

    /**
     * @param dateTime
     * @param format
     * @return
     * @author shenxiaozhong
     */
    public static String getDateTimeStr(Date dateTime, String format) {
        SimpleDateFormat simpledateformat = new SimpleDateFormat(format);
        if (null == dateTime) {
            return "";
        } else {
            return simpledateformat.format(dateTime);
        }
    }

    public static String getSysDateFormatyyyyMM() {
        Date dNow = new Date(); // 当前时间
        // Date dBefore = new Date();
        Calendar calendar = Calendar.getInstance(); // 得到日历
        calendar.setTime(dNow);// 把当前时间赋给日历
        calendar.add(Calendar.DAY_OF_MONTH, -1); // 设置为前一天
        Date dBefore = calendar.getTime(); // 得到前一天的时间

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM"); // 设置时间格式
        return sdf.format(dBefore);
    }

    /**
     * @return
     * @Function: com.yihaodian.backend.finance.util.DateTime.getSysDateFirstDate
     * @Description: 指定时间的第一天
     * @version:v1.0.0
     * @author:wanglei3
     * @date:Mar 1, 20134:42:06 PM
     * <p>
     * Modification History: Date Author Version Description
     * ----------
     * ------------------------------------------------------- Mar 1,
     * 2013 wanglei3 v1.0.0
     */
    public static String getSysDateFirstDate(Date date) {

        SimpleDateFormat datef = new SimpleDateFormat("yyyy-MM-dd");

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        // 当前月的第一天
        calendar.set(GregorianCalendar.DAY_OF_MONTH, 1);
        Date beginTime = calendar.getTime();
        String beginTime1 = datef.format(beginTime);
        return beginTime1;
    }

    /**
     * @return
     * @Function: com.yihaodian.backend.finance.util.DateTime.getSysDateLastDate
     * @Description: 指定时间的最后一天
     * @version:v1.0.0
     * @author:wanglei3
     * @date:Mar 1, 20134:40:47 PM
     * <p>
     * Modification History: Date Author Version Description
     * ----------
     * ------------------------------------------------------- Mar 1,
     * 2013 wanglei3 v1.0.0
     */
    public static String getSysDateLastDate(Date date) {

        SimpleDateFormat datef = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, 1);
        calendar.roll(Calendar.DATE, -1);
        Date endTime = calendar.getTime();
        String endTime1 = datef.format(endTime);
        return endTime1;
    }

    public static String getSysDateMonthFormatYyyyMM(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        SimpleDateFormat datef = new SimpleDateFormat("yyyy-MM");
        return datef.format(date);
    }

    public static boolean isSameYyyyMm(Date firstDate, Date sencordDate) {
        SimpleDateFormat datef = new SimpleDateFormat("yyyy-MM");
        String firstDateStr = datef.format(firstDate);
        String sencordDateStr = datef.format(sencordDate);
        if (firstDateStr.equals(sencordDateStr)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断给定时日是否工作日（不是星期天和星期6)
     *
     * @param date
     * @return
     */
    public static boolean isWorkDay(Date date) {
        boolean result = true;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == 1 || dayOfWeek == 7) {
            result = false;
        } else {
            result = true;
        }
        return result;
    }

    /**
     * 获取某个日期的前后任意一天
     *
     * @param date 时间
     * @param day  天数
     * @return
     */
    public static Date getAnyDay(Date date, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, day);
        date = calendar.getTime();
        return date;
    }

    /**
     * 获取两个日期之间的月数
     *
     * @param date1
     * @param date2
     * @return
     * @throws ParseException
     */
    public static int getMonthSpace(Long date1, Long date2) {
        if (date1.equals(date2))// 只有两个值相等才返回0
            return 0;
        int result = 0;
        result = dateDiff(date1, date2) / 30;
        return result == 0 ? 1 : Math.abs(result);
    }

    /**
     * 将字符串转换为时间格式
     *
     * @param date
     * @return
     */
    public static Calendar getCal(String date) {
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(Integer.parseInt(date.substring(0, 4)),
                Integer.parseInt(date.substring(4, 6)) - 1,
                Integer.parseInt(date.substring(6, 8)));
        return cal;
    }

    /**
     * 根据用户生日计算年龄
     *
     * @param birthday
     * @return xx岁xx月
     */
    /**
     * 当前日比较
     *
     * @return
     */
    private static boolean compareTo(Calendar datebegin, Calendar dateend) {
        return datebegin.get(Calendar.DAY_OF_MONTH) > dateend
                .get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 计算两个日期相隔的多少年
     *
     * @param datebegin
     * @param dateend
     * @return
     */
    private static int CalculatorYear(Calendar datebegin, Calendar dateend) {
        int year1 = datebegin.get(Calendar.YEAR);
        int year2 = dateend.get(Calendar.YEAR);
        int month1 = datebegin.get(Calendar.MONTH);
        int month2 = dateend.get(Calendar.MONTH);
        int year = year2 - year1;
        if (compareTo(datebegin, dateend)) // 计算天时向月借了一个月
            month2 -= 1;
        if (month1 > month2)
            year -= 1;
        return year;
    }

    /**
     * 计算两个日期相隔的多少月
     *
     * @param datebegin
     * @param dateend
     * @return
     */
    private static int CalculatorMonth(Calendar datebegin, Calendar dateend) {
        int month1 = datebegin.get(Calendar.MONTH);
        int month2 = dateend.get(Calendar.MONTH);
        int month = 0;
        if (compareTo(datebegin, dateend)) // 计算天时向月借了一个月
            month2 -= 1;
        if (month2 >= month1)
            month = month2 - month1;
        else if (month2 < month1) // 借一整年
            month = 12 + month2 - month1;
        return month;

    }

    /**
     * 计算两个日期相隔的多少天
     *
     * @param datebegin
     * @param dateend
     * @return
     */
    public static int CalculatorDay(Calendar datebegin, Calendar dateend) {

        int day11 = datebegin.get(Calendar.DAY_OF_MONTH);
        int day21 = dateend.get(Calendar.DAY_OF_MONTH);

        if (day21 >= day11) {
            return day21 - day11;
        } else {// 借一整月
            Calendar cal = Calendar.getInstance();
            cal.setTime(dateend.getTime());
            cal.set(Calendar.DAY_OF_MONTH, 1);
            cal.add(dateend.DAY_OF_MONTH, -1);
            return cal.getActualMaximum(Calendar.DATE) + day21 - day11;
        }
    }

    /**
     * 返回两个时间相隔的多少年
     *
     * @param datebegin 开始日期
     * @param dateend   结束日期
     * @return
     */
    public static int getYear(Calendar datebegin, Calendar dateend) {
        return CalculatorYear(datebegin, dateend) > 0 ? CalculatorYear(
                datebegin, dateend) : 0;
    }

    /**
     * 返回除去整数年后的月数
     *
     * @param datebegin 开始日期
     * @param dateend   结束日期
     * @return
     */
    public static int getMonth(Calendar datebegin, Calendar dateend) {
        int month = CalculatorMonth(datebegin, dateend) % 12;
        return (month > 0 ? month : 0);
    }

    /**
     * 返回除去整月后的天数
     *
     * @param datebegin 开始日期
     * @param dateend   结束日期
     * @return
     */
    private static int getDay(Calendar datebegin, Calendar dateend) {
        int day = CalculatorDay(datebegin, dateend);
        return day;
    }

    /**
     * 返回指定日期与当前日期间相差的年月天
     */
    public static String getAgeByBirthday(Date date) {
        return getAgeByBirthday(date, new Date());
    }

    /**
     * 返回两个个日期间相差的年月天
     *
     * @param datebegin 开始日期
     * @param dateend   结束日期
     * @return 以@符号分割的字符串，使用者在程序中自己分割后拼接成自己需要的格式
     */
    public static String getAgeByBirthday(Date datebegin, Date dateend) {
        Calendar cbegin;
        Calendar cend;
        cbegin = Calendar.getInstance();
        cend = Calendar.getInstance();
        cbegin.setTime(datebegin);
        cend.setTime(dateend);
        return getYear(cbegin, cend) + "@" + getMonth(cbegin, cend) + "@"
                + getDay(cbegin, cend);
    }

    /**
     * 返回今天的星期序号
     */
    public static int getToadyIndex() {
        int curDayIndexOfWeekInEn = 0;
        int curDayIndexOfArray = 0;
        Calendar c = Calendar.getInstance();
        curDayIndexOfWeekInEn = c.get(Calendar.DAY_OF_WEEK);// 获致是本周的第几天地,
        curDayIndexOfArray = weekDay2WeekIndex(curDayIndexOfWeekInEn);
        return curDayIndexOfArray;
    }

    /**
     * 将周几转为星期数组的序号：英语日期 1代表星期天...7代表星期六
     */
    public static int weekDay2WeekIndex(int weekday) {
        // -----------周1-周2-周3-周4-周5-周6-周日
        // 我的数组标- -1--0---1---2---3---4---5
        // 英语第几天- 2---3---4---5---6---7---1
        int index = -1;
        if (weekday == Calendar.SUNDAY) { // 如果周日那么下标为5
            index = 5;
        } else {// 否则下标都是星期减3
            index = weekday - 3;
        }
        return index;
    }

    public static long[] getTimesArray() {
        long[] l = new long[4];
        l[0] = new Date().getDay();
        l[1] = new Date().getHours();
        l[2] = new Date().getMinutes();
        l[3] = new Date().getSeconds();
        return l;
    }

    /**
     * 根据日期获得星期
     *
     * @param date
     * @return
     */
    public static String getWeekOfDate(Date date) {
        String[] weekDaysName = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        String[] weekDaysCode = {"0", "1", "2", "3", "4", "5", "6"};
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int intWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        return weekDaysName[intWeek];
    }

    /**
     * 获取指定日期的日期号
     *
     * @param date
     * @return
     */
    public static String getDayOfDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int intDay = calendar.get(Calendar.DAY_OF_MONTH);
        return intDay + "";
    }

    /**
     * 时间个性化转换
     *
     * @param currDate   当前时间
     * @param covertDate 发布时间
     * @return 转化后的字符串
     */
    @SuppressWarnings("deprecation")
    public static String toPDateStr(long currDate, long covertDate) {
        int d_minutes, d_hours, d_days;
        long timeNow = currDate; // 转换为服务器当前时间戳
        long currcovertDate = covertDate;
        long d;
        String result;
        d = (timeNow - currcovertDate) / 1000;
        d_days = (int) (d / 86400);
        d_hours = (int) (d / 3600);
        d_minutes = (int) (d / 60);
        if (d_days > 0 && d_days < 4) {
            result = d_days + "天前";
        } else if (d_days <= 0 && d_hours > 0) {
            result = d_hours + "小时前";
        } else if (d_hours <= 0 && d_minutes > 0) {
            result = d_minutes + "分钟前";
        } else if (d_minutes <= 0 && d >= 0) {
            return Math.round(d) + "秒前";
        } else {
            Date s = new Date(currcovertDate);
            result = (s.getMonth() + 1) + "月" + s.getDate() + "日";
        }
        return result;
    }

    public static String secToFormat(int sec) {
        int m = sec / 60;
        String mstr = m < 10 ? "0" + m : m + "";
        int se = sec % 60;
        String sestr = se < 10 ? "0" + se : se + "";
        return mstr + ":" + sestr;
    }

    public static String formatMsTime(int ms) {
        int totalSeconds = ms / 1000;
        int seconds = totalSeconds % 60;
        int minutes = totalSeconds / 60 % 60;
        int hours = totalSeconds / 60 / 60;
        String timeStr = "";
        if (hours > 9) {
            timeStr += hours + ":";
        } else if (hours > 0) {
            timeStr += "0" + hours + ":";
        }
        if (minutes > 9) {
            timeStr += minutes + ":";
        } else if (minutes > 0) {
            timeStr += "0" + minutes + ":";
        } else {
            timeStr += "00:";
        }
        if (seconds > 9) {
            timeStr += seconds;
        } else if (seconds > 0) {
            timeStr += "0" + seconds;
        } else {
            timeStr += "00";
        }

        return timeStr;
    }

    public static String minToHour(int sec) {
        int hour = sec / 3600;
        int minute = (sec - hour * 3600) / 60;
        if (hour > 0) {
            return hour + "小时" + minute + "分钟";
        }
        return minute + "分钟";
    }

    public static String mToKm(int m) {
        int km = m / 1000;
        int ms = m - km * 1000;
        if (km > 0) {
            return km + "." + ms + "公里";
        }
        return "0." + ms + "公里";
    }

    /**
     * 以"yyyyMMdd"形式返回当前系统时间
     *
     * @return
     */
    public static String getCurrentDate() {
        SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMMdd");
        return simpledateformat.format(new Date()).toString();
    }
}
