package com.unq.dapp_grupo_e.model;

import jakarta.persistence.Id;

public class Transaction {

    @Id
    private Long idExchange;
    private Long idUser;
    private String symbolTrade;
    private Float cryptoNominalValue;
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


    
    
}
