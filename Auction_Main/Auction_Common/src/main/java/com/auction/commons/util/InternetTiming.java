package com.auction.commons.util;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class InternetTiming {

	public static Date getInternetDateTime() {
		Date dateTime = new Date();//null;
		return dateTime;
	}

	public static long getInternetDateTimeAsMiliSeconds() {
		long miliSeconds = new Date().getTime();//0;
		return miliSeconds;
	}

	public static String getApplicationTimeZone() {
		return TimeZone.getDefault().getID();
	}
	public static int getCurrentMonth() {
		return Calendar.getInstance().get(Calendar.MONTH)+1;
	}
	public static int getCurrentYear() {
		return Calendar.getInstance().get(Calendar.YEAR);
	} 
}