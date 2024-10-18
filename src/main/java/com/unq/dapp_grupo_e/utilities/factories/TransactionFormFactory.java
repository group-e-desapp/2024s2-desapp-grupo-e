package com.unq.dapp_grupo_e.utilities.factories;

import com.unq.dapp_grupo_e.controller.dto.TransactionFormDTO;

public class TransactionFormFactory {

    private TransactionFormFactory() {
        throw new IllegalStateException("Utility class");
    }

    public static TransactionFormDTO createFullData(String symbol, Float nominalValue, Double price, String operationType) {
        var transactionForm = new TransactionFormDTO();
        transactionForm.symbolCrypto = symbol;
        transactionForm.cryptoNominalValue = nominalValue;
        transactionForm.priceOffered = price;
        transactionForm.operationType = operationType;
        return transactionForm;
    }
    
}
