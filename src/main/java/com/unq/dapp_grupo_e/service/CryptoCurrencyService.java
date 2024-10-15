package com.unq.dapp_grupo_e.service;

import com.unq.dapp_grupo_e.model.CryptoCurrency;
import com.unq.dapp_grupo_e.model.CryptoCurrencyList;

public interface CryptoCurrencyService {


    public CryptoCurrency getCryptoValue(String symbol);
    
    public CryptoCurrencyList getAllCryptoValues();
    
}
