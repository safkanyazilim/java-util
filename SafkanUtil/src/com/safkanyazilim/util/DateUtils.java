package com.safkanyazilim.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;


public class DateUtils {

	private static SimpleDateFormat millisecondDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	private static SimpleDateFormat secondDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static SimpleDateFormat noSpaceSecondDateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
	private static SimpleDateFormat dayDateFormat = new SimpleDateFormat("yyyyMMdd");

	/**
	 * Static utility method. This will take a <code>Date</code> instance, and return another date instance. 
	 * The returned instance will be a copy of the given instance, but the method will set hours, minutes, 
	 * seconds and milliseconds values to zero, effectively making it a "date" rather than a "datetime". The argument
	 * will not be modified.
	 * 
	 * @param date the <code>Date</code> instance whose time will be removed
	 * @return a new <code>Date</code> instance, referring to a "date"
	 */
    public static Date removeTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setLenient(false);
        calendar.setTime(date);
        
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    
        return calendar.getTime();
    }
    
    /**
	 * <p>
	 * Static utility method. Converts <code>dateString</code> to date, 
	 * using the given date format "dd.MM.yyyy" (eg. 17.12.2009) 
	 * </p>
	 * 
	 * @param dateString The string containing the date
	 * @return Returns a Date object containing the date in the string
	 */
    public static Date stringToDate(String dateString) {	
        return stringToDate(dateString, "dd.MM.yyyy");
    }
    
    /**
	 * <p>
	 * Static utility method. Converts <code>datestring</code> to date, 
	 * using the given date <code>format</code> 
	 * </p>
	 * 
	 * @param dateString The string containing the date
	 * @param format The format that the date is going to parsed from the <code>dateString</code>
	 * @return Returns a Date object containing the date in the string
	 */
    public static Date stringToDate(String dateString, String format) {	
        Date date = null;
        DateFormat df = new SimpleDateFormat(format);
        
        try {
    	     date = df.parse(dateString);                        
        } catch (ParseException e) {
        }

        return date;
    }
	
    /**
	 * <p>
	 * Static utility method. Converts <code>dateString</code> to date, 
	 * using the given date <code>format</code> and <code>locale</code>
	 * </p>
	 * 
	 * @param dateString The string containing the date
	 * @param format The format that the date is going to parsed from the <code>dateString</code>
	 * @param locale The locale used to interpret the locale-sensitive date format like M
	 * @return Returns a Date object containing the date in the string
	 */
    public static Date stringToDate(String dateString, String format, Locale locale) {	
        Date date = null;
        DateFormat df = new SimpleDateFormat(format, locale);
        
        try {
    	     date = df.parse(dateString);                        
        } catch (ParseException e) {
        }

        return date;
    }
	

    /**
     * Get timestamp string in the format "yyyy-MM-dd HH:mm:ss.SSS" for the
     * given Date. 
     * @param date a timestamp
     * @return the string representing the given timestamp
     */
    
    public synchronized static String getTimestampString(Date date) {
    	return millisecondDateFormat.format(date);
    }

    /**
     * Get only a date string, which is the date format required and used
     * by Lucene. DateTools of the Lucene package does strange things,
     * probably caused by locale dependency and time zones. This method
     * should act deterministically.
     * 
     * @param date The date. The time value will be ignored.
     * @return The date, as a string, in the format yyyyMMdd
     */
    
    public synchronized static String getDateString(Date date) {
    	return dayDateFormat.format(date);
    }

    public synchronized static Date getDayDate(String dateString) {
    	if (dateString == null) {
    		return null;
    	}
    	
    	Date date = null;
    	
    	try {
			date = dayDateFormat.parse(dateString);
		} catch (ParseException e) {
		}
		
		
		return date;
    }
    
    
    /**
     * Returns the current timestamp string in the format "yyyy-MM-dd HH:mm:ss.SSS"
     * @return the current timestamp string
     */
    
    public synchronized static String getCurrentTimestampString() {
    	return millisecondDateFormat.format(new Date());
    }

    /**
     * Returns the current timestamp string in the format "yyyy-MM-dd_HH:mm:ss"
     * 
     * This is intended for appending to a filename.
     * 
     * @return the current timestamp string
     */

    public synchronized static String getCurrentTimestampStringNoSpace() {
    	return noSpaceSecondDateFormat.format(new Date());
    }

    
    /**
     * This will return a Date object, given a timestamp string in the 
     * format "yyyy-MM-dd HH:mm:ss.SSS". It will return null if the given
     * timestamp string is null. If the given string is not parseable,
     * it will return null.
     * @param timestampString a timestamp string
     * @return a Date object representing the timestamp. 
     */
    
    public synchronized static Date getTimestamp(String timestampString) {
    	if (timestampString == null) {
    		return null;
    	}
    	
    	Date timestamp = null;
    	
    	try {
			timestamp = millisecondDateFormat.parse(timestampString);
		} catch (ParseException e) {
		}
		
		if (timestamp != null) {
			return timestamp;
		}
		
		try {
			timestamp = secondDateFormat.parse(timestampString);
		} catch (ParseException e) {
		}
		
		return timestamp;
    }

	public static List<String> generateDateRangeStringList(Date startDate, Date endDate) {
		List<String> dateRangeStringList = new LinkedList<String>();
		
		Calendar cal = GregorianCalendar.getInstance();
		
		startDate = removeTime(startDate);
		endDate = removeTime(endDate);
		
		cal.setTime(startDate);
		
		long timeDifference; 
		
		do {
		
			dateRangeStringList.add(getDateString(cal.getTime()));
			
			cal.add(Calendar.DATE, 1);
			
			timeDifference = endDate.getTime() - cal.getTimeInMillis();
			
		} while (timeDifference >= 0);
		
		return dateRangeStringList;
	}
}
