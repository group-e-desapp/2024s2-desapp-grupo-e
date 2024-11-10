package com.unq.dapp_grupo_e.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class CurrentDateAndTime {

    private static final String DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";
    private static final SimpleDateFormat FORMAT_APPLIED = new SimpleDateFormat(DATE_FORMAT);

    private CurrentDateAndTime() {}

    public static Date getNewDate() {
        return new Date();
    }

    public static String getNewDateAsString() {
        return FORMAT_APPLIED.format(getNewDate());
    }
    
    public static Date toDate(String dateAsString) throws ParseException {
        return FORMAT_APPLIED.parse(dateAsString);
    }

    public static Boolean achieveRequirementOfTransacion(String date) {
        try {
            Date dateTransactionCreated = toDate(date);

            var diffBetweenDates = getNewDate().getTime() - dateTransactionCreated.getTime();
            
            return TimeUnit.MILLISECONDS.toMinutes(diffBetweenDates) < 30;
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        
        
    }
}
