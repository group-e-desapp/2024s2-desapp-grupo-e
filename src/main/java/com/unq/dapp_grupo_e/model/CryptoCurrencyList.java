package com.unq.dapp_grupo_e.model;

import java.util.ArrayList;
import java.util.List;

import com.unq.dapp_grupo_e.exceptions.InvalidCurrencyException;

public class CryptoCurrencyList {

    public final List<CryptoCurrency> cryptos = new ArrayList<>();

    public List<CryptoCurrency> getCryptos() {
        return cryptos;
    }

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
