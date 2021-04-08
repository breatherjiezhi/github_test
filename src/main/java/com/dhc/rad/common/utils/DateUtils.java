/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package com.dhc.rad.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日期工具类, 继承org.apache.commons.lang.time.DateUtils类
 * @author DHC
 * @version 2014-4-15
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
	static Logger logger = LoggerFactory.getLogger(DateUtils.class);
	private static String[] parsePatterns = {
		"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM", 
		"yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
		"yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd）
	 */
	public static String getDate() {
		return getDate("yyyy-MM-dd");
	}
	
	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String getDate(String pattern) {
		return DateFormatUtils.format(new Date(), pattern);
	}
	
	/**
	 * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String formatDate(Date date, Object... pattern) {
		String formatDate = null;
		if (pattern != null && pattern.length > 0) {
			formatDate = DateFormatUtils.format(date, pattern[0].toString());
		} else {
			formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
		}
		return formatDate;
	}
	
	/**
	 * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String formatDateTime(Date date) {
		return formatDate(date, "yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
	 * @author maliang
	 */
	public static String formatDatePro(Date date, Object... pattern) {
		String formatDate = null;
		if (date != null) {
			if (pattern != null && pattern.length > 0) {
				formatDate = DateFormatUtils.format(date, pattern[0].toString());
			} else {
				formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
			}
		}
		return formatDate;
	}

	/**
	 * 得到当前时间字符串 格式（HH:mm:ss）
	 */
	public static String getTime() {
		return formatDate(new Date(), "HH:mm:ss");
	}

	/**
	 * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String getDateTime() {
		return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到当前年份字符串 格式（yyyy）
	 */
	public static String getYear() {
		return formatDate(new Date(), "yyyy");
	}

	/**
	 * 得到当前月份字符串 格式（MM）
	 */
	public static String getMonth() {
		return formatDate(new Date(), "MM");
	}

	/**
	 * 得到当天字符串 格式（dd）
	 */
	public static String getDay() {
		return formatDate(new Date(), "dd");
	}

	/**
	 * 得到当前星期字符串 格式（E）星期几
	 */
	public static String getWeek() {
		return formatDate(new Date(), "E");
	}
	
	/**
	 * 日期型字符串转化为日期 格式
	 * { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", 
	 *   "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm",
	 *   "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm" }
	 */
	public static Date parseDate(Object str) {
		if (str == null){
			return null;
		}
		try {
			return parseDate(str.toString(), parsePatterns);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 获取过去的天数
	 * @param date
	 * @return
	 */
	public static long pastDays(Date date) {
		long t = new Date().getTime()-date.getTime();
		return t/(24*60*60*1000);
	}

	/**
	 * 获取过去的小时
	 * @param date
	 * @return
	 */
	public static long pastHour(Date date) {
		long t = new Date().getTime()-date.getTime();
		return t/(60*60*1000);
	}
	
	/**
	 * 获取过去的分钟
	 * @param date
	 * @return
	 */
	public static long pastMinutes(Date date) {
		long t = new Date().getTime()-date.getTime();
		return t/(60*1000);
	}
	
	/**
	 * 转换为时间（天,时:分:秒.毫秒）
	 * @param timeMillis
	 * @return
	 */
    public static String formatDateTime(long timeMillis){
		long day = timeMillis/(24*60*60*1000);
		long hour = (timeMillis/(60*60*1000)-day*24);
		long min = ((timeMillis/(60*1000))-day*24*60-hour*60);
		long s = (timeMillis/1000-day*24*60*60-hour*60*60-min*60);
		long sss = (timeMillis-day*24*60*60*1000-hour*60*60*1000-min*60*1000-s*1000);
		return (day>0?day+",":"")+hour+":"+min+":"+s+"."+sss;
    }
	
	/**
	 * 获取两个日期之间的天数
	 * 
	 * @param before
	 * @param after
	 * @return
	 */
	public static double getDistanceOfTwoDate(Date before, Date after) {
		long beforeTime = before.getTime();
		long afterTime = after.getTime();
		return (afterTime - beforeTime) / (1000 * 60 * 60 * 24);
	}
	
	
    /** 
     * 获得指定日期的前一天 
     *  
     * @param specifiedDay 
     * @return 
     * @throws ParseException 
     * @throws Exception 
     */  
    public static Date getSpecifiedDayBefore(Date specifiedDay,int dayNum) throws ParseException {//可以用new Date().toLocalString()传递参数  
    	SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
		Calendar date = Calendar.getInstance();
		date.setTime(specifiedDay);
		date.set(Calendar.DATE, date.get(Calendar.DATE) + dayNum);
		Date endDate = dft.parse(dft.format(date.getTime()));  
		return endDate;
    }


	/**
	 *获取本周起始日期
	 */
	public static String getTimeIn(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		// 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
		int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
		if (1 == dayWeek) {
			cal.add(Calendar.DAY_OF_MONTH, -1);
		}
		// System.out.println("要计算日期为:" + sdf.format(cal.getTime())); // 输出要计算日期
		// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		// 获得当前日期是一个星期的第几天
		int day = cal.get(Calendar.DAY_OF_WEEK);
		// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
		cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
		String imptimeBegin = sdf.format(cal.getTime());
		// System.out.println("所在周星期一的日期：" + imptimeBegin);
		cal.add(Calendar.DATE, 6);
		String imptimeEnd = sdf.format(cal.getTime());
		// System.out.println("所在周星期日的日期：" + imptimeEnd);
		return imptimeBegin + "," + imptimeEnd;
	}

	/**
	 * 获取两日期间所有日期集合
	 * @param dBegin
	 * @param dEnd
	 * @return
	 */
	public static List<String> findInDates(Date dBegin, Date dEnd)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List lDate = new ArrayList();
		List<String> Dlist = new ArrayList<>();
		lDate.add(dBegin);
		Calendar calBegin = Calendar.getInstance();
		// 使用给定的 Date 设置此 Calendar 的时间
		calBegin.setTime(dBegin);
		Calendar calEnd = Calendar.getInstance();
		// 使用给定的 Date 设置此 Calendar 的时间
		calEnd.setTime(dEnd);
		// 测试此日期是否在指定日期之后
		Dlist.add(sdf.format(calBegin.getTime()));
		while (dEnd.after(calBegin.getTime()))
		{
			// 根据日历的规则，为给定的日历字段添加或减去指定的时间量
			calBegin.add(Calendar.DAY_OF_MONTH, 1);
			Dlist.add(sdf.format(calBegin.getTime()));
		}
		return Dlist;
	}

    /**
	   * 切割时间段
	   *
	   * @param dateType 交易类型 M/D/H/N -->每月/每天/每小时/每分钟
	   * @param start yyyy-MM-dd HH:mm:ss
	   * @param end  yyyy-MM-dd HH:mm:ss
	   * @return
	   */
	  public static List<String> cutDate(String start, String end) {
	    try {
	      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	      Date dBegin = sdf.parse(start);
	      Date dEnd = sdf.parse(end);
	      return findDates("D", dBegin, dEnd);
	    } catch (Exception e) {
	      logger.error(e.getMessage(), e);
	    }
	    return null;
	  }
	 
	  public static List<String> findDates(String dateType, Date dBegin, Date dEnd) throws Exception {
	    List<String> listDate = new ArrayList<>();
	    Calendar calBegin = Calendar.getInstance();
	    calBegin.setTime(dBegin);
	    Calendar calEnd = Calendar.getInstance();
	    calEnd.setTime(dEnd);
	    listDate.add("'"+new SimpleDateFormat("yyyy-MM-dd").format(calBegin.getTime())+"'");
	    while (calEnd.after(calBegin)) {
	      switch (dateType) {
	        case "M":
	          calBegin.add(Calendar.MONTH, 1);
	          break;
	        case "D":
	          calBegin.add(Calendar.DAY_OF_YEAR, 1);break;
	        case "H":
	          calBegin.add(Calendar.HOUR, 1);break;
	        case "N":
	          calBegin.add(Calendar.SECOND, 1);break;
	      }
	      if (calEnd.after(calBegin))
	    	  listDate.add("'"+new SimpleDateFormat("yyyy-MM-dd").format(calBegin.getTime())+"'");
	      else
	    	  listDate.add("'"+new SimpleDateFormat("yyyy-MM-dd").format(calBegin.getTime())+"'");
	    }
	    return listDate;
	  }
	  public static List<String> cutDate2(String start, String end) {
		    try {
		      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		      Date dBegin = sdf.parse(start);
		      Date dEnd = sdf.parse(end);
		      return findDates2("D", dBegin, dEnd);
		    } catch (Exception e) {
		      logger.error(e.getMessage(), e);
		    }
		    return null;
		  }
		 
		  public static List<String> findDates2(String dateType, Date dBegin, Date dEnd) throws Exception {
		    List<String> listDate = new ArrayList<>();
		    Calendar calBegin = Calendar.getInstance();
		    calBegin.setTime(dBegin);
		    Calendar calEnd = Calendar.getInstance();
		    calEnd.setTime(dEnd);
		    listDate.add(new SimpleDateFormat("yyyy-MM-dd").format(calBegin.getTime()));
		    while (calEnd.after(calBegin)) {
		      switch (dateType) {
		        case "M":
		          calBegin.add(Calendar.MONTH, 1);
		          break;
		        case "D":
		          calBegin.add(Calendar.DAY_OF_YEAR, 1);break;
		        case "H":
		          calBegin.add(Calendar.HOUR, 1);break;
		        case "N":
		          calBegin.add(Calendar.SECOND, 1);break;
		      }
		      if (calEnd.after(calBegin))
		    	  listDate.add(new SimpleDateFormat("yyyy-MM-dd").format(calBegin.getTime()));
		      else
		    	  listDate.add(new SimpleDateFormat("yyyy-MM-dd").format(calBegin.getTime()));
		    }
		    return listDate;
		  }
		  
	  
	  
	/**
	 * @param args
	 * @throws ParseException
	 */
	public static void main(String[] args) throws ParseException {
//		System.out.println(formatDate(parseDate("2010/3/6")));
//		System.out.println(getDate("yyyy年MM月dd日 E"));
//		long time = new Date().getTime()-parseDate("2012-11-19").getTime();
//		System.out.println(time/(24*60*60*1000));
	}
}
