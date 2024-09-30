package com.unq.dapp_grupo_e.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class CryptoCurrency {

    @Id
    private String idSymbol;
    private String symbol;
    private Float price;
    private String lastUpdateDateAndTime;

    public String getSymbol() {
        return symbol;
    }
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
    public Float getPrice() {
        return price;
    }
    public void setPrice(Float price) {
        this.price = price;
    }
    public String getLastUpdateDateAndTime() {
        return lastUpdateDateAndTime;
    }
    public void setLastUpdateDateAndTime(String lastUpdateDateAndTime) {
        this.lastUpdateDateAndTime = lastUpdateDateAndTime;
    }


    
    
}
