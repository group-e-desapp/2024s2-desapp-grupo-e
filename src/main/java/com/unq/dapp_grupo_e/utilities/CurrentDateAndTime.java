package com.unq.dapp_grupo_e.utilities;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CurrentDateAndTime {

    private static final String DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";

    private CurrentDateAndTime() {};

    public static Date getNewDate() {
        return new Date();
    }

    public static String getNewDateAsString() {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        return formatter.format(getNewDate()); 
    }
    
}
