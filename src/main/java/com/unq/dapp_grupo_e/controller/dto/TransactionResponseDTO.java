package com.unq.dapp_grupo_e.controller.dto;

import com.unq.dapp_grupo_e.model.Transaction;
import com.unq.dapp_grupo_e.model.User;

public class TransactionResponseDTO {

    public String dateIntentionCreated;
    public String cryptoSymbol;
    public Float nominalValue;
    public Double priceOfferCotization;
    public Double totalOperationARS;
    public String userFullName;
    public Integer userOperationsDone;
    public String userReputation;

    public static TransactionResponseDTO from(Transaction transaction, User user) {
        var transactionDTO = new TransactionResponseDTO();
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
    
}
