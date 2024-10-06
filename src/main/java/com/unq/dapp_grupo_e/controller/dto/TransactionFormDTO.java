package com.unq.dapp_grupo_e.controller.dto;

import com.unq.dapp_grupo_e.model.Transaction;

import jakarta.validation.constraints.NotNull;

public class TransactionFormDTO {

    @NotNull
    public String symbolCrypto;
    @NotNull
    public String operationType;
    @NotNull
    public Float cryptoNominalValue;
    @NotNull
    public Float priceOffered;


    public Transaction toModel() {
        var createdTransaction = new Transaction();
        createdTransaction.setIdUser((long) 1);
        createdTransaction.setSymbolTrade(symbolCrypto);
        createdTransaction.setOperationType(operationType);
        createdTransaction.setCryptoNominalValue(cryptoNominalValue);
        createdTransaction.setPriceOffered(priceOffered);
        return createdTransaction;
    }
    
}
