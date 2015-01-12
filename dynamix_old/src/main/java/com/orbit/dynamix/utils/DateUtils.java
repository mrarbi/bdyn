package com.orbit.dynamix.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;



/**
 * 
 * @author m.arbi
 * 
 */
public class DateUtils implements Cloneable {

	protected Calendar calendar_;

	private boolean initializeHours = false;

	/**
	 * renvoi la date en format string. pattern : "dd/MM/yyyy"
	 * 
	 * @param date
	 * @return
	 */
	public static String getStringFromDate(Date date) {
		if (null == date) {
			return null;
		}
		SimpleDateFormat simpleFormat = new SimpleDateFormat(Constants.FORMAT_DATE_SIMPLE);
		return simpleFormat.format(date);
	}

	/**
	 * Initialisation de l'instance du calendar.
	 * 
	 * @param year
	 *            Year of new day.
	 * @param month
	 *            Month of new day.
	 * @param dayOfMonth
	 *            Day of month of new day.
	 */
	private void initialize(int year, int month, int dayOfMonth) {
		calendar_ = Calendar.getInstance();
		calendar_.setLenient(true);
		calendar_.setFirstDayOfWeek(Calendar.MONDAY);
		calendar_.setTimeZone(TimeZone.getTimeZone("GMT"));
		set(year, month, dayOfMonth);
	}

	/**
	 * Set date of this day. The day is lenient meaning that illegal day
	 * parameters can be specified and results in a recomputed day with legal
	 * month/day values.
	 * 
	 * @param year
	 *            Year of this day.
	 * @param month
	 *            Month of this day (0-11).
	 * @param dayOfMonth
	 *            Day of month of this day (1-31).
	 */
	public void set(int year, int month, int dayOfMonth) {
		setYear(year);
		setMonth(month);
		setDayOfMonth(dayOfMonth);
		if (this.initializeHours) {
			setMinute(10);
			setHourOfDay(10);
			setSeconds(10);
			setMillSeconds(10);
		}
	}

	/**
	 * Initialisation de l'instance du calendar.
	 * 
	 * @param year
	 *            Year of new day.
	 * @param month
	 *            Month of new day.
	 * @param dayOfMonth
	 *            Day of month of new day.
	 */
	private void initialize(int year, int month, int dayOfMonth, Locale locale) {
		if (null != locale) {
			calendar_ = Calendar.getInstance(locale);
		} else {
			calendar_ = Calendar.getInstance();
		}
		calendar_.setLenient(true);
		calendar_.setFirstDayOfWeek(Calendar.MONDAY);
		calendar_.setTimeZone(TimeZone.getTimeZone("GMT"));
		set(year, month, dayOfMonth);
	}

	/**
	 * Create a new day. The day is lenient meaning that illegal day parameters
	 * can be specified and results in a recomputed day with legal month/day
	 * values.
	 * 
	 * @param year
	 *            Year of new day.
	 * @param month
	 *            Month of new day (0-11)
	 * @param dayOfMonth
	 *            Day of month of new day (1-31)
	 */
	public DateUtils(int year, int month, int dayOfMonth) {
		initialize(year, month, dayOfMonth);
	}

	/**
	 * Create a new day, specifying the year and the day of year. The day is
	 * lenient meaning that illegal day parameters can be specified and results
	 * in a recomputed day with legal month/day values.
	 * 
	 * @param year
	 *            Year of new day.
	 * @param dayOfYear
	 *            1=January 1, etc.
	 */
	public DateUtils(int year, int dayOfYear) {
		initialize(year, Calendar.JANUARY, 1);
		calendar_.set(Calendar.DAY_OF_YEAR, dayOfYear);
	}

	/**
	 * Create a new day representing the day of creation (according to the
	 * setting of the current machine).
	 */
	public DateUtils() {
		// Now (in the currenct locale of the client machine)
		Calendar calendar = Calendar.getInstance();

		// Prune time part
		initialize(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
	}

	public DateUtils(Date date, boolean initialiseHours) {
		// Create a calendar based on given date
		try {
			// @FIX -MAR- BUG 6743
			this.initializeHours = initialiseHours;
			Calendar calendar = Calendar.getInstance();
			if (date != null)
				calendar.setTime(date);
			else
				calendar.setTime(new Date());
			// Extract date values and use these only
			initialize(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	/**
	 * Create a new day based on a java.util.Calendar instance. NOTE: The time
	 * component from calendar will be pruned.
	 * 
	 * @param calendar
	 *            Calendar instance to copy.
	 */
	public DateUtils(Calendar calendar) {
		this(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
	}

	/**
	 * Create a new day based on a java.util.Date instance. NOTE: The time
	 * component from date will be pruned.
	 * 
	 * @param date
	 *            Date instance to copy.
	 */
	public DateUtils(Date date) {
		this(date, false);
	}

	/**
	 * Create a new day based on a java.util.Date instance. NOTE: The time
	 * component from date will be pruned.
	 * 
	 * @param date
	 *            Date instance to copy.
	 */
	public DateUtils(Date date, Locale locale) {
		// Create a calendar based on given date
		try {
			Calendar calendar = null;
			if (null != locale) {
				calendar = Calendar.getInstance(locale);
			} else {
				calendar = Calendar.getInstance();
			}
			if (date != null)
				calendar.setTime(date);
			else
				calendar.setTime(new Date());

			// Extract date values and use these only
			initialize(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), locale);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create a new day based on a time value. Time is milliseconds since "the
	 * Epoch" (1.1.1970). NOTE: The time component from time will be pruned.
	 * 
	 * @param time
	 *            Milliseconds since "the Epoch".
	 */
	public DateUtils(long time) {
		this(new Date(time));
	}

	/**
	 * Create a new day as a copy of the specified day.
	 * 
	 * @param day
	 *            Day to clone.
	 */
	public DateUtils(DateUtils day) {
		this(day.getYear(), day.getMonth(), day.getDayOfMonth());
	}

	/**
	 * Create a clone of this day.
	 * 
	 * @return This day cloned.
	 */
	public Object clone() {
		return new DateUtils(this);
	}

	/**
	 * A more explicit front-end to the Day() constructor which return a day
	 * object representing the day of creation.
	 * 
	 * @return A day instance representing today.
	 */
	public static DateUtils today() {
		return new DateUtils();
	}

	/**
	 * Return a Calendar instance representing the same day as this instance.
	 * For use by secondary methods requiring java.util.Calendar as input.
	 * 
	 * @return Calendar equivalent representing this day.
	 */
	public Calendar getCalendar() {
		return (Calendar) calendar_.clone();
	}

	/**
	 * Return a Date instance representing the same date as this instance. For
	 * use by secondary methods requiring java.util.Date as input.
	 * 
	 * @return Date equivalent representing this day.
	 */
	public Date getDate() {
		return getCalendar().getTime();
	}

	/**
	 * Compare this day to the specified day. If object is not of type Day a
	 * ClassCastException is thrown.
	 * 
	 * @param object
	 *            Day object to compare to.
	 * @return
	 * @see Comparable#compareTo(Object)
	 * @throws ClassCastException
	 *             If object is not of type Day.
	 */
	public int compareTo(Object object) {
		DateUtils day = (DateUtils) object;
		return calendar_.getTime().compareTo(day.calendar_.getTime());
	}

	/**
	 * Return true if this day is after the specified day.
	 * 
	 * @param date
	 *            Day to compare to.
	 * @return True if this is after day, false otherwise.
	 */
	public boolean isAfter(DateUtils day) {
		return calendar_.after(day.calendar_);
	}

	public boolean isAfterOrEqual(DateUtils day) {
		return calendar_.after(day.calendar_) || calendar_.equals(day.calendar_);
	}

	/**
	 * Return true if this day is before the specified day.
	 * 
	 * @param date
	 *            Day to compare to.
	 * @return True if this is before day, false otherwise.
	 */
	public boolean isBefore(DateUtils day) {
		return calendar_.before(day.calendar_);
	}


	public boolean isBeforeOrEqual(DateUtils day) {
		return calendar_.before(day.calendar_) || calendar_.equals(day.calendar_);
	}

	/**
	 * Overload required as default definition of equals() has changed.
	 * 
	 * @return A hash code value for this object.
	 */
	public int hashCode() {
		return calendar_.hashCode();
	}

	/**
	 * Return year of this day.
	 * 
	 * @return Year of this day.
	 */
	public int getYear() {
		return calendar_.get(Calendar.YEAR);
	}
	
	/**
	 * Return year of this day.
	 * 
	 * @return Year of this day.
	 */
	public String getYearString() {
		return String.valueOf(calendar_.get(Calendar.YEAR));
	}
	
	/**
	 * 
	 * @return
	 */
	public int getWeek() {
		return calendar_.get(Calendar.WEEK_OF_YEAR);
	}

	/**
	 * Set year of this day.
	 * 
	 * @param year
	 *            New year of this day.
	 */
	public void setYear(int year) {
		calendar_.set(Calendar.YEAR, year);
	}

	/**
	 * 
	 * @param year
	 */
	public void setWeek(int week) {
		calendar_.set(Calendar.WEEK_OF_YEAR, week);
	}

	/**
	 * Set Hour of this day.
	 * 
	 * @param hour
	 */
	public void setHour(int hour) {
		calendar_.set(Calendar.HOUR, hour);
	}

	/**
	 * Get Hour of this day.
	 * 
	 */
	public int getHour() {
		return calendar_.get(Calendar.HOUR);
	}

	/**
	 * Set Hour of this day.
	 * 
	 * @param hour
	 */
	public void setHourOfDay(int hour) {
		calendar_.set(Calendar.HOUR_OF_DAY, hour);
	}

	/**
	 * Get Hour of this day.
	 * 
	 */
	public int getHourOfDay() {
		return calendar_.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * Set Minute of this day.
	 * 
	 * @param minute
	 */
	public void setMinute(int minute) {
		calendar_.set(Calendar.MINUTE, minute);
	}

	/**
	 * Get Minute of this day.
	 * 
	 */
	public int getMinute() {
		return calendar_.get(Calendar.MINUTE);
	}

	/**
	 * Set Minute of this day.
	 * 
	 * @param minute
	 */
	public void setSeconds(int minute) {
		calendar_.set(Calendar.SECOND, minute);
	}

	/**
	 * Get Minute of this day.
	 * 
	 */
	public int getSeconds() {
		return calendar_.get(Calendar.SECOND);
	}

	/**
	 * Set Minute of this day.
	 * 
	 * @param minute
	 */
	public void setMillSeconds(int minute) {
		calendar_.set(Calendar.MILLISECOND, minute);
	}

	/**
	 * Get Minute of this day.
	 * 
	 */
	public int getMillSeconds() {
		return calendar_.get(Calendar.MILLISECOND);
	}

	/**
	 * Return month of this day. The result must be compared to
	 * Calendar.JANUARY, Calendar.FEBRUARY, etc.
	 * 
	 * @return Month of this day.
	 */
	public int getMonth() {
		return calendar_.get(Calendar.MONTH);
	}

	/**
	 * Return the 1-based month number of the month of this day. 1 = January, 2
	 * = February and so on.
	 * 
	 * @return Month number of this month
	 */
	public int getMonthNo() {
		// It is tempting to return getMonth() + 1 but this is conceptually
		// wrong, as Calendar month is an enumeration and the values are tags
		// only and can be anything.
		switch (getMonth()) {
		case Calendar.JANUARY:
			return 1;
		case Calendar.FEBRUARY:
			return 2;
		case Calendar.MARCH:
			return 3;
		case Calendar.APRIL:
			return 4;
		case Calendar.MAY:
			return 5;
		case Calendar.JUNE:
			return 6;
		case Calendar.JULY:
			return 7;
		case Calendar.AUGUST:
			return 8;
		case Calendar.SEPTEMBER:
			return 9;
		case Calendar.OCTOBER:
			return 10;
		case Calendar.NOVEMBER:
			return 11;
		case Calendar.DECEMBER:
			return 12;
		}

		// This will never happen
		return 0;
	}

	/**
	 * Return the 1-based month number of the month of this day. 1 = January, 2
	 * = February and so on.
	 * 
	 * @return Month number of this month
	 */
	public String getMonthString() {
		// It is tempting to return getMonth() + 1 but this is conceptually
		// wrong, as Calendar month is an enumeration and the values are tags
		// only and can be anything.
		switch (getMonth()) {
		case Calendar.JANUARY:
			return "01";
		case Calendar.FEBRUARY:
			return "02";
		case Calendar.MARCH:
			return "03";
		case Calendar.APRIL:
			return "04";
		case Calendar.MAY:
			return "05";
		case Calendar.JUNE:
			return "06";
		case Calendar.JULY:
			return "07";
		case Calendar.AUGUST:
			return "08";
		case Calendar.SEPTEMBER:
			return "09";
		case Calendar.OCTOBER:
			return "10";
		case Calendar.NOVEMBER:
			return "11";
		case Calendar.DECEMBER:
			return "12";
		}

		// This will never happen
		return "0";
	}

	/**
	 * Set month of this day. January = 0, February = 1, etc. Illegal month
	 * values will result in a recomputation of year and a resetting of month to
	 * a valid value. I.e. setMonth(20), will add 1 year to day and set month to
	 * 8.
	 * 
	 * @param month
	 *            New month of this day.
	 */
	public void setMonth(int month) {
		calendar_.set(Calendar.MONTH, month);
	}

	/**
	 * Return day of month of this day. NOTE: First day of month is 1 (not 0).
	 * 
	 * @return Day of month of this day.
	 */
	public int getDayOfMonth() {
		return calendar_.get(Calendar.DAY_OF_MONTH);
	}

	public String getDayOfMonthString() {
		int day = calendar_.get(Calendar.DAY_OF_MONTH);
		if (day < 10)
			return "0" + day;
		return "" + day;
	}

	/**
	 * Set day of month of this date. 1=1st 2=2nd, etc. Illegal day values will
	 * result in a recomputation of month/year and a resetting of day to a valid
	 * value. I.e. setDayOfMonth(33), will add 1 month to date and set day to 5,
	 * 4, 3 or 2 depending on month/year.
	 * 
	 * @param dayOfMonth
	 *            New day of month of this day.
	 */
	public void setDayOfMonth(int dayOfMonth) {
		calendar_.set(Calendar.DAY_OF_MONTH, dayOfMonth);
	}

	/**
	 * Return the day number of year this day represents. January 1 = 1 and so
	 * on.
	 * 
	 * @return day number of year.
	 */
	public int getDayOfYear() {
		return calendar_.get(Calendar.DAY_OF_YEAR);
	}

	/**
	 * Return the day of week of this day. NOTE: Must be compared to
	 * Calendar.MONDAY, TUSEDAY etc.
	 * 
	 * @return Day of week of this day.
	 */
	public int getDayOfWeek() {
		return calendar_.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * Return the day number of week of this day, where Monday=1, Tuesday=2, ...
	 * Sunday=7.
	 * 
	 * @return Day number of week of this day.
	 */
	public int getDayNumberOfWeek() {
		return getDayOfWeek() == Calendar.SUNDAY ? 7 : getDayOfWeek() - Calendar.SUNDAY;
	}

	/**
	 * Return the week number of year, this day belongs to. 1st=1 and so on.
	 * 
	 * @return Week number of year of this day.
	 */
	public int getWeekOfYear() {
		return calendar_.get(Calendar.WEEK_OF_YEAR);
	}

	/**
	 * 
	 * @param WEEK_OF_YEAR
	 */
	public void setWeekOfYear(int WEEK_OF_YEAR) {
		calendar_.set(Calendar.WEEK_OF_YEAR, WEEK_OF_YEAR);
	}

	/**
	 * Add number of days without week end
	 * 
	 * @param nDays
	 */
	public void addDaysWithoutWeekEnd(int nDays) {
		boolean isWeekAnd = false;
		for (int i = 1; i <= nDays; i++) {
			calendar_.add(Calendar.DAY_OF_MONTH, 1);
			int dayOfWeek = calendar_.get(Calendar.DAY_OF_WEEK);
			isWeekAnd = (dayOfWeek == Calendar.SATURDAY) || (dayOfWeek == Calendar.SUNDAY);
			if (!isWeekAnd) {
				nDays = nDays - 1;
				// calendar_.add(Calendar.DAY_OF_MONTH, 1);
			}
		}
	}

	/**
	 * Add a number of days to this day. Subtracting a number of days can be
	 * done by a negative argument to addDays() or calling subtractDays()
	 * explicitly.
	 * 
	 * @param nDays
	 *            Number of days to add.
	 */
	public void addHours(int nHours) {
		calendar_.add(Calendar.HOUR_OF_DAY, nHours);
	}
	
	public static void main(String[] args) {
		DateUtils dateUtils = new DateUtils();
		
		dateUtils.addHours(24);
		
		System.out.println(dateUtils.getDate());
		
	}
	
	/**
	 * Add a number of days to this day. Subtracting a number of days can be
	 * done by a negative argument to addDays() or calling subtractDays()
	 * explicitly.
	 * 
	 * @param nDays
	 *            Number of days to add.
	 */
	public void addDays(int nDays) {
		calendar_.add(Calendar.DAY_OF_MONTH, nDays);
	}

	/**
	 * 
	 * @param nWeeks
	 */
	public void addWeeksOfMonth(int nWeeks) {
		calendar_.add(Calendar.WEEK_OF_MONTH, nWeeks);
	}

	/**
	 * 
	 * @param nWeeks
	 */
	public void addWeeks(int nWeeks) {
		calendar_.add(Calendar.WEEK_OF_YEAR, nWeeks);
	}

	/**
	 * Subtract a number of days from this day
	 * 
	 * @param nDays
	 *            Number of days to subtract.
	 */
	public void subtractDays(int nDays) {
		addDays(-nDays);
	}

	/**
	 * Add a number of months to this day. The actual number of days added
	 * depends on the staring day. Subtracting a number of months can be done by
	 * a negative argument to addMonths() or calling subtactMonths() explicitly.
	 * NOTE: addMonth(n) m times will in general give a different result than
	 * addMonth(m*n). Add 1 month to January 31, 2005 will give February 28,
	 * 2005.
	 * 
	 * @param nMonths
	 *            Number of months to add.
	 */
	public void addMonths(int nMonths) {
		calendar_.add(Calendar.MONTH, nMonths);
	}

	/**
	 * Subtract a number of months from this day
	 * 
	 * @see #addMonths(int).
	 * 
	 * @param nDays
	 *            Number of days to subtract.
	 */
	public void subtractMonths(int nMonths) {
		addMonths(-nMonths);
	}

	/**
	 * Add a number of years to this day. The actual number of days added
	 * depends on the starting day. Subtracting a number of years can be done by
	 * a negative argument to addYears() or calling subtractYears explicitly.
	 * 
	 * @param nYears
	 *            Number of years to add.
	 */
	public DateUtils addYears(int nYears) {
		calendar_.add(Calendar.YEAR, nYears);
		return this;
	}

	/**
	 * Subtract a number of years from this day
	 * 
	 * @see #addYears(int).
	 * 
	 * @param nYears
	 *            Number of years to subtract.
	 */
	public DateUtils subtractYears(int nYears) {
		addYears(-nYears);
		return this;
	}

	/**
	 * Return the number of days in the year of this day.
	 * 
	 * @return Number of days in this year.
	 */
	public int getDaysInYear() {
		return calendar_.getActualMaximum(Calendar.DAY_OF_YEAR);
	}

	/**
	 * Return true if the year of this day is a leap year.
	 * 
	 * @return True if this year is a leap year, false otherwise.
	 */
	public boolean isLeapYear() {
		return getDaysInYear() == calendar_.getMaximum(Calendar.DAY_OF_YEAR);
	}

	/**
	 * Return true if the specified year is a leap year.
	 * 
	 * @param year
	 *            Year to check.
	 * @return True if specified year is leap year, false otherwise.
	 */
	public static boolean isLeapYear(int year) {
		return (new DateUtils(year, Calendar.JANUARY, 1)).isLeapYear();
	}

	/**
	 * Return the number of days in the month of this day.
	 * 
	 * @return Number of days in this month.
	 */
	public int getDaysInMonth() {
		return calendar_.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * Get default locale name of this day ("Monday", "Tuesday", etc.
	 * 
	 * @return Name of day.
	 */
	public String getDayName() {
		switch (getDayOfWeek()) {
		case Calendar.MONDAY:
			return "Lundi";
		case Calendar.TUESDAY:
			return "Mardi";
		case Calendar.WEDNESDAY:
			return "Mercredi";
		case Calendar.THURSDAY:
			return "Jeudi";
		case Calendar.FRIDAY:
			return "Vendredi";
		case Calendar.SATURDAY:
			return "Samedi";
		case Calendar.SUNDAY:
			return "Dimanche";
		}
		// This will never happen
		return null;
	}

	/**
	 * 
	 * @param dateUtils
	 * @return
	 */
	public String getDayName(DateUtils dateUtils) {
		switch (dateUtils.getDayOfWeek()) {
		case Calendar.MONDAY:
			return "Lundi";
		case Calendar.TUESDAY:
			return "Mardi";
		case Calendar.WEDNESDAY:
			return "Mercredi";
		case Calendar.THURSDAY:
			return "Jeudi";
		case Calendar.FRIDAY:
			return "Vendredi";
		case Calendar.SATURDAY:
			return "Samedi";
		case Calendar.SUNDAY:
			return "Dimanche";
		}
		// This will never happen
		return null;
	}

	/**
	 * 
	 * @param DAY_OF_WEEK
	 */
	public void setDay(String DAY_OF_WEEK) {
		if (DAY_OF_WEEK.equals("LU")) {
			calendar_.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		} else if (DAY_OF_WEEK.equals("MA")) {
			calendar_.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
		} else if (DAY_OF_WEEK.equals("ME")) {
			calendar_.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
		} else if (DAY_OF_WEEK.equals("JE")) {
			calendar_.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
		} else if (DAY_OF_WEEK.equals("VE")) {
			calendar_.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
		} else if (DAY_OF_WEEK.equals("SA")) {
			calendar_.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
		} else if (DAY_OF_WEEK.equals("DI")) {
			calendar_.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		}
	}

	/**
	 * 
	 * @param day
	 * @return
	 */
	public Map<Integer, Integer> getNumberOfDaysInMonth() {
		Map<Integer, Integer> mapOfDays = new HashMap<Integer, Integer>();
		int numberOfDaysInMonth = this.getDaysInMonth();
		mapOfDays.put(Calendar.SUNDAY, Integer.valueOf(0));
		mapOfDays.put(Calendar.MONDAY, Integer.valueOf(0));
		mapOfDays.put(Calendar.TUESDAY, Integer.valueOf(0));
		mapOfDays.put(Calendar.WEDNESDAY, Integer.valueOf(0));
		mapOfDays.put(Calendar.THURSDAY, Integer.valueOf(0));
		mapOfDays.put(Calendar.FRIDAY, Integer.valueOf(0));
		mapOfDays.put(Calendar.SATURDAY, Integer.valueOf(0));

		for (int i = 0; i < numberOfDaysInMonth; i++) {
			int nbreJour = 0;
			switch (getDayNumberOfWeek()) {
			case 7:
				nbreJour = ((Integer) mapOfDays.get(7)).intValue();
				nbreJour++;
				mapOfDays.put(7, nbreJour);
				break;
			case 1:
				nbreJour = ((Integer) mapOfDays.get(1)).intValue();
				nbreJour++;
				mapOfDays.put(1, nbreJour);
				break;
			case 2:
				nbreJour = ((Integer) mapOfDays.get(2)).intValue();
				nbreJour++;
				mapOfDays.put(2, nbreJour);
				break;
			case 3:
				nbreJour = ((Integer) mapOfDays.get(3)).intValue();
				nbreJour++;
				mapOfDays.put(3, nbreJour);
				break;
			case 4:
				nbreJour = ((Integer) mapOfDays.get(4)).intValue();
				nbreJour++;
				mapOfDays.put(4, nbreJour);
				break;
			case 5:
				nbreJour = ((Integer) mapOfDays.get(5)).intValue();
				nbreJour++;
				mapOfDays.put(5, nbreJour);
				break;
			case 6:
				nbreJour = ((Integer) mapOfDays.get(6)).intValue();
				nbreJour++;
				mapOfDays.put(6, nbreJour);
				break;
			default:
				break;
			}
			this.addDays(1);
		}
		return mapOfDays;
	}

	/**
	 * Return number of days between two days. The method always returns a
	 * positive number of days.
	 * 
	 * @param date
	 *            The day to compare to.
	 * @return Number of days between this and day.
	 */
	public int daysBetween(DateUtils day) {
		long millisBetween = calendar_.getTime().getTime() - day.calendar_.getTime().getTime();
		return (int) Math.round((millisBetween / (double) (1000 * 60 * 60 * 24)));
	}

	/**
	 * Return number of munites between two days. The method always returns a
	 * positive number of munites.
	 * 
	 * @param date
	 *            The day to compare to.
	 * @return Number of munites between this and day.
	 */
	public int munitesBetween(DateUtils day) {
		long millisBetween = Math.abs(calendar_.getTime().getTime() - day.calendar_.getTime().getTime());
		return (int) (millisBetween / (1000 * 60));
	}

	/**
	 * Find the n'th xxxxday of s specified month (for instance find 1st sunday
	 * of May 2006; findNthOfMonth (1, Calendar.SUNDAY, Calendar.MAY, 2006);
	 * Return null if the specified day doesn't exists.
	 * 
	 * @param n
	 *            Nth day to look for.
	 * @param dayOfWeek
	 *            Day to look for (Calendar.XXXDAY).
	 * @param month
	 *            Month to check (Calendar.XXX).
	 * @param year
	 *            Year to check.
	 * @return Required Day (or null if non-existent)
	 * @throws ArrayIndexOutOfBoundsException
	 *             if dyaOfWeek parameter doesn't represent a valid day.
	 */
	public static DateUtils getNthOfMonth(int n, int dayOfWeek, int month, int year) throws ArrayIndexOutOfBoundsException {
		// Validate the dayOfWeek argument
		if (dayOfWeek < 0 || dayOfWeek > 6)
			throw new ArrayIndexOutOfBoundsException(dayOfWeek);

		DateUtils first = new DateUtils(year, month, 1);

		int offset = dayOfWeek - first.getDayOfWeek();
		if (offset < 0)
			offset = 7 + offset;

		int dayNo = (n - 1) * 7 + offset + 1;

		return dayNo > first.getDaysInMonth() ? null : new DateUtils(year, month, dayNo);
	}

	/**
	 * Find the first of a specific day in a given month, for instance first
	 * Tuesday of May: getFirstOfMonth (Calendar.TUESDAY, Calendar.MAY, 2005);
	 * 
	 * @param dayOfWeek
	 *            Weekday to get.
	 * @param month
	 *            Month of day to get.
	 * @param year
	 *            Year of day to get.
	 * @return The requested day.
	 */
	public static DateUtils getFirstOfMonth(int dayOfWeek, int month, int year) {
		return DateUtils.getNthOfMonth(1, dayOfWeek, month, year);
	}

	/**
	 * Find the last of a specific day in a given month, for instance last
	 * Tuesday of May: getLastOfMonth (Calendar.TUESDAY, Calendar.MAY, 2005);
	 * 
	 * @param dayOfWeek
	 *            Weekday to get.
	 * @param month
	 *            Month of day to get.
	 * @param year
	 *            Year of day to get.
	 * @return The requested day.
	 */
	public static DateUtils getLastOfMonth(int dayOfWeek, int month, int year) {
		DateUtils day = DateUtils.getNthOfMonth(5, dayOfWeek, month, year);
		return day != null ? day : DateUtils.getNthOfMonth(4, dayOfWeek, month, year);
	}

	/**
	 * changer la date existante au premier jour de la semaine d'une ann�e (avec
	 * comme premier jour de la semaine est le lundi)
	 * 
	 * @param week
	 * @param year
	 */
	public void setWeekForFirstDayOfWeek(int week, int year) {
		calendar_.set(Calendar.YEAR, year);
		calendar_.set(Calendar.WEEK_OF_YEAR, week);
		calendar_.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
	}

	/**
	 * Return a scratch string representation of this day. Used for debugging
	 * only. The format of the day is dd/mm-yyyy
	 * 
	 * @return A string representation of this day.
	 */
	public String toString() {
		StringBuffer string = new StringBuffer();
		if (getDayOfMonth() < 10)
			string.append('0');
		string.append(getDayOfMonth());
		if (getMonth() < 9)
			string.append('0');
		string.append(getMonth() + 1);
		string.append(getYear());

		return string.toString();
	}

	/**
	 * 
	 * @param week
	 * @param year
	 */
	public Date getDateByWeekAndYear(int week, int year) {
		calendar_.set(Calendar.YEAR, year);
		calendar_.set(Calendar.WEEK_OF_YEAR, week);
		return calendar_.getTime();
	}

	/**
	 * Retourne la date d'hier
	 * 
	 * @return
	 */
	public Date getYesterDayDate() {
		calendar_.add(Calendar.DAY_OF_MONTH, -1);
		return calendar_.getTime();
	}

	/**
	 * 
	 * @param month
	 * @return
	 */
	public Integer lastDayofMonth(Integer month, Integer year) {
		if (month == 1)
			return 31;
		if (month == 2) {
			Calendar cal = new GregorianCalendar(year, month - 1, 01);
			return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		}
		if (month == 3)
			return 31;
		if (month == 4)
			return 30;
		if (month == 5)
			return 31;
		if (month == 6)
			return 30;
		if (month == 7)
			return 31;
		if (month == 8)
			return 31;
		if (month == 9)
			return 30;
		if (month == 10)
			return 31;
		if (month == 11)
			return 30;
		if (month == 12)
			return 31;
		return null;
	}

	/**
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 * @throws Exception
	 */
	public int nbOfMonthsBetweenTwoDates(Date date1, Date date2) throws Exception {
		GregorianCalendar gc1 = new GregorianCalendar();
		gc1.setTime(date1);
		GregorianCalendar gc2 = new GregorianCalendar();
		gc2.setTime(date2);
		int gap = 0;
		gc1.add(GregorianCalendar.MONTH, 1);
		while (gc1.compareTo(gc2) <= 0) {
			gap++;
			gc1.add(GregorianCalendar.MONTH, 1);
		}
		return gap;
	}

	/**
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 * @throws Exception
	 */
	public int nbOfDaysBetweenTwoDates(Date date1, Date date2) throws Exception {
		GregorianCalendar gc1 = new GregorianCalendar();
		gc1.setTime(date1);
		GregorianCalendar gc2 = new GregorianCalendar();
		gc2.setTime(date2);
		int gap = 0;
		gc1.add(GregorianCalendar.DAY_OF_MONTH, 1);
		while (gc1.compareTo(gc2) <= 0) {
			gap++;
			gc1.add(GregorianCalendar.DAY_OF_MONTH, 1);
		}
		return gap;
	}

}
