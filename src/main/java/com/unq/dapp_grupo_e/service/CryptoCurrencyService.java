package com.unq.dapp_grupo_e.service;

import com.unq.dapp_grupo_e.model.CryptoCurrency;
import com.unq.dapp_grupo_e.model.CryptoCurrencyList;
import com.unq.dapp_grupo_e.model.cryptoCotizationsBody.CryptoFormCotization;

public interface CryptoCurrencyService {


    public CryptoCurrency getCryptoValue(String symbol);

    public CryptoFormCotization getLatestCotizationsOf(String symbolCrypto);
    
    public CryptoCurrencyList getAllCryptoValues();

    public void deleteAllCotizationsOf(String symbolCrypto);
    
}
