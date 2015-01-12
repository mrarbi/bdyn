package com.orbit.dynamix.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class Utils {

	public static Double arrondirWithPrecision(String fgMe, int precision) {
		if (StringUtils.isEmpty(fgMe) || fgMe.equalsIgnoreCase("0")) {
			if (precision == 2)
				return 0.00;
			else
				return 0.000;
		}
		Double dec = Double.valueOf(0);
		if (isNumber(fgMe)) {
			Integer precisionDiv = 1;
			for (int i = 1; i <= precision; i++)
				precisionDiv *= 10;
			dec = new Double(Double.valueOf(fgMe) / Double.valueOf(precisionDiv));
		}
		return dec;
	}

	public static boolean isNumber(String s) {
		try {
			if (StringUtils.isEmpty(s))
				return false;
			@SuppressWarnings("unused")
			int i = Integer.parseInt(s);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	/**
	 * 
	 * @param d
	 * @return
	 */
	public static Double round2Dec(Double d) {
		try {
			if (d != 0.0) {
				Long a = Math.round(100 * d);
				return a.doubleValue() / 100;
			}
		} catch (Exception e) {

		}
		return 0.00;
	}

	/**
	 * get Integer from String
	 * 
	 * @author walid rahali
	 * @param str
	 * @return
	 */
	public static Integer getIntegerFromString(String str) {
		Integer i = null;
		try {
			i = Integer.valueOf(str);
		} catch (NumberFormatException e) {
			i = 0;
		} catch (Exception e) {
			i = 0;
		}
		return i;
	}

	/**
	 * 
	 * @param value
	 * @return String
	 */
	public static String getMois(Integer value) {
		return value > 9 ? "" + value : "0" + value;
	}

	/**
	 * 
	 * @param value
	 * @return String
	 */
	public static String getString(Integer value) {
		if (value != null)
			return value.intValue() != 0 ? (String) ConvertUtils.convert(value) : "";
		return null;
	}

	/**
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isEqualsDates(DateUtils date1, DateUtils date2) {
		return date1.toString().equals(date2.toString());
	}

	/**
	 * 
	 * @param value
	 * @return String
	 */
	public static String getString(Boolean value) {
		if (value != null)
			return (String) ConvertUtils.convert(value);
		return null;
	}

	/**
	 * 
	 * @param value
	 * @return String
	 */
	public static String getString(Double value) {
		if (value != null && value != 0) {
			DecimalFormat df = new DecimalFormat("0.00");
			DecimalFormatSymbols dfs = df.getDecimalFormatSymbols();
			dfs.setDecimalSeparator('.');
			df.setDecimalFormatSymbols(dfs);
			String valStr = df.format(value);
			if (StringUtils.isNotEmpty(valStr) && valStr.equals("-0.00"))
				return "0.00";
			return valStr;
		} else
			return "0.00";
	}

	/**
	 * recuperer la chaine de caracteres depuis un double arrondis a 0
	 * 
	 * @param value
	 * @param round
	 * @return
	 */
	public static String getString(Double value, boolean round) {
		if (value != null && value != 0 && round) {
			String stringValue = getString(value);
			if (!"00".equals(stringValue.split("[.]")[1])) {
				return getString(getInteger(stringValue.split("[.]")[0]) + 1) + "." + "00";
			} else {
				return stringValue;
			}
		}
		return "";
	}

	// /**
	// *
	// * @param value
	// * @return String
	// */
	// public static String getString(Character value) {
	// if (value != null)
	// return (String) ConvertUtils.convert(value);
	// return null;
	// }

	/**
	 * 
	 * @param value
	 * @return String
	 */
	public static String getString(Short value) {
		if (value != null)
			return (String) ConvertUtils.convert(value);
		return null;
	}

	/**
	 * 
	 * @param value
	 * @return String
	 */
	public static String getString(Byte value) {
		if (value != null)
			return (String) ConvertUtils.convert(value);
		return null;
	}

	/**
	 * 
	 * @param value
	 * @return String
	 */
	public static String getString(Long value) {
		if (value != null)
			return (String) ConvertUtils.convert(value);
		return null;
	}

	/**
	 * 
	 * @param value
	 * @return 0 if Null
	 */
	public static Integer getInteger(String value) {
		if (StringUtils.isNotEmpty(value) && StringUtils.isNumeric(value))
			return (Integer) ConvertUtils.convert(value, Integer.class);
		return 0;
	}
	
	/**
	 * 
	 * @param value
	 * @return 0 if Null
	 */
	public static Integer getInteger(Long value) {
		if (null != value)
			return value.intValue();
		return 0;
	}
	
	/**
	 * 
	 * @param value
	 * @return 0 if Null
	 */
	public static Integer getInteger(long value) {
		return new Long(value).intValue();
	}

	/**
	 * 
	 * @param value
	 * @return
	 */
	public static Integer getIntegerWithNull(String value) {
		if (StringUtils.isNotEmpty(value) && StringUtils.isNumeric(value))
			return (Integer) ConvertUtils.convert(value, Integer.class);
		return null;
	}

	/**
	 * 
	 * @param value
	 * @return Integer
	 */
	public static Integer getIntegerWithZero(String value) {
		try {
			if (null != value)
				return (Integer) ConvertUtils.convert(value, Integer.class);
		} catch (ClassCastException cast) {
			return 0;
		}
		return 0;
	}

	// /**
	// *
	// * @param value
	// * @return
	// */
	// public static Integer getInteger(Character value) {
	// if(null != value)
	// return (Integer) ConvertUtils.convert(getString(value), Integer.class);
	// return null;
	// }
	/**
	 * 
	 * @param value
	 * @return Long
	 */
	public static Long getLong(String value) {
		if (null != value)
			return (Long) ConvertUtils.convert(value, Long.class);
		return null;
	}

	/**
	 * 
	 * @param value
	 * @return Short
	 */
	public static Short getShort(String value) {
		if (null != value)
			return (Short) ConvertUtils.convert(value, Short.class);
		return null;
	}

	/**
	 * 
	 * @param value
	 * @return Byte
	 */
	public static Byte getByte(String value) {
		return (Byte) ConvertUtils.convert(value, Byte.class);
	}

	/**
	 * 
	 * @param value
	 * @return Double
	 */
	public static Double getDouble(String value) {
		return (Double) ConvertUtils.convert(value, Double.class);
	}

	/**
	 * 
	 * @param value
	 * @return Boolean
	 */
	public static Boolean getBoolean(String value) {
		return (Boolean) ConvertUtils.convert(value, Boolean.class);
	}

	/**
	 * 
	 * @param value
	 * @return
	 */
	public static String getString(Date value) {
		if (value == null)
			return null;
		return DateUtils.getStringFromDate(value);
	}

	/**
	 * 
	 * @param date
	 * @return
	 */
	public static String getTime(Date date) {
		DateUtils d = new DateUtils(date);
		String heure = d.getHourOfDay() > 9 ? "" + d.getHourOfDay() : "0" + d.getHourOfDay();
		String min = d.getMinute() > 9 ? "" + d.getMinute() : "0" + d.getMinute();
		String sec = d.getSeconds() > 9 ? "" + d.getSeconds() : "0" + d.getSeconds();
		return heure + ":" + min + ":" + sec;
	}

	/**
	 * 
	 * @param date
	 *            dd/MM/yyyy
	 * @return newDate "yyyy-MM-dd"
	 */
	public static String getDateFormatSQL(String date) {
		String newDate = null;
		if (date != null) {
			String day = date.substring(0, 2);
			String month = date.substring(3, 5);
			String year = date.substring(6, 10);
			newDate = year + "-" + month + "-" + day;
		}
		return newDate;
	}

	/**
	 * 
	 * @param date
	 *            "ddMMyyyy"
	 * @return newDate Constantes.DATE_PATTERN_WITHSLASH
	 */
	public static String getDateLogin(Date date) {
		DateUtils dateUtils = new DateUtils(date);
		return dateUtils.getDayOfMonthString() + dateUtils.getMonthString() + dateUtils.getYearString();
	}

	/**
	 * 
	 * @param date
	 *            "ddMMyy"
	 * @return newDate Constantes.DATE_PATTERN_WITHSLASH
	 */
	public static String change_Format_Date_2(String date) {
		String newDate = null;
		if (StringUtils.isNotEmpty(date)) {
			String day = date.substring(0, 2);
			String month = date.substring(2, 4);
			String year = date.substring(4, date.length());
			if (getInteger(year) < 50)
				year = "20" + year;
			else
				year = "19" + year;
			newDate = day + "/" + month + "/" + year;
		}
		return newDate;
	}

	/**
	 * 
	 * @param date
	 *            "yyyy-MM-dd"
	 * @return newDate Constantes.DATE_PATTERN_WITHSLASH
	 */
	public static String changeFormatDate_(String date) {
		String newDate = "";
		if (StringUtils.isNotEmpty(date)) {
			String day = date.substring(8, 10);
			String month = date.substring(5, 7);
			String year = date.substring(0, 4);
			newDate = day + "/" + month + "/" + year;
		}
		return newDate;
	}

	/**
	 * return true if collection is not null and not empty
	 * 
	 * @param collection
	 * @return
	 */
	public static final boolean isListNotEmpty(Collection collection) {
		return (null != collection && !collection.isEmpty());
	}

	/**
	 * 
	 * @return
	 */
	public static String getCurrentDate() {
		DateUtils d = new DateUtils();
		String jour = d.getDayOfMonth() > 9 ? "" + d.getDayOfMonth() : "0" + d.getDayOfMonth();
		String mois = d.getMonthString();
		return jour + "-" + mois + "-" + d.getYear() + " " + getHeure();
	}

	public static String getHeure() {
		DateUtils d = new DateUtils();
		String heure = d.getHourOfDay() > 9 ? "" + d.getHourOfDay() : "0" + d.getHourOfDay();
		String min = d.getMinute() > 9 ? "" + d.getMinute() : "0" + d.getMinute();
		String sec = d.getSeconds() > 9 ? "" + d.getSeconds() : "0" + d.getSeconds();
		return heure + "." + min + "." + sec;
	}

	/**
	 * 
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public static int getDateInt(Date date) throws Exception {
		if (null == date)
			return 0;
		DateUtils dateUtils = new DateUtils(date);
		int year = dateUtils.getYear();
		int month = dateUtils.getMonthNo();
		int day = dateUtils.getDayOfMonth();
		return 10000 * year + 100 * month + day;
	}
	
	/**
	 * 
	 * @param value
	 * @return
	 */
	public static Date getDate(String value) {
		try {
			if (StringUtils.isEmpty(value))
				return null;
			return Utils.stringToDate(value);
		} catch (ParseException e) {
			return null;
		}
	}
	
	/**
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static final Date stringToDate(String date) throws ParseException {
		try {
			if (StringUtils.isNotEmpty(date) && date.length()>=6) {
				date = date.trim();
				if (date.length() == 6) {
					StringBuffer buffer = new StringBuffer();
					buffer.append(date.substring(0, 2)).append("/").append(
							date.substring(2, 4)).append("/20").append(
							date.substring(4));
					date = buffer.toString();
				} else if (date.length() == 8) {
					StringBuffer buffer = new StringBuffer();
					buffer.append(date.substring(0, 2)).append("/").append(
							date.substring(2, 4)).append("/").append(
							date.substring(4));
					date = buffer.toString();
				}
				SimpleDateFormat simpleDate = new SimpleDateFormat(
						"dd/MM/yyyy", Locale.FRANCE);
				return simpleDate.parse(date);
			}
		} catch (Exception ex) {
		}
		return null;
	}


	public static String encodePassword(String password) {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(password);
		return encodedPassword;
	}

	public static final int stringOccur(String text, String string) {
		return regexOccur(text, Pattern.quote(string));
	}
	 
	 
	 public static final int regexOccur(String text, String regex) {
	    Matcher matcher = Pattern.compile(regex).matcher(text);
	    int occur = 0;
	    while(matcher.find()) {
	        occur ++;
	    }
	    return occur;
	}
	 
	 public static List<Integer> genererListCheckedIds(Map<Integer, String> checkboxComp) {
		Set<Integer> listKeys = checkboxComp.keySet();
		List<Integer> listIds = new ArrayList<Integer>();
		for (Integer key : listKeys) {
				listIds.add(Integer.parseInt(checkboxComp.get(key)));
		}
		return listIds;
	}
//	 public static List<Long> genererListCheckedIds(Map<Long, String> checkboxUser) {
//			Set<Long> listKeys = checkboxUser.keySet();
//			List<Long> listIds = new ArrayList<Long>();
//			for (Long key : listKeys) {
//				if (STR_TRUE.equals(checkboxUser.get(key))) {
//					listIds.add(key);
//
//				}
//			}
//			return listIds;
//		}
}
