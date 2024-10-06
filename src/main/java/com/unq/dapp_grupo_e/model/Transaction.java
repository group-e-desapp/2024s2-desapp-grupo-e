package com.unq.dapp_grupo_e.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Transaction {

    @Id
    private Long idExchange;
    private Long idUser = (long) 1;
    private String symbolTrade;
    private Float cryptoNominalValue;
    private Float priceOffered;
    private String operationType;


    public Long getIdExchange() {
        return idExchange;
    }
    public void setIdExchange(Long idExchange) {
        this.idExchange = idExchange;
    }
    public Long getIdUser() {
        return idUser;
    }
    public void setIdUser(Long idUser) {
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

    public Float getPriceOffered() {
        return priceOffered;
    }
    public void setPriceOffered(Float priceOffered) {
        this.priceOffered = priceOffered;
    }


    public boolean isAValidMarginForTransaction(Double currentCryptoPriceInARS) {
        var marginOfPrice = currentCryptoPriceInARS * 0.05;
        return (currentCryptoPriceInARS - marginOfPrice < priceOffered) || (priceOffered < currentCryptoPriceInARS + marginOfPrice);
    }

    public Float totalSumOfOperation() {
        return cryptoNominalValue * priceOffered;
    }
    
    
    
}