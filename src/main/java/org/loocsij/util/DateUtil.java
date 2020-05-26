package org.loocsij.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import java.util.Hashtable;

import org.loocsij.logger.*;

import org.apache.log4j.Logger;

/**
 * Utility for Date instance processing. Provide current date's hour, minute,
 * second, and milliseconds. Get current date's string expression. Get escaped
 * time relative to specified time stamp.
 * 
 * @author wengm
 * @since 2007-11-28
 */
public class DateUtil {
	private static Logger log = Log.getLogger(DateUtil.class);

	private static Hashtable simpleDateFormats = new Hashtable();

	/**
	 * Calculate escaped time from specified time to now and return it with
	 * String pattern. If the escaped time is not longer than one day, it will
	 * be displayed as "[Escaped Time] - HH:mm:ss.SSS", else "[Escaped Time] -
	 * yyyy.MM.dd-HH:mm:ss.SSS".
	 * 
	 * @return String - String pattern of escaped time.
	 * @since 2008-11-1
	 * @author wengm
	 */
	public static String elapsedTime(long start) {
		long current = new Date().getTime();
		long escapedMilliseconds = current - start;
		log.info(getSimpleDateFormat("yyyy.MM.dd-HH:mm:ss.SSS").format(new Date(0)));
		log.info(getSimpleDateFormat("yyyy.MM.dd-HH:mm:ss.SSS").format(new Date(escapedMilliseconds-new Date(0).getTime())));
		if (escapedMilliseconds <= 0) {
			return escapedMilliseconds == 0 ? "[Escaped Time] - 0 seconds"
					: "Escaped time? Maybe you specified one future time, didn't you?";
		}
		long milliseconds = escapedMilliseconds % 1000;
		long escapedSeconds = (escapedMilliseconds - milliseconds) / 1000;
		long seconds = escapedSeconds % 60;
		long escapedMinutes = (escapedSeconds - seconds) / 60;
		long minute = escapedMinutes % 60;
		long escapedHours = (escapedMinutes - minute) / 60;
		long hour = escapedHours % 24;
		long escapedDays = (escapedHours - hour) / 24;
		long day = escapedDays % 30;
		long escapedMonths = (escapedDays - day) / 30;
		long month = escapedMonths % 12;
		long escapedYears = (escapedMonths - month) / 12;
		String strMilliseconds = null;
		if (milliseconds > 99) {
			strMilliseconds = String.valueOf(milliseconds);
		} else if (milliseconds > 9) {
			strMilliseconds = "0" + milliseconds;
		} else {
			strMilliseconds = "00" + milliseconds;
		}
		if (escapedHours < 24) {
			return "[Escaped Time] - "
					+ (hour == 0 ? "00" : (hour > 9) ? String.valueOf(hour)
							: "0" + String.valueOf(hour))
					+ ":"
					+ (minute == 0 ? "00" : (minute > 9) ? String
							.valueOf(minute) : "0" + String.valueOf(minute))
					+ ":"
					+ (seconds == 0 ? "00" : (seconds > 9) ? String
							.valueOf(seconds) : "0" + String.valueOf(seconds))
					+ "." + strMilliseconds;
		}
		String strEscapedYears = null;
		if (escapedYears >= 1000) {
			strEscapedYears = String.valueOf(escapedYears);
		} else if (escapedYears > 99) {
			strEscapedYears = "0" + escapedYears;
		} else if (escapedYears > 9) {
			strEscapedYears = "00" + escapedYears;
		} else {
			strEscapedYears = "000" + escapedYears;
		}
		return "[Escaped Time] - "
				+ strEscapedYears
				+ "."
				+ (month == 0 ? "00" : month > 9 ? String.valueOf(month) : "0"
						+ String.valueOf(month))
				+ "."
				+ (day == 0 ? "00" : (day > 9) ? String.valueOf(day) : "0"
						+ String.valueOf(day))
				+ "-"
				+ (hour == 0 ? "00" : (hour > 9) ? String.valueOf(hour) : "0"
						+ String.valueOf(hour))
				+ ":"
				+ (minute == 0 ? "00" : (minute > 9) ? String.valueOf(minute)
						: "0" + String.valueOf(minute))
				+ ":"
				+ (seconds == 0 ? "00" : (seconds > 9) ? String
						.valueOf(seconds) : "0" + String.valueOf(seconds))
				+ "." + strMilliseconds;
	}

	/**
	 * judge if current year is leap year
	 * 
	 * @param year -
	 *            year number
	 * @return true, if current year is leap year;false, else.
	 */
	public static boolean isLeapYear(int year) {
		boolean leap = false;
		if ((year % 4 == 0) && ((year % 100) != 0)) {
			leap = true;
		} else if (year % 400 == 0) {
			leap = true;
		}
		return leap;
	}

	/**
	 * Get instance of SimpleDateFormat. If the instance has not been created
	 * before, then create it and put it into container; else, get it from the
	 * container directly.
	 * 
	 * @return SimpleDateFormat
	 * @since 2008-11-1
	 * @author wengm
	 */
	public static SimpleDateFormat getSimpleDateFormat(String sPattern) {
		if (DateUtil.simpleDateFormats.get(sPattern) != null) {
			log.debug("Add new instance of SimpleDateFormat for pattern"
					+ sPattern);
			return (SimpleDateFormat) DateUtil.simpleDateFormats.get(sPattern);
		}
		SimpleDateFormat format = new SimpleDateFormat(sPattern);
		DateUtil.simpleDateFormats.put(sPattern, format);
		return format;
	}

	/**
	 * Calculate how many day in the year or month of specified date. If the
	 * year is leap year, there are 366 days in the year and 29 day in the
	 * February;else,365 and 28 days respectively. If the month is BIG MONTH,
	 * there are 31 days in the month, else if SMALL MONTH, 39 days instead.<br>
	 * <ul>
	 * BIG MONTH
	 * <li>January</li>
	 * <li>March</li>
	 * <li>May</li>
	 * <li>July</li>
	 * <li>August</li>
	 * <li>October</li>
	 * <li>December</li>
	 * </ul>
	 * <ul>
	 * SMALL MONTH
	 * <li>April</li>
	 * <li>June</li>
	 * <li>September</li>
	 * <li>November</li>
	 * </ul>
	 * 
	 * @param millis:
	 *            Date.getTime() value.
	 * @param type:
	 *            Calendar.YEAR or Calendar.MONTH
	 * @return int - days number of the year or month of the date.
	 */
	public static int daysNumber(long millis, int type) {
		if (type != Calendar.YEAR && type != Calendar.MONTH) {
			throw new IllegalArgumentException(
					"Usage: org.loocsij.util.DateUtil.daysNumber($DateInstance.getTime(), Calendar.YEAR) or org.loocsij.util.DateUtil.daysNumber($DateInstance.getTime(), Calendar.YEAR)");
		}

		int year_small = 365, year_large = 366;
		int month_small = 30, month_large = 31;
		int february_small = 28, february_large = 29;

		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(millis);

		int year = c.get(Calendar.YEAR);
		boolean isLeapYear = DateUtil.isLeapYear(year);
		if (type == Calendar.YEAR) {
			if (isLeapYear) {
				return year_large;
			} else {
				return year_small;
			}
		}

		int month = c.get(Calendar.MONTH);
		long[] m_l = { Calendar.JANUARY, Calendar.MARCH, Calendar.MAY,
				Calendar.JULY, Calendar.AUGUST, Calendar.OCTOBER,
				Calendar.DECEMBER };
		long[] m_s = { Calendar.APRIL, Calendar.JUNE, Calendar.SEPTEMBER,
				Calendar.NOVEMBER };

		int daysCount = 0;
		for (int index = 0, length = m_l.length; index < length; index++) {
			if (month == m_l[index]) {
				daysCount = month_large;
			}
		}
		for (int index = 0, length = m_s.length; index < length; index++) {
			if (month == m_s[index]) {
				daysCount = month_small;
			}
		}
		if (month == Calendar.FEBRUARY) {
			if (isLeapYear) {
				daysCount = february_large;
			} else {
				daysCount = february_small;
			}
		}

		return daysCount;
	}

	public static void main(String[] strs) throws ParseException {
		long start = new Date().getTime();
		start = getSimpleDateFormat("yyyy.MM.dd-HH:mm:ss.SSS").parse("1970.01.01-08:00:00.000").getTime();
		for(int i=0;i<10000*10000*10000*10000;i++){
		}
		log.info(elapsedTime(start));
	}
}