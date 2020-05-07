
package com.auction.commons.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.auction.commons.constant.CommonConstants;

@Component
public class DateHelper {

	private static SimpleDateFormat startDateFormat = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
	private static SimpleDateFormat endDateFormat = new SimpleDateFormat("yyyy-MM-dd 23:59:59");

	private static SimpleDateFormat dateToString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static SimpleDateFormat dateToStringPMAM = new SimpleDateFormat("EEEE, dd MMMM  yyyy hh:mm:ss a");
	private static SimpleDateFormat monthAndYearDateFormat = new SimpleDateFormat("MM-yyyy");
	private static SimpleDateFormat onlyDateToString = new SimpleDateFormat("dd-MM-yyyy");

	@Value("${auction.default.duration}")
	private int auctionDuration;

	private static AuctionLogger logger = new AuctionLogger("DateHelper");

	/**
	 * This method helps to get date into specified string format.
	 * 
	 * @param date
	 *            Provide java.util.Date date as first argument.
	 * 
	 * @param isStartDate
	 *            {@code true} to get starting date like as yyyy-MM-dd 00:00:00 or
	 *            {@code false} to get ending date like as yyyy-MM-dd 23:59:59
	 * 
	 * @return it will return appropriate date staring of given date in argument.
	 */

	public static String getDateString(Date date, boolean isStartDate) {
		logger.info("DateHelper class in call getDateString method ");
		String strDate = null;
		if (isStartDate) {
			strDate = startDateFormat.format(date);
		} else {
			strDate = endDateFormat.format(date);
		}
		return strDate;
	}

