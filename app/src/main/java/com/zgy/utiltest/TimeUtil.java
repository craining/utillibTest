package com.zgy.utiltest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {

	private static String TIME_DATE_TIME_STRING_FORMAT = "yyyy-MM-dd HH:mm:ss";// String 时间格式
	private static String TIME_DATE_STRING_FORMAT      = "yyyy-MM-dd";// String 时间格式
	
	
//	private static String TIME_DATE_STRING_FORMAT_CN = "yyyy年MM月dd日";// String 时间格式
//	private static String TIME_DATE_STRING_FORMAT_CN1 = "yyyy年MM月dd日";// String 时间格式
	private static String TIME_DATE_STRING_FORMAT_CN = "yyyy.MM.dd";// String 时间格式
	
	
	private static long ONE_MINUTE = 60000;
	private static long ONE_HOUR = ONE_MINUTE*60;
	private static long ONE_DAY = ONE_HOUR*24;
	private static long ONE_MONTH = ONE_DAY*30;
	private static long ONE_YEAR = ONE_MONTH*12;

	/**
	 * 获得系统当前时间
	 * 
	 * @Description:
	 * @return long型时间
	 * @see:
	 * @since:
	 * @author: zhuanggy
	 * @date:2013-3-8
	 */
	public static long getCurrentTimeMillis() {
		return (System.currentTimeMillis() / 1000) * 1000;// 精确到秒
	}

	
	/**
	 * 获得当前日期
	 * @param @return 
	 * @author zhuanggy
	 * @date 2014-5-16
	 */
	public static String getCurrentDateString() {
		return longToDateString(System.currentTimeMillis());
	}
	
	/**
	 * 获得当前日期时间
	 * @param @return 
	 * @author zhuanggy
	 * @date 2014-5-16
	 */
	public static String getCurrentDateTimeString() {
		return longToDateTimeString(System.currentTimeMillis());
	}
	
	/**
	 * 日期+时间的string 型转为long型
	 * 
	 * @Description:
	 * @param dateTime
	 *            (型如 ： 2013-03-08 13:51:00)
	 * @return
	 * @see:
	 * @since:
	 * @author: zhuanggy
	 * @date:2013-3-8
	 */
	public static long dateTimeStringToLong(String dateTime) {
		SimpleDateFormat sdf = new SimpleDateFormat(TIME_DATE_TIME_STRING_FORMAT);
		Date dt2;
		try {
			dt2 = sdf.parse(dateTime);
			return dt2.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return -1;
	}

	/**
	 * long型时间转为日期+时间的String型
	 * 
	 * @Description:
	 * @param dateTimeMillis
	 * @return
	 * @see:
	 * @since:
	 * @author: zhuanggy
	 * @date:2013-3-8
	 */
	public static String longToDateTimeString(long dateTimeMillis) {
		SimpleDateFormat sdf = new SimpleDateFormat(TIME_DATE_TIME_STRING_FORMAT);
		Date dt = new Date(dateTimeMillis);
		return sdf.format(dt);
	}
	
	/**
	 * str型的long时间转为日期+时间的String型
	 * 
	 * @Description:
	 * @param dateTimeMillis
	 * @return
	 * @see:
	 * @since:
	 * @author: zhuanggy
	 * @date:2013-5-14
	 */
	public static String longToDateTimeString(String dateTimeMillis) {
		
		String result = "";
		try {
			result = longToDateTimeString(Long.parseLong(dateTimeMillis));
		} catch (Exception e) {
			e.printStackTrace();
		}
		 return result;
	}

	/**
	 * 日期+时间的string 型转为long型
	 * 
	 * @Description:
	 * @param date
	 *            (型如 ： 2013-03-08)
	 * @return
	 * @see:
	 * @since:
	 * @author: zhuanggy
	 * @date:2013-3-8
	 */
	public static long dateStringToLong(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat(TIME_DATE_STRING_FORMAT);
		Date dt2;
		try {
			dt2 = sdf.parse(date);
			return dt2.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return -1;
	}

	/**
	 * long型时间转为日期String型
	 * 
	 * @Description:
	 * @param dateTimeMillis
	 * @return
	 * @see:
	 * @since:
	 * @author: zhuanggy
	 * @date:2013-3-8
	 */
	public static String longToDateString(long dateTimeMillis) {
		SimpleDateFormat sdf = new SimpleDateFormat(TIME_DATE_STRING_FORMAT);
		Date dt = new Date(dateTimeMillis);
		return sdf.format(dt);
	}
	
	/**
	 * long型时间转为日期String型
	 * 
	 * @Description:
	 * @param dateTimeMillis
	 * @return
	 * @see:
	 * @since:
	 * @author: zhuanggy
	 * @date:2013-3-8
	 */
	public static String longToDateStringCN(long dateTimeMillis) {
		SimpleDateFormat sdf = new SimpleDateFormat(TIME_DATE_STRING_FORMAT_CN);
		Date dt = new Date(dateTimeMillis);
		return sdf.format(dt);
	}
	
	public static String longToDateStringCN1(long dateTimeMillis) {
//		String TIME_DATE_STRING_FORMAT_CN1 = "yyyy" + FontApplication.getInstance().getString(R.string.str_year_label)+ "MM" + FontApplication.getInstance().getString(R.string.str_month_label)+ "dd" + FontApplication.getInstance().getString(R.string.str_day_label);// String 时间格式
		String TIME_DATE_STRING_FORMAT_CN1 = "yyyy年MM月dd日";// String 时间格式

		SimpleDateFormat sdf = new SimpleDateFormat(TIME_DATE_STRING_FORMAT_CN1);
		Date dt = new Date(dateTimeMillis);
		return sdf.format(dt);
	}
	
	/**
	 * str型的long时间转为日期String型
	 * 
	 * @Description:
	 * @param dateTimeMillis
	 * @return
	 * @see:
	 * @since:
	 * @author: zhuanggy
	 * @date:2013-5-14
	 */
	public static String longToDateString(String dateTimeMillis) {
		String result = "";
		try {
			result = longToDateString(Long.parseLong(dateTimeMillis));
		} catch (Exception e) {
			e.printStackTrace();
		}
		 return result;
	}
	
	/**
	 * str型的long时间转为日期String型
	 * 
	 * @Description:
	 * @param dateTimeMillis
	 * @return
	 * @see:
	 * @since:
	 * @author: zhuanggy
	 * @date:2013-5-14
	 */
	public static String longToDateStringCN(String dateTimeMillis) {
		String result = "";
		try {
			result = longToDateStringCN(Long.parseLong(dateTimeMillis));
		} catch (Exception e) {
			e.printStackTrace();
		}
		 return result;
	}
	
	/**
	 * str型的long时间转为日期String型
	 * 
	 * @Description:
	 * @param dateTimeMillis
	 * @return XXXX年XX月XX日
	 * @see:
	 * @since:
	 * @author: xln
	 * @date:2015-10-21
	 */
	public static String longToDateStringCN1(String dateTimeMillis) {
		String result = "";
		try {
			result = longToDateStringCN1(Long.parseLong(dateTimeMillis));
		} catch (Exception e) {
			e.printStackTrace();
		}
		 return result;
	}
	
	/**
	 * 获取距离当前时间
	 * 
	 * @Description:
	 * @param dateTimeMillis
	 * @return
	 * @see:
	 * @since:
	 * @author: lixiangwei
	 * @date:2014-6-18
	 */
	public static String long2NowString(long dateTimeMillis) {
		String result = "";
		try {
			long long2now = TimeUtil.getCurrentTimeMillis()-dateTimeMillis;
			long dates = 0;
			if(long2now >= ONE_YEAR){
				result = long2now/ONE_YEAR+"years later";
			}else if(long2now >= ONE_MONTH ){
				result = long2now/ONE_MONTH+"months later";
			}else if(long2now >= ONE_DAY ){
				result = long2now/ONE_DAY+"days later";
			}else if(long2now >= ONE_HOUR ){
				result = long2now/ONE_HOUR+"hours later";
			}else if(long2now >= ONE_MINUTE ){
				result = long2now/ONE_MINUTE+"minutes later";
			}else {
				result ="just now";
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		 return result;
	}
}
