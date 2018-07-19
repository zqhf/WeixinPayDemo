package com.zhaoqing.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * 操作日期类型数据的工具类
 * 
 * @author Administrator
 * @version 1.0, 2011-7-28
 * @since
 */
public class DateUtil {
	//日期格式
	private static final String DATE_PATTERN_1 = "yyyy-MM-dd";
	private static final String DATE_PATTERN_2 = "yyyyMMdd";  
	private static final String DATE_PATTERN_3 = "yyyy/MM/dd";
	
	public static final String DATE_PATTERN_TIMESTAMP = "yyyy-MM-dd'T'HH:mm:ss'Z'";
	
	//时间格式
	private static final String TIME_PATTERN_1 = "HH:mm:ss";
	private static final String TIME_PATTERN_2 = "HHmmss";
	
	public static final String DATE_FORMAT_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
	
	public static final String DATE_FORMAT_YYYYMMDDHHMMSSS = "yyyyMMddHHmmsss";
	
	public static final String DATE_FORMAT2 = "yyyy/MM/dd HH:mm:ss";
	
	//日期时间格式
	public static final String DATETIME_PATTERN_1 = "yyyy-MM-dd HH:mm:ss";
	
	private static final String TIMESTAMP = "yyyyMMddHHmmsssss";
	
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat();
	
	//private static final SimpleDateFormat DATE_FORMAT_YYYY_MM_DD = new SimpleDateFormat("yyyy-MM-dd");
	//private static final SimpleDateFormat DATE_FORMAT_HH_MM_SS = new SimpleDateFormat("HH:mm:ss");
	/**
	 * 获取当前日期
	 * @return String YYYY-MM-DD
	 */
	public static String getCurrentDateStr1() {
		return getCurrentDateStr(DATE_PATTERN_1);
	}
	
	/**
	 * 获取当前日期
	 * @return String YYYYMMDD
	 */
	public static String getCurrentDateStr2() {
		return getCurrentDateStr(DATE_PATTERN_2);
	}
	
	/**
	 * 获取当前日期
	 * @return String yyyy/MM/dd
	 */
	public static String getCurrentDateStr3() {
		return getCurrentDateStr(DATE_PATTERN_3);
	}
	/**
	 * 获取指定格式的当前日期
	 * @param format
	 * @return
	 */
	public static String getCurrentDateStr(String format) {
		String dateStr = "";
		Calendar cal = Calendar.getInstance();
		
		DATE_FORMAT.applyPattern(format);
		dateStr = DATE_FORMAT.format(cal.getTime());
		
		return dateStr;
	}
	
	/**
	 * 获取当前时间
	 * @return String HH:mm:ss
	 */
	public static String getCurrentTimeStr1() {
		return getCurrentTimeStr(TIME_PATTERN_1);
	}
	
	/**
	 * 获取当前时间
	 * @return String HHmmss
	 */
	public static String getCurrentTimeStr2() {
		return getCurrentTimeStr(TIME_PATTERN_2);
	}
	
	/**
	 * 获取当前时间
	 * @param format
	 * @return
	 */
	public static String getCurrentTimeStr(String format) {
		String timeStr = "";
		Calendar cal = Calendar.getInstance();
		
		DATE_FORMAT.applyPattern(format);
		timeStr = DATE_FORMAT.format(cal.getTime());
		
		return timeStr;
	}
	
	/**
	 * 获取当前日期时间
	 * @return String yyyy-MM-dd HH-mm-ss
	 */
	public static String getCurrentDateTimeStr() {
		
		return getCurrentDateStr1() + " " + getCurrentTimeStr1();
	}
	
	/**
	 * 获取当前日期时间
	 * @return String yyyyMMddHHmmss
	 */
	public static String getCurrentDateTimeStr2() {
		
		return getCurrentDateStr2() + getCurrentTimeStr2();
	}

	/**
	 * 获取当前日期时间
	 * @return String yyyyMMddHHmmss
	 */
	public static String getTimeStamp() {
		return getCurrentDateStr(TIMESTAMP);
	}
	
	
	public static String formatDate(Date d) {
		DATE_FORMAT.applyPattern(DATE_PATTERN_1);
		return DATE_FORMAT.format(d);
	}
	
	public static String formatDateyyyyMMddHHmmss(Date d) {
		DATE_FORMAT.applyPattern(DATE_FORMAT_YYYYMMDDHHMMSS);
		return DATE_FORMAT.format(d);
	}
	
	public static String formatDateyyyyMMddHHmmsss(Date d) {
		DATE_FORMAT.applyPattern(DATE_FORMAT_YYYYMMDDHHMMSSS);
		return DATE_FORMAT.format(d);
	}
	
	public static String formatDate(String dateStr) {
		Date d = null;
		String date = "";
		try {
			d = DATE_FORMAT.parse(dateStr);
			DATE_FORMAT.applyPattern(DATE_PATTERN_1);
			date = DATE_FORMAT.format(d);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	public static String formatTime(Date d) {
		DATE_FORMAT.applyPattern(TIME_PATTERN_1);
		return DATE_FORMAT.format(d);
	}
	
	public static String formatTime(String time) {
		Date d = null;
		String str = "";
		try {
			d = DATE_FORMAT.parse(time);
			DATE_FORMAT.applyPattern(TIME_PATTERN_1);
			str = DATE_FORMAT.format(d);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return str;
	}
	
	public static String formatDateTime(Date d) {
		DATE_FORMAT.applyPattern(DATETIME_PATTERN_1);
		return DATE_FORMAT.format(d);
	}
	
	public static String formatDateTime(String datetime) {
		Date d = null;
		String str = "";
		try {
			d = DATE_FORMAT.parse(datetime);
			DATE_FORMAT.applyPattern(DATETIME_PATTERN_1);
			str = DATE_FORMAT.format(d);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return str;
	}
	
	public static int getCurYear()
    {
        Calendar today = GregorianCalendar.getInstance();
        return today.get(Calendar.YEAR);
    }

    public static int getCurMonth()
    {
        Calendar today = GregorianCalendar.getInstance();
        return today.get(Calendar.MONTH) + 1;
    }

    public static int getCurDay()
    {
        Calendar today = GregorianCalendar.getInstance();
        return today.get(Calendar.DATE);
    }
	
	/**
     * 获取前后N年的年集合
     * 
     * @return
     */
    public static List<Integer> getYears(int prevYearNumber, int nextYearNumber)
    {
        List<Integer> years = new ArrayList<Integer>();
        Integer curYear = DateUtil.getCurYear();
        for (int i = curYear - prevYearNumber; i <= curYear + nextYearNumber; i++)
        {
            years.add(i);
        }
        return years;
    }
    
    /**
     * 获取月列表
     * @方法名称：
     * @方法描述：
     * @引用表：
     * @详细流程：
     *
     * @param n 
     *   n = 0 返回[1...12]
     *   n = 1.. 12 返回  [1.. n]
     *   n = -1 返回[1... 当前月]
     * @return
     */
    public static List<Integer> getMonths(int n)
    {
        if (n == 0)
        {
            n = 12; // 返回12个月
        }
        else if (n == -1)
        {
            n = DateUtil.getCurMonth();
        }
        
        List<Integer> month = new ArrayList<Integer>();
        
        for (int i = 1; i <= n; i++)
        {
            month.add(i);
        }
        return month;
    }
    
    /**
     * 获取前/后几个月的第一天
     * 
     * @return
     * @throws Exception
     */
    public static Calendar getFirstDayByAddMonth(int n)
    {
        Calendar lastDate = GregorianCalendar.getInstance();
        lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
        lastDate.add(Calendar.MONTH, n);// 减N个月，变为上月的1号
        return lastDate;
    }

    /**
     * 
     * @方法名称：格式化一个字符串日期
     * @方法描述：
     *
     * @param strDate
     * @param pattren
     * @return
     * @throws Exception
     */
    public static String formatDate(String strDate, String pattren) throws Exception
    {
        SimpleDateFormat sdf = new SimpleDateFormat(pattren);
        Date d = sdf.parse(strDate);
        String date = sdf.format(d);
        return date;
    }
    
    
    /**
     * 
     * @方法名称：对某日期加减月
     *
     * @param now
     * @param n
     * @return
     */
    public static Date addMonth(Date date, int n)
    {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTimeInMillis(date.getTime());
        
        calendar.add(Calendar.MONTH, n);
        
        return new Date(calendar.getTimeInMillis());
    }
    
    /**
     * 
     * @方法名称：对某日期加减天
     *
     * @param now
     * @param n
     * @return
     */
    public static Date addDay(Date date, int n)
    {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTimeInMillis(date.getTime());
        
        calendar.add(Calendar.DAY_OF_MONTH, n);
        
        return new Date(calendar.getTimeInMillis());
    }
    
    /**
     * 
     * @方法名称：对某日期加减小时
     *
     * @param now
     * @param n
     * @return
     */
    public static Date addHour(Date date, int n)
    {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTimeInMillis(date.getTime());
        
        calendar.add(Calendar.HOUR, n);
        
        return new Date(calendar.getTimeInMillis());
    }
    
    public static Date toDate(String dateStr) {
		DATE_FORMAT.applyPattern(DATETIME_PATTERN_1);
		try {
			return DATE_FORMAT.parse(dateStr);
		} catch (ParseException e) {
			
		}
		return null;
	}
    
    /**
     * 将时间转换为int
     * @param time
     * @return
     */
    public static int string2intForTime(String time)
    {
    	//获取时间中除冒号以外的其他值   
		StringBuffer sba = new StringBuffer();   
		//获取kk   
		String astartTime = time.substring(0, 2);   
		//获取mm   
		String bstartTime = time.substring(3, 5);   
		//获取ss   
		String cstartTime = time.substring(6, 8);   
		  
		sba.append(astartTime);   
		sba.append(bstartTime);   
		sba.append(cstartTime);   
		String dstartTime = sba.toString();   
		//将系统时间转换为Int型   
		int newTime = Integer.parseInt(dstartTime);
		return newTime;
    }
    
    public static String formatDate(Date date, String pattren) throws Exception
    {
        SimpleDateFormat sdf = new SimpleDateFormat(pattren);
        String dateString = sdf.format(date);
        return dateString;
    }
}