	/**
	 * This method helps to get date into specified string format.
	 * 
	 * @param date
	 *            Provide java.util.Date date as first argument.
	 * 
	 * @param isStartDate
	 *            {@code true} to get starting date like as yyyy-MM-dd 00:00:00 or
	 *            {@code false} to get ending date like as yyyy-MM-dd 23:59:59
	 * 
	 * @return it will return appropriate date staring of given date in argument.
	 * @throws ParseException
	 */
	public static String getDateString(String date, boolean isStartDate) {
		logger.info("DateHelper class in call getDateString method ");
		String strDate = "";
		try {
			if (isStartDate) {
				strDate = startDateFormat.format(startDateFormat.parse((date + " 00:00:00")));
			} else {
				strDate = endDateFormat.format(endDateFormat.parse((date + " 23:59:59")));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return strDate;
	}

	/**
	 * This method helps to getStringToDate.
	 */
	public static Date getStringToDate(String date, boolean isStartDate) {
		logger.info("DateHelper class in call getStringToDate method ");
		Date formatedDate = null;
		try {
			if (isStartDate) {
				formatedDate = startDateFormat.parse((date + " 00:00:00"));
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(formatedDate);
				calendar.set(Calendar.MILLISECOND, 0);
				calendar.set(Calendar.SECOND, 0);
				calendar.set(Calendar.MINUTE, 0);
				calendar.set(Calendar.HOUR_OF_DAY, 0);
				formatedDate = calendar.getTime();
			} else {
				formatedDate = endDateFormat.parse((date + " 23:59:59"));
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(formatedDate);
				calendar.set(Calendar.MILLISECOND, 00);
				calendar.set(Calendar.SECOND, 59);
				calendar.set(Calendar.MINUTE, 59);
				calendar.set(Calendar.HOUR_OF_DAY, 23);
				formatedDate = calendar.getTime();
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return formatedDate;
	}

	/**
	 * This method helps to getString To Date java.sql.Date
	 */
	public static java.sql.Date getSqlDateFromString(String inputDate) {
		logger.info("DateHelper class in call getSqlDateFromString method ");
		if (null != inputDate && !inputDate.isEmpty()) {
			return java.sql.Date.valueOf(inputDate);
		} else {
			return new java.sql.Date(System.currentTimeMillis());
		}

	}

	public static Date getTomorrowDate(Date date, boolean isStartDate) {
		logger.info("DateHelper class in call getTomorrowDate method ");
		Date formatedDate = null;
		try {
			if (isStartDate) {
				formatedDate = date;
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(formatedDate);
				calendar.set(Calendar.MILLISECOND, 0);
				calendar.set(Calendar.SECOND, 0);
				calendar.set(Calendar.MINUTE, 0);
				calendar.set(Calendar.HOUR_OF_DAY, 0);
				calendar.add(Calendar.DATE, -1);
				formatedDate = calendar.getTime();
			} else {
				formatedDate = date;
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(formatedDate);
				calendar.set(Calendar.MILLISECOND, 0);
				calendar.set(Calendar.SECOND, 59);
				calendar.set(Calendar.MINUTE, 59);
				calendar.set(Calendar.HOUR_OF_DAY, 23);
				formatedDate = calendar.getTime();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return formatedDate;
	}

	public static Date getDate(Date date, boolean isStartDate) {
		logger.info("DateHelper class in call getDate method ");
		Date formatedDate = null;
		try {
			if (isStartDate) {
				formatedDate = date;
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(formatedDate);
				calendar.set(Calendar.MILLISECOND, 0);
				calendar.set(Calendar.SECOND, 0);
				calendar.set(Calendar.MINUTE, 0);
				calendar.set(Calendar.HOUR_OF_DAY, 0);
				calendar.add(Calendar.DATE, -1);
				formatedDate = calendar.getTime();
			} else {
				formatedDate = date;
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(formatedDate);
				calendar.set(Calendar.MILLISECOND, 0);
				calendar.set(Calendar.SECOND, 59);
				calendar.set(Calendar.MINUTE, 59);
				calendar.set(Calendar.HOUR_OF_DAY, 23);
				formatedDate = calendar.getTime();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return formatedDate;
	}

	public static Date getAuctionDate(Date date, boolean isStartDate) {
		logger.info("DateHelper class in call getAuctionDate method ");
		Date formatedDate = null;
		try {
			if (isStartDate) {
				formatedDate = date;
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(formatedDate);
				calendar.set(Calendar.MILLISECOND, 0);
				calendar.set(Calendar.SECOND, 0);
				calendar.set(Calendar.MINUTE, 0);
				formatedDate = calendar.getTime();
			} else {
				formatedDate = date;
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(formatedDate);
				calendar.set(Calendar.MILLISECOND, 0);
				calendar.set(Calendar.SECOND, 00);
				calendar.set(Calendar.MINUTE, CommonConstants.auctionDuration);
				formatedDate = calendar.getTime();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return formatedDate;
	}

	public static Date removeMilliSecondFromDate(Date date) {
		logger.info("DateHelper class in call removeMilliSecondFromDate method ");
		Date formatedDate = null;
		try {
			formatedDate = date;
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(formatedDate);
			calendar.set(Calendar.MILLISECOND, 0);
			formatedDate = calendar.getTime();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return formatedDate;
	}

	/**
	 * This method helps to get date into specified string format.
	 * 
	 * @param date
	 *            Provide java.util.Date date as first argument.
	 * 
	 * @return it will return appropriate date staring of given date in argument.
	 * @throws ParseException
	 */
	public static String dateToString(Date date) {
		logger.info("DateHelper class in call dateToString method ");
		logger.info("DateHelper class  login time   " + date);
		dateToString.setTimeZone(java.util.TimeZone.getTimeZone(InternetTiming.getApplicationTimeZone()));
		logger.info("DateHelpercheck time zone " + InternetTiming.getApplicationTimeZone());
		String strDate = "";
		strDate = dateToString.format(date);
		;
		logger.info("DateHelper class  login time convert string ==:   " + strDate);
		return strDate;
	}

	public static String dateToStringAmPm(Date date) {
		logger.info("DateHelper class in call dateToStringAmPm method ");
		dateToStringPMAM.setTimeZone(java.util.TimeZone.getTimeZone(InternetTiming.getApplicationTimeZone()));
		String strDate = "";
		strDate = dateToStringPMAM.format(date);
		return strDate;
	}

	public static String onlydateToString(Date date) {
		logger.info("DateHelper class in call onlydateToString method ");
		onlyDateToString.setTimeZone(java.util.TimeZone.getTimeZone(InternetTiming.getApplicationTimeZone()));
		String strDate = "";
		strDate = onlyDateToString.format(date);
		return strDate;
	}

	/**
	 * This method helps to get date after added n hours.
	 * 
	 * @param date
	 *            Provide java.util.Date date as first argument.
	 * 
	 * @param int
	 *            Provide number of hours to add in date.
	 * 
	 * @return it will return appropriate date staring of given date in argument.
	 * @throws ParseException
	 */
	public static Date getDateAfterAddedHours(Date date, int hours) {
		logger.info("DateHelper class in call getDateAfterAddedHours method ");
		Calendar cal = Calendar.getInstance(); // creates calendar
		cal.setTime(date); // sets calendar time/date
		// cal.set(Calendar.SECOND, 0);
		// cal.set(Calendar.MILLISECOND, 0);
		cal.add(Calendar.HOUR_OF_DAY, hours);
		return cal.getTime();
	}

	/**
	 * This method helps to get date after added n hours.
	 * 
	 * @param date
	 *            Provide java.util.Date date as first argument.
	 * 
	 * @param int
	 *            Provide number of hours to add in date.
	 * 
	 * @return it will return appropriate date staring of given date in argument.
	 * @throws ParseException
	 */
	public static long getDateAfterAddedHoursInMiliSeconds(Date date, int hours) {
		logger.info("DateHelper class in call getDateAfterAddedHoursInMiliSeconds method ");
		Calendar cal = Calendar.getInstance(); // creates calendar
		cal.setTime(date); // sets calendar time/date
		// cal.set(Calendar.SECOND, 0);
		// cal.set(Calendar.MILLISECOND, 0);
		cal.add(Calendar.HOUR_OF_DAY, hours);
		return cal.getTimeInMillis();
	}

	public static Date getDateStringWithMonthAndYear(String date, boolean isStartDate) {
		logger.info("DateHelper class in call getDateStringWithMonthAndYear method ");
		Date formatedDate = null;
		try {
			formatedDate = monthAndYearDateFormat.parse((date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return formatedDate;
	}

	public static long getRemainingTimeToEnd(Date endDate) {
		logger.info("DateHelper class in call getRemainingTimeToEnd method ");
		dateToString.setTimeZone(java.util.TimeZone.getTimeZone(InternetTiming.getApplicationTimeZone()));
		String dateStart = dateToString.format(InternetTiming.getInternetDateTime());
		String dateStop = dateToString.format(endDate);
		Date d1 = null;
		Date d2 = null;
		long remainingTime = 0L;
		try {
			d1 = dateToString.parse(dateStart);
			d2 = dateToString.parse(dateStop);
			// in milliseconds
			long diff = d2.getTime() - d1.getTime();
			long diffSeconds = diff / 1000 % 60;
			long diffMinutes = diff / (60 * 1000) % 60;
			long diffHours = diff / (60 * 60 * 1000) % 24;
			long diffDays = diff / (24 * 60 * 60 * 1000);
			remainingTime = diffDays + diffHours + diffMinutes + diffSeconds;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return remainingTime;
	}

	public static Date incrementDateByOneDay(Date endDate) {
		logger.info("DateHelper class in call incrementDateByOneDay method ");
		Calendar c = Calendar.getInstance();
		c.setTime(endDate);
		c.add(Calendar.DATE, 1);
		return c.getTime();
	}

	public static Date decrementDateByOneDay(Date endDate) {
		logger.info("DateHelper class in call decrementDateByOneDay method ");
		Calendar c = Calendar.getInstance();
		c.setTime(endDate);
		c.add(Calendar.DATE, -1);
		return c.getTime();
	}

	/**
	 * This method helps twoDateAndTimeDifference.
	 * 
	 * @param date
	 *            Provide java.util.Date date as first argument.
	 * 
	 * 
	 * @return it will return appropriate Boolean Values .
	 *
	 */
	public static Boolean twoDateAndTimeDifference(Date otpExpireddate) {
		logger.info("DateHelper class in call twoDateAndTimeDifference method ");
		dateToString.setTimeZone(java.util.TimeZone.getTimeZone(InternetTiming.getApplicationTimeZone()));
		String dateStop = dateToString.format(InternetTiming.getInternetDateTime());
		String dateStart = dateToString.format(otpExpireddate);
		Date d1 = null;
		Date d2 = null;
		try {
			d1 = dateToString.parse(dateStart);
			d2 = dateToString.parse(dateStop);
			long diff = Math.abs(d1.getTime() - d2.getTime());
			if (diff <= 300000) {
				return true;

			} else {
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * This method helps getRemainingTimeToSms.
	 * 
	 * @param date
	 *            Provide java.util.Date date as first argument.
	 * 
	 * 
	 * @return it will return appropriate long Values .
	 *
	 */
	public static long getRemainingTimeToSms(Date otpExpireddate) {
		logger.info("DateHelper class in call getRemainingTimeToSms method ");
		dateToString.setTimeZone(java.util.TimeZone.getTimeZone(InternetTiming.getApplicationTimeZone()));
		String FinalDate = dateToString.format(otpExpireddate);
		String CurrentDate = dateToString.format(InternetTiming.getInternetDateTime());

		Date date1;
		Date date2;
		// Setting dates
		long remainingMiunte = 0l;
		// long remainingSecond = 0l;
		long miunteTime = 30;
		// long seconds = 00;

		try {
			date1 = dateToString.parse(FinalDate);
			date2 = dateToString.parse(CurrentDate);
			long diff = date2.getTime() - date1.getTime();
			// long diffSeconds = diff / 1000 % 60;
			long diffMinutes = diff / (60 * 1000) % 60;

			if (diff >= 30 * 60 * 1000) {
				return remainingMiunte = 0;
			} else {
				remainingMiunte = miunteTime - diffMinutes;
				if (remainingMiunte == 0) {
					remainingMiunte = 30;
				}
				return remainingMiunte;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return remainingMiunte;

	}

	/**
	 * This method helps getloginAndLogOutTime difference more than 45.
	 * 
	 * @param date
	 *            Provide java.util.Date date as first argument.
	 * 
	 * 
	 * @return it will return appropriate Boolean Values .
	 *
	 */
	public static Boolean getloginAndLogOutTimeDiff(Date logoutTimestamp) {
		logger.info("DateHelper class in call getloginAndLogOutTimeDiff method ");
		dateToString.setTimeZone(java.util.TimeZone.getTimeZone(InternetTiming.getApplicationTimeZone()));
		if (logoutTimestamp != null) {
			String dateStop = dateToString.format(InternetTiming.getInternetDateTime());
			logger.info("Login Time " + dateStop);
			String dateStart = dateToString.format(logoutTimestamp);
			logger.info("Logout Time " + dateStart);
			Date d1 = null;
			Date d2 = null;
			try {
				d1 = dateToString.parse(dateStart);
				d2 = dateToString.parse(dateStop);
				long diff = Math.abs(d1.getTime() - d2.getTime());
				logger.info("Logout and Login diff " + diff);
				if (diff > 45 * 60 * 1000) {
					logger.info(" loginLogoutLog more than 45 minutes Return True " + diff);
					return true;

				} else {
					logger.info("Logout and Login diff 45 minit not more then Return false " + diff);
					return false;
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			logger.info("Logout time is null return false");
			return false;
		}

		return false;

	}

	public static Date getDateBeforeHour(Date date, int hours) {
		logger.info("DateHelper class in call getDateAfterAddedHours method ");
		Calendar cal = Calendar.getInstance(); // creates calendar
		cal.setTime(date); // sets calendar time/date
		cal.add(Calendar.MINUTE, -hours);
		return cal.getTime();
	}

	public static Date decrementDateDay(Date date, Integer Day) {
		logger.info("DateHelper class in call decrementDateByOneDay method ");
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, -Day);

		return cal.getTime();
	}
	
	public static Date decrementtwoDateDay(Date date, Integer Day) {
		logger.info("DateHelper class in call decrementtwoDateDay method ");
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, -3);

		return cal.getTime();
	}

	public static void main(String[] args) {
		
	}

}