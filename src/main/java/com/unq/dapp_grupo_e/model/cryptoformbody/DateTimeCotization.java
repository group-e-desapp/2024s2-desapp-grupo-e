package com.unq.dapp_grupo_e.model.cryptoformbody;

public class DateTimeCotization {

    private String dateTimeRecord;
    private Double cotization;

    public DateTimeCotization(String dateRegistered, Double cotization) {
        this.dateTimeRecord = dateRegistered;
        this.cotization = cotization;
    }

    public String getDateTimeCotization() {
        return dateTimeRecord;
    }

    public Double getCotization() {
        return cotization;
    }
    
}
