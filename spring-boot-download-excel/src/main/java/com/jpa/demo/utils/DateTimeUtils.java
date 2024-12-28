package com.jpa.demo.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateTimeUtils {

    public static final String FORMATO_LOCAL = "dd/MM/yyyy";

    private static DateTimeFormatter brazilDateFormatter = DateTimeFormatter.ofPattern(FORMATO_LOCAL);

    public static LocalDate stringToLocalDate(String date) {
        if (date == null || date.trim().isEmpty()) {
            return null;
        }
        return LocalDate.parse(date, brazilDateFormatter);  // Use the custom formatter
    }

    // Convert a LocalDate to a string in the format dd/MM/yyyy
    public static String localDateToString(LocalDate date) {
        return date != null ? date.format(brazilDateFormatter) : null;  // Use the custom formatter
    }
}
