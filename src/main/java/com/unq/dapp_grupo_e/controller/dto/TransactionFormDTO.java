package com.unq.dapp_grupo_e.controller.dto;

import com.unq.dapp_grupo_e.model.Transaction;
import com.unq.dapp_grupo_e.utilities.CurrentDateAndTime;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public class TransactionFormDTO {

    @NotNull
    @Schema(example = "BTCUSDT")
    public String symbolCrypto;
    @NotNull
    @Schema(example = "Sell")
    public String operationType;
    @NotNull
    @Schema(example = "25")
    public Float cryptoNominalValue;
    @NotNull
    @Schema(example = "531.5")
    public Double priceOffered;


    public Transaction toModel() {
        var createdTransaction = new Transaction();
        createdTransaction.setSymbolTrade(symbolCrypto);
        createdTransaction.setOperationType(operationType);
        createdTransaction.setCryptoNominalValue(cryptoNominalValue);
        createdTransaction.setPriceOffered(priceOffered);
        createdTransaction.setDateTimeCreated(CurrentDateAndTime.getNewDateAsString());
        return createdTransaction;
    }
    
    
}
