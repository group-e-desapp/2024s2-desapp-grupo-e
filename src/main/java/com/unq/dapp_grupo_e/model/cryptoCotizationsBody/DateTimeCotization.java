package com.unq.dapp_grupo_e.model.cryptoCotizationsBody;

public class DateTimeCotization {

    private String dateTimeCotization;
    private Double cotization;

    public DateTimeCotization(String dateRegistered, Double cotization) {
        this.dateTimeCotization = dateRegistered;
        this.cotization = cotization;
    }

    public String getDateTimeCotization() {
        return dateTimeCotization;
    }

    public Double getCotization() {
        return cotization;
    }
    
}
