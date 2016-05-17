package com.wzq.tbmp.util;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import com.wzq.tbmp.config.Constant;

public class DateUtil {
	private static SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy/MM");
	private static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat sdf5 = new SimpleDateFormat("yyyy-MM-dd E", Locale.CHINESE);
	private static SimpleDateFormat sdf4 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	private static SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static Date getDateByTime(Date date, String time) {
		try {
			String date1 = sdf2.format(date);
			return sdf4.parse(date1 + " " + time);
		} catch (Exception e) {
			return null;
		}
	}

	public static String getTodayStr() {
		return sdf2.format(getToday());
	}
	
	public static long getNowUnixTime() {
		return System.currentTimeMillis() / 1000;
	}
	
	public static Date getToday() {
		Calendar cal = Calendar.getInstance();
		return cal.getTime();
	}
	public static Date getNow() {
		Calendar cal = Calendar.getInstance();
		return cal.getTime();
	}

	public static String formatDateToYMdHms(Date date) {
		return sdf1.format(date);
	}

	public static String formatDateToYMd(Date date) {
		return sdf2.format(date);
	}
	
	public static String formatDateToYMdE(Date date) {
		return sdf5.format(date);
	}

	public static String formatDateToYM(Date date) {
		return sdf3.format(date);
	}
	
	public static Date parseYMdHmToDate(String date) throws Exception {
		return sdf4.parse(date);
	}
	
	public static Date parseYMdHmsToDate(String date) throws Exception {
		return sdf1.parse(date);
	}
	
	public static Date parseYMdToDate(String date) throws Exception{
		return sdf2.parse(date);
	}
	
	public static Date parseYMToDate(String date) throws Exception{
		return sdf3.parse(date);
	}
	
	public static Date getTodayWithoutHms() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
	
	public static int getYearOld(Date birthday, int maxAge) {
		if (birthday == null) {
			return 0;
		}
		Calendar today = Calendar.getInstance();
		Calendar cal = Calendar.getInstance();
		cal.setTime(birthday);
		int year = today.get(Calendar.YEAR) - cal.get(Calendar.YEAR);
		if (year == 0) {
			return 0;
		}
		cal.set(Calendar.YEAR, today.get(Calendar.YEAR));
		if (cal.getTime().getTime() >= getTodayWithoutHms().getTime()) {
			year--;
		}
		return Math.min(year, maxAge);
	}
	
	public static Calendar getCalendar(){
		Calendar nowCal = Calendar.getInstance();
		return nowCal;
	}
	
	public static int getCurrentHour() {
		return getCalendar().get(Calendar.HOUR_OF_DAY);
	}
	
	public static TimeZone getTimeZone() {
		int timeZoneVal = Constant.TIME_ZONE;
		if (timeZoneVal > 0) {
			return TimeZone.getTimeZone("GMT+" + timeZoneVal);
		} else {
			return TimeZone.getTimeZone("GMT-" + Math.abs(timeZoneVal));
		}
	}
	
	public static void setSdfTimeZone() {
		sdf1.setTimeZone(getTimeZone());
	}
}
