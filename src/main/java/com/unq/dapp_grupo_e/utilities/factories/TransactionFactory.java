package com.unq.dapp_grupo_e.utilities.factories;

import com.unq.dapp_grupo_e.model.Transaction;

public class TransactionFactory {

    private TransactionFactory() {
        throw new IllegalStateException("Utility class");
    }

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

    public static Transaction createWithFullData(Integer idUser, String symbol, Float nominalValue,
                                    Double priceOffered, String operationType, String dateTimeCreated) {
        Transaction transaction = new Transaction();
        transaction.setIdUser(idUser);
        transaction.setSymbolTrade(symbol);
        transaction.setCryptoNominalValue(nominalValue);
        transaction.setPriceOffered(priceOffered);
        transaction.setOperationType(operationType);
        transaction.setDateTimeCreated(dateTimeCreated);
        return transaction;
    }
    
}
    