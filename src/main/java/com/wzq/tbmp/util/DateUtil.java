package com.wzq.tbmp.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import com.wzq.tbmp.config.Constant;

public class DateUtil {
	private static SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static void setSdfTimeZone() {
		sdf1.setTimeZone(getTimeZone());
	}
	
	public static Date getNow() {
		Calendar cal = getCalendar();
		return cal.getTime();
	}
	
	/**
	 * 获取当前秒数
	 * @return
	 */
	public static long getNowUnixTime() {
		return System.currentTimeMillis() / 1000;
	}
	
	public static TimeZone getTimeZone() {
		int timeZoneVal = Constant.TIME_ZONE;
		if (timeZoneVal > 0) {
			return TimeZone.getTimeZone("GMT+" + timeZoneVal);
		} else {
			return TimeZone.getTimeZone("GMT-" + Math.abs(timeZoneVal));
		}
	}

	public static Calendar getCalendar() {
		return Calendar.getInstance(getTimeZone());
	}
	
}
