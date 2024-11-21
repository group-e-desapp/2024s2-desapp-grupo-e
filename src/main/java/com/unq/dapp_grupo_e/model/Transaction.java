package com.unq.dapp_grupo_e.model;


import com.unq.dapp_grupo_e.exceptions.InvalidActionException;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idExchange;
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
    @Column(name = "idUserAnswering")
    private Integer idUserAnswering = 0;
    @Enumerated(EnumType.STRING)
    private TransactionStatus status = TransactionStatus.ACTIVE;


    public Integer getIdExchange() {
        return idExchange;
    }
    public void setIdExchange(Integer idExchange) {
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


    public TransactionStatus getStatus() {
        return status;
    }
    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public void confirmTransactionDone(Integer idUserConfirm) {
        
        if (!idUserConfirm.equals(idUser)) {
            throw new InvalidActionException("Not allowed to confirm a transaction that you didn't create");
        }

        if (this.getStatus() == TransactionStatus.TRANSFERING) {
            this.setStatus(TransactionStatus.CLOSE);
        } else {
            throw new InvalidActionException("Not allowed to confirm the transaction in its current state");
        }
    }

    public void cancelTransaction(Integer idUserCancel) {

        if (idUserCancel.equals(idUser)) {
            this.setStatus(TransactionStatus.CANCELLED);
        } else if (idUserCancel.equals(idUserAnswering)) {
            this.setStatus(TransactionStatus.ACTIVE);
        } else {
            throw new InvalidActionException("Not allowed to cancel a transaction that you are not part of");
        }
    }

    public void realizeTransfer(Integer idUserTransfering) {
        if (this.getStatus() != TransactionStatus.ACTIVE) {
            throw new InvalidActionException("Transaction is already in process or is not available for interaction");
        }

        if (!idUserTransfering.equals(idUser)) {
            this.setIdUserAnswering(idUserTransfering);
            this.setStatus(TransactionStatus.TRANSFERING);
        } else {
            throw new InvalidActionException("Not allowed to realize the transfer for your own intention created");
        }
    }


    public Integer getIdUserAnswering() {
        return idUserAnswering;
    }

    public void setIdUserAnswering(Integer idUserAnswering) {
        this.idUserAnswering = idUserAnswering;
    }
    

    
}