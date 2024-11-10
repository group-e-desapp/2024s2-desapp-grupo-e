package com.unq.dapp_grupo_e.controller.dto;

import com.unq.dapp_grupo_e.model.Transaction;
import com.unq.dapp_grupo_e.model.User;

public class TransactionResponseDTO {

    private Integer idTransaction;
    private String dateIntentionCreated;
    private String cryptoSymbol;
    private Float nominalValue;
    private Double priceOfferCotization;
    private Double totalOperationARS;
    private String userFullName;
    private Integer userOperationsDone;
    private String userReputation;

    public static TransactionResponseDTO from(Transaction transaction, User user) {
        var transactionDTO = new TransactionResponseDTO();
        transactionDTO.idTransaction = transaction.getIdExchange();
        transactionDTO.dateIntentionCreated = transaction.getDateTimeCreated();
        transactionDTO.cryptoSymbol = transaction.getSymbolTrade();
        transactionDTO.nominalValue = transaction.getCryptoNominalValue();
        transactionDTO.priceOfferCotization = transaction.getPriceOffered();
        transactionDTO.totalOperationARS = transaction.getCryptoNominalValue() * transaction.getPriceOffered();
        transactionDTO.userFullName = user.getName() + " " + user.getSurname();
        transactionDTO.userOperationsDone = user.getAmountSetOperations();
        transactionDTO.userReputation = user.reputation();
        return transactionDTO;
    }
    
    public Integer getIdTransaction() {
        return idTransaction;
    }

    public String getDateIntentionCreated() {
        return dateIntentionCreated;
    }

    public String getCryptoSymbol() {
        return cryptoSymbol;
    }

    public Float getNominalValue() {
        return nominalValue;
    }

    public Double getPriceOfferCotization() {
        return priceOfferCotization;
    }

    public Double getTotalOperationARS() {
        return totalOperationARS;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public Integer getUserOperationsDone() {
        return userOperationsDone;
    }

    public String getUserReputation() {
        return userReputation;
    }
    
    
}
