package com.unq.dapp_grupo_e.utilities.factories;

import com.unq.dapp_grupo_e.model.CryptoCurrency;

public class CryptoCurrencyFactory {


    private CryptoCurrencyFactory() {
        throw new IllegalStateException("Utility class");
    }

    public static CryptoCurrency createWithSymbol(String symbolName) {
        CryptoCurrency crypto = new CryptoCurrency();
        crypto.setPrice(1245.65);
        crypto.setSymbol(symbolName);
        crypto.setLastUpdateDateAndTime("13/10/2024 18:12:45");
        return crypto;
    }

    public static CryptoCurrency createWithSymbolAndPrice(String symbolName, Double price) {
        CryptoCurrency crypto = new CryptoCurrency();
        crypto.setPrice(price);
        crypto.setSymbol(symbolName);
        crypto.setLastUpdateDateAndTime("14/10/2024 18:12:45");
        return crypto;
    }
    
}
