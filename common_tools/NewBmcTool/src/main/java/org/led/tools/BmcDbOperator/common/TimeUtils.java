package org.led.tools.BmcDbOperator.common;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;



public final class TimeUtils {
    private static final long MILLISECOND_CONVERT_UNIT = 1000L;
    private static final String TIME_ZONE_UTC = "UTC";
    private static final int SECOND_NUMBER = 1000;
    private static final long SECOND_SHIFT = 2208988800L;
    /**
     * This is a work around for a non working ConfDatetime.getCalendar().
     * 
     * Note! Only works for UTC time.
     * 
     * @param utcTime
     * @return
     */

    private static DatatypeFactory dataTypeFactory = null;

    private TimeUtils() {
    }

    public static GregorianCalendar toCalendar(Date utcTime) {

        GregorianCalendar date = new GregorianCalendar(TimeZone.getTimeZone(TIME_ZONE_UTC));
        date.setTime(utcTime);

        return date;
    }

    /**
     * Adds offsets(second) to a date
     */
    public static Date add(Date date, int... offset) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        for (int second : offset) {
            cal.add(Calendar.SECOND, second);
        }
        return cal.getTime();
    }

    /**
     * Adds offsets according to calendar unit to a date
     */
    public static Date add(int calendarUnit, Date date, int... offset) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        for (int second : offset) {
            cal.add(calendarUnit, second);
        }
        return cal.getTime();
    }

    /**
     * Calculate the difference of two date. offset - base
     */
    public static long diff(Date offset, Date base) {

        Calendar calendarOffset = Calendar.getInstance();
        // calendarOffset.clear();
        calendarOffset.setTime(offset);

        Calendar calendarBase = Calendar.getInstance();
        // calendarOffset.clear();
        calendarBase.setTime(base);

        return calendarOffset.getTimeInMillis() - calendarBase.getTimeInMillis();
    }

    /**
     * Calculate the difference in 'second' unit of two date. offset - base
     */
    public static long diffOfSecond(Date offset, Date base) {

        return diff(offset, base) / SECOND_NUMBER;
    }

    /**
     * Adds offsets to a date with field
     */
    public static Date addWithField(Date date, int field, int... offset) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        for (int unit : offset) {
            cal.add(field, unit);
        }
        return cal.getTime();
    }

    /**
     * Generates a date now.
     * 
     * @return
     */
    public static Date now() {
        return now(0);
    }

    /**
     * Generates a Date for with an optional offset.
     * 
     * @param offset
     *            additional offset to the "now"
     * @return
     */
    public static Date now(int offset) {
        GregorianCalendar now = new GregorianCalendar(TimeZone.getTimeZone(TIME_ZONE_UTC));
        now.add(GregorianCalendar.SECOND, offset);
        return now.getTime();
    }

    /**
     * Generates a Date with millisecond as 0.
     * 
     * @return
     */
    public static Date nowWithoutMillisecond() {
        GregorianCalendar now = new GregorianCalendar(TimeZone.getTimeZone(TIME_ZONE_UTC));
        now.set(GregorianCalendar.MILLISECOND, 0);
        return now.getTime();
    }

    /**
     * Generates a GregorianCalendar with the default time zone "UTC".
     * 
     * @return
     */
    public static GregorianCalendar getGregorianCalendar() {
        return getGregorianCalendar(TIME_ZONE_UTC);
    }

    /**
     * Generates a GregorianCalendar with time zone.
     * 
     * @param timeZone
     *            set the timeZone to the "gc"
     * @return
     */
    public static GregorianCalendar getGregorianCalendar(String timeZone) {
        return new GregorianCalendar(TimeZone.getTimeZone(timeZone));
    }

    /**
     * Converts a UTC ConfDatetime to XMLGregorianCalendar
     * 
     * @param dt
     * @return
     */
    public static XMLGregorianCalendar confDatetimeToXmlGregorianCalendar(Date dt) {
        GregorianCalendar c = toCalendar(dt);
        XMLGregorianCalendar greg = null;
        try {
            if (dataTypeFactory == null) {
                dataTypeFactory = DatatypeFactory.newInstance();
            }
            greg = dataTypeFactory.newXMLGregorianCalendar(c);
            greg.setMillisecond(DatatypeConstants.FIELD_UNDEFINED);
        } catch (DatatypeConfigurationException e) {
            throw new BmcRuntimeException(BmcExceptionType.FAILED_CREATE_DATA_TYPE_FACTORY, e);
        }
        return greg;
    }

    /**
     * Generates a UTC Date and Time String for with an optional offset.
     * 
     * @param offset
     *            additional offset to the "now"
     * @return
     */
    public static String getUTCNowString(int offset) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone(TIME_ZONE_UTC));
        Date now = now(offset);
        return sdf.format(now);
    }
    
    public static String getNowNumberFormatStringUntilSecond() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Date now = new Date();
        return sdf.format(now);
    }

    public static String getNowNumberFormatStringUntilMillisecond() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        Date now = new Date();
        return sdf.format(now);
    }
    
    public static long javaMilliTimeToNtpTime(long javaMilliTime) {
        return javaSecondTimeToNtpTime(javaMilliTime / MILLISECOND_CONVERT_UNIT);
    }

    public static long javaSecondTimeToNtpTime(long javaSecondTime) {
        return javaSecondTime + SECOND_SHIFT;
    }

    public static Date conventUTCTimeTolocalTime(Date date) {
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(date);
        int zoneOffset = cal.get(java.util.Calendar.ZONE_OFFSET);
        int dstOffset = cal.get(java.util.Calendar.DST_OFFSET);
        cal.add(java.util.Calendar.MILLISECOND, zoneOffset + dstOffset);
        return cal.getTime();
    }

    public static Date getUTCTimeNow() {
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone(TIME_ZONE_UTC));
        int zoneOffset = cal.get(java.util.Calendar.ZONE_OFFSET);
        int dstOffset = cal.get(java.util.Calendar.DST_OFFSET);
        cal.add(java.util.Calendar.MILLISECOND, -(zoneOffset + dstOffset));
        return cal.getTime();
    }

}
