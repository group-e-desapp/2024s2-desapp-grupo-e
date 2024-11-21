package com.unq.dapp_grupo_e.dto;

import com.unq.dapp_grupo_e.model.Transaction;
import com.unq.dapp_grupo_e.model.User;

public class TransactionProcessedDTO {

    private Integer idTransaction;
    private String cryptoSymbol;
    private String userFullName;    
    private String addressForTransaction;
    private String action;


    public static TransactionProcessedDTO from(Transaction transaction, User user) {
        var transactionDTO = new TransactionProcessedDTO();
        transactionDTO.idTransaction = transaction.getIdExchange();
        transactionDTO.cryptoSymbol = transaction.getSymbolTrade();
        transactionDTO.userFullName = user.getName() + " " + user.getSurname();
        if (transaction.getOperationType().equals("SELL")) {
            transactionDTO.addressForTransaction = user.getCvu();
            transactionDTO.action = "Realize transfer";
        } else {
            transactionDTO.addressForTransaction = user.getWalletAddress();
            transactionDTO.action = "Accept offer for crypto value";
        }
        return transactionDTO;
    }

    public Integer getIdTransaction() {
        return idTransaction;
    }

    public String getCryptoSymbol() {
        return cryptoSymbol;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public String getAddressForTransaction() {
        return addressForTransaction;
    }

    public String getAction() {
        return action;
    }
    
}
