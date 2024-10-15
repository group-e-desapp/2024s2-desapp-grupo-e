package com.unq.dapp_grupo_e.utilities.factories;

import com.unq.dapp_grupo_e.model.Transaction;

public class TransactionFactory {

    public static Transaction createWithPrice(Double priceOffered) {
        var transaction = new Transaction();
        transaction.setSymbolTrade("ADAUSDT");
        transaction.setCryptoNominalValue((float) 10.0);
        transaction.setPriceOffered(priceOffered);
        transaction.setOperationType("Sell");
        return transaction;
    }

    public static Transaction createWithPriceAndNominalValue(Double priceOffered, Float nominalValue) {
        var transaction = new Transaction();
        transaction.setSymbolTrade("ADAUSDT");
        transaction.setCryptoNominalValue(nominalValue);
        transaction.setPriceOffered(priceOffered);
        transaction.setOperationType("Sell");
        return transaction;
    }
    
}
