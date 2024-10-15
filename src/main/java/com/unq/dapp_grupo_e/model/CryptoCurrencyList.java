package com.unq.dapp_grupo_e.model;

import java.util.ArrayList;

import com.unq.dapp_grupo_e.model.exceptions.InvalidCurrencyException;

public class CryptoCurrencyList {

    public ArrayList<CryptoCurrency> cryptos = new ArrayList<>();

    //public CryptoCurrencyList() { }

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
