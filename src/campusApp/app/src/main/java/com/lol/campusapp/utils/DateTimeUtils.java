package com.lol.campusapp.utils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

/**
 * provides methods parsing Date and Time objects
 * ensures consistent date/time Format usage throughout the app
 */
public class DateTimeUtils {
    public static final String NULL_STRING = "null";

    public static String getAbbrvDayOfWeek(LocalDate date) {
        return date.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.GERMAN).replace(".", "");
    }

    public static DateTimeFormatter getTimeFormatter() {
        return DateTimeFormatter.ofPattern("HH:mm");
    }

    public static DateTimeFormatter getDateFormatter() {
        return DateTimeFormatter.ofPattern("dd.MM.yyyy");
    }

    public static LocalTime parseTime(String time) {
        if (time.equals(NULL_STRING) || time.trim().isEmpty()) {
            return null;
        } else {
            return LocalTime.parse(time, getTimeFormatter());
        }
    }

    public static LocalDate parseDate(String date) {
        if (date.equals(NULL_STRING) || date.trim().isEmpty()) {
            return null;
        } else {
            return LocalDate.parse(date, getDateFormatter());
        }
    }

    public static String printDate(LocalDate date) {
        return date != null? date.format(getDateFormatter()) : NULL_STRING;
    }

    public static String printTime(LocalTime time) {
        return time != null? time.format(getTimeFormatter()) : NULL_STRING;
    }
}
