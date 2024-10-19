package com.unq.dapp_grupo_e.model;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idExchange;
    @Column(name = "idUser")
    private Integer idUser = 1;
    @Column(name = "symbolTrade")
    private String symbolTrade;
    @Column(name = "cryptoNominalValue")
    private Float cryptoNominalValue;
    @Column(name = "priceOffered")
    private Double priceOffered;
    @Column(name = "operationType")
    private String operationType;
    @Column(name = "dateTimeCreated")
    private String dateTimeCreated;


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