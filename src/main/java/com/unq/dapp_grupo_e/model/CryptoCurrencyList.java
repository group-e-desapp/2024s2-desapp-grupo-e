package com.unq.dapp_grupo_e.model;

import java.util.ArrayList;
import java.util.List;

import com.unq.dapp_grupo_e.model.exceptions.InvalidCurrencyException;

public class CryptoCurrencyList {

    public static final List<CryptoCurrency> cryptos = new ArrayList<>();

    public void addCrypto(CryptoCurrency crypto) {
        cryptos.add(crypto);
    }

    public CryptoCurrency getCrypto(String symbol) {
        return cryptos.stream()
                      .filter(c -> c.getSymbol().equalsIgnoreCase(symbol))
                      .findFirst()
                      .orElseThrow(() -> new InvalidCurrencyException("The crypto of name '" + symbol + "' doesn't exist"));
    }
    
}
