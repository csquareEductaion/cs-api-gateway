package com.csquare.gateway.util;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;


/**
 * Custom class for CommonUtil
 *
 * @copyright Copyright (c) CodeX. All Right Reserved.
 * @author CodeX
 */
public class CommonUtil {

    /**
     * Method to check whether given String is empty or not
     *
     * @param value - The String
     * @return true/false - The boolean
     */
    public static boolean isEmpty(String value) {

        if (null == value) {
            return true;
        }

        return value.isEmpty();
    }

    /**
     * Method to check whether given List is empty or not
     *
     * @param value - The List<Object>
     * @return true/false - The boolean
     */
    public static boolean isEmpty(List<Object> value) {

        if (null == value) {
            return true;
        }

        return value.isEmpty();
    }

    /**
     * Method to trim String value
     *
     * @param value - The List<Object>
     * @return trimmed value - The String
     */
    public static String trim(String value) {

        if (null == value) {
            return value;
        }
        return value.trim();
    }

    /**
     * Method to check if two string values are equal
     *
     * @param value1 - The String
     * @param value2 - The String
     * @return true/false - THe boolean
     */
    public static boolean equals(String value1, String value2) {

        boolean doesMatch = false;

        if (isEmpty(value1)) {
            doesMatch = isEmpty(value2);
            return doesMatch;
        }

        if (!isEmpty(value2)) {
            doesMatch = value1.equals(value2);
        }

        return doesMatch;
    }

    /**
     * Method to convert String value to boolean
     *
     * @param value - The String
     * @return true/false - THe boolean
     */
    public static boolean toBoolean(String value) {

        if (null == value) {
            return false;
        }

        if ("true".equalsIgnoreCase(value)) {
            return true;
        }
        return false;
    }

    /**
     * Method to convert String value to boolean
     *
     * @param value - The String
     * @return true/false - THe boolean
     */
    public static Double toDouble(String value) {

        double doubleValue = 0.0;
        if (null == value) {
            return doubleValue;
        }
        try {
            doubleValue = Double.parseDouble(value);
        } catch (NumberFormatException e) {
            doubleValue = 0.0;
        }
        return doubleValue;
    }

    /**
     * Method to convert String value to boolean
     *
     * @param value - The String
     * @return true/false - THe boolean
     */
    public static String formatDouble(Double value) {

        DecimalFormat df = new DecimalFormat("###,###,###,###,##0.00");
        return df.format(value);
    }

    /**
     * Method to convert String value to float
     *
     * @param value - The String
     * @return floatValue - The float
     */
    public static Float toFloat(String value) {

        Float floatValue = 0.0f;
        if (null == value) {
            return floatValue;
        }

        try {
            floatValue = Float.parseFloat(value);
        } catch (NumberFormatException e) {
            floatValue = 0.0f;
        }
        return floatValue;
    }

    /**
     * Method to convert String value to long
     *
     * @param value - The String
     * @return longValue - The long
     */
    public static Long toLong(String value) {

        Long longValue = 0l;
        if (null == value) {
            return longValue;
        }

        try {
            longValue = Long.parseLong(value);
        } catch (NumberFormatException e) {
            longValue = 0l;
        }
        return longValue;
    }

    /**
     * Method to get Current Date
     *
     * @return currentDate - The String
     */
    public static String getCurrentDate() {

        String datePattern = "dd-MM-yyyy";
        String utc = "UTC";
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();

        DateFormat sdf = new SimpleDateFormat(datePattern);
        sdf.setTimeZone(TimeZone.getTimeZone(utc));
        sdf.setLenient(false);
        return sdf.format(date);
    }

    /**
     * Method to get Current Date
     *
     * @return currentDate - The String
     */
    public static String getCurrentMonth() {

        String date = getCurrentDate();
        String[] splitDate = date.split("-");
        return splitDate[1];
    }

    /**
     * Method to get Current Date
     *
     * @return currentDate - The String
     */
    public static String getCurrentYear() {

        String date = getCurrentDate();
        String[] splitDate = date.split("-");
        return splitDate[2];
    }

    /**
     * Method to get TimeStamp based on data provided
     *
     * @param timestamp - The long
     * @return timestamp - The String
     */
    public static String getTimeStamp(long timestamp) {

        String datePattern = "dd-MM-yyyy HH:mm:ss";
        String utc = "UTC";
        Date date = new Date(timestamp);

        DateFormat sdf = new SimpleDateFormat(datePattern);
        sdf.setTimeZone(TimeZone.getTimeZone(utc));
        sdf.setLenient(false);
        return sdf.format(date);
    }

    /**
     * This method is used to get the date as string format for the given datePattern and date.
     *
     * @param dateStr - The String
     * @param fromDateFormat - The String
     * @param toDateFormat - The String
     * @return formatedDate - The String
     * @throws ParseException - The ParseException
     */
    public static String formatDate(String dateStr, String fromDateFormat, String toDateFormat) throws ParseException {

        SimpleDateFormat dt = new SimpleDateFormat(fromDateFormat);
        Date date1 = dt.parse(dateStr);

        SimpleDateFormat dt1 = new SimpleDateFormat(toDateFormat);
        String formatedDate = dt1.format(date1);
        return formatedDate;
    }

    /**
     * Method to close any Object which can be closed
     *
     * @param closeable - The Closeable
     */
    public static void close(Closeable closeable) {

        if (null == closeable) {
            return;
        }

        try {
            closeable.close();
        } catch (IOException ioe) {
            // Do Nothing
        }
    }

    /**
     * Method to flush any Object which can be flushed
     *
     * @param flushable - The Flushable
     */
    public static void flush(Flushable flushable) {

        if (null == flushable) {
            return;
        }

        try {
            flushable.flush();
        } catch (IOException ioe) {
            // Do Nothing
        }
    }

    /**
     * @param connection - the HttpURLConnection
     */
    public static void disconnect(HttpURLConnection connection) {

        if (null == connection) {
            return;
        }

        try {
            connection.disconnect();
        } catch (Exception e) {
            // DO Nothing
        }
    }

    /**
     * @param exc Exception
     * @return String
     */
    public static String getStackTrace(Exception exc) {

        StringBuilder sb = new StringBuilder();
        if (null == exc) {
            return sb.toString();
        }

        StackTraceElement[] stackTraceElements = exc.getStackTrace();

        for (StackTraceElement element : stackTraceElements) {
            sb.append(element.toString());
            sb.append("\n");
        }

        return sb.toString();
    }

}