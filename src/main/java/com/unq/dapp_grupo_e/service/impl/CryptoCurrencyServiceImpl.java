package com.unq.dapp_grupo_e.service.impl;

import org.springframework.stereotype.Service;

import com.unq.dapp_grupo_e.model.CryptoCurrency;
import com.unq.dapp_grupo_e.service.BinanceService;
import com.unq.dapp_grupo_e.service.CryptoCurrencyService;
import com.unq.dapp_grupo_e.utilities.CurrentDateAndTime;

@Service
public class CryptoCurrencyServiceImpl implements CryptoCurrencyService {

    private final BinanceService binanceService;

    
    public CryptoCurrencyServiceImpl(BinanceService binanceService) {
        this.binanceService = binanceService;
    }

    @Override
    public CryptoCurrency getCryptoValue(String symbol) {
        CryptoCurrency cryptoEntity =  binanceService.getPrice(symbol);
        String actualTimeAndDate = CurrentDateAndTime.getNewDateAsString();
        cryptoEntity.setLastUpdateDateAndTime(actualTimeAndDate);
        return cryptoEntity;
    }

    
}
