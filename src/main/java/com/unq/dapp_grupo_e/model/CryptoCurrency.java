package com.unq.dapp_grupo_e.model;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class CryptoCurrency implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSymbol;
    private String symbol;
    private Double price;
    private String lastUpdateDateAndTime;

    public String getSymbol() {
        return symbol;
    }
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    public String getLastUpdateDateAndTime() {
        return lastUpdateDateAndTime;
    }
    public void setLastUpdateDateAndTime(String lastUpdateDateAndTime) {
        this.lastUpdateDateAndTime = lastUpdateDateAndTime;
    }


    
    
}
