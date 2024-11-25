package com.unq.dapp_grupo_e.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.unq.dapp_grupo_e.exceptions.InvalidDateFormatException;

public class CurrentDateAndTime {

    private static final String DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";

    private CurrentDateAndTime() {}

    public static Date getNewDate() {
        return new Date();
    }

    public static String getNewDateAsString() {
        SimpleDateFormat formatApplied = new SimpleDateFormat(DATE_FORMAT);
        return formatApplied.format(getNewDate());
    }
    
    public static Date toDate(String dateAsString) throws ParseException {
        SimpleDateFormat formatApplied = new SimpleDateFormat(DATE_FORMAT);
        return formatApplied.parse(dateAsString);
    }

    public static String previousDayOf(String date) {
        DateTimeFormatter formatApplied = DateTimeFormatter.ofPattern(DATE_FORMAT);
        var dateToUpdate = LocalDateTime.parse(date, formatApplied);
        dateToUpdate = dateToUpdate.minusDays(1);

        return formatApplied.format(dateToUpdate);
    }

    public static String previousTimeAs(String date,  Integer hoursToGoBack) {
        DateTimeFormatter formatApplied = DateTimeFormatter.ofPattern(DATE_FORMAT);
        var dateToUpdate = LocalDateTime.parse(date, formatApplied);

        dateToUpdate = dateToUpdate.minusHours(hoursToGoBack);
       
        return formatApplied.format(dateToUpdate);

    }

    public static boolean achieveRequirementOfTransacion(String date) {
        try {
            Date dateTransactionCreated = toDate(date);

            var diffBetweenDates = getNewDate().getTime() - dateTransactionCreated.getTime();
            
            return TimeUnit.MILLISECONDS.toMinutes(diffBetweenDates) < 30;
        } catch (ParseException e) {
            throw new InvalidDateFormatException("Date given is not valid due to its format or values");
        } 
    }
}
