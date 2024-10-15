package com.unq.dapp_grupo_e.model;

import com.unq.dapp_grupo_e.utilities.CurrentDateAndTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idExchange;
    private Integer idUser = 15;
    private String symbolTrade;
    private Float cryptoNominalValue;
    private Double priceOffered;
    private String operationType;
    private String dateTimeCreated;


    public Transaction() {};

    public Transaction(Long idExc, Integer idUser, String symbol, Float cryptoNominalValue,
    Double priceOffered, String operationType) {
        this.idExchange = idExc;
        this.idUser = idUser;
        this.symbolTrade = symbol;
        this.cryptoNominalValue = cryptoNominalValue;
        this.priceOffered = priceOffered;
        this.operationType = operationType;
        this.dateTimeCreated = CurrentDateAndTime.getNewDateAsString();
    }

    public Long getIdExchange() {
        return idExchange;
    }
    public void setIdExchange(Long idExchange) {
        this.idExchange = idExchange;
    }
    public Integer getIdUser() {
        return idUser;
    }
    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }
    public String getSymbolTrade() {
        return symbolTrade;
    }
    public void setSymbolTrade(String symbolTrade) {
        this.symbolTrade = symbolTrade;
    }
    public Float getCryptoNominalValue() {
        return cryptoNominalValue;
    }
    public void setCryptoNominalValue(Float cryptoNominalValue) {
        this.cryptoNominalValue = cryptoNominalValue;
    }
    public String getOperationType() {
        return operationType;
    }
    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public Double getPriceOffered() {
        return priceOffered;
    }
    public void setPriceOffered(Double priceOffered) {
        this.priceOffered = priceOffered;
    }

    public String getDateTimeCreated() {
        return dateTimeCreated;
    }

    public void setDateTimeCreated(String dateTimeCreated) {
        this.dateTimeCreated = dateTimeCreated;
    }

    public boolean isAValidMarginForTransaction(Double currentCryptoPriceInARS) {
        var marginOfPrice = currentCryptoPriceInARS * 0.05;
        return (currentCryptoPriceInARS - marginOfPrice < priceOffered) && (priceOffered < currentCryptoPriceInARS + marginOfPrice);
    }

    public Double totalSumOfOperation() {
        return cryptoNominalValue * priceOffered;
    }

    
    
    
    
}