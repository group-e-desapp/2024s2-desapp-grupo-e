package com.unq.dapp_grupo_e.service.impl;

import org.springframework.stereotype.Service;

import com.unq.dapp_grupo_e.model.CryptoCurrency;
import com.unq.dapp_grupo_e.model.CryptoCurrencyEnum;
import com.unq.dapp_grupo_e.model.CryptoCurrencyList;
import com.unq.dapp_grupo_e.repository.CryptoCurrencyRepository;
import com.unq.dapp_grupo_e.service.BinanceService;
import com.unq.dapp_grupo_e.service.CryptoCurrencyService;
import com.unq.dapp_grupo_e.utilities.CurrentDateAndTime;

@Service
public class CryptoCurrencyServiceImpl implements CryptoCurrencyService {

    private final BinanceService binanceService;
    private final CryptoCurrencyRepository cryptoCurrencyRepo;
    
    public CryptoCurrencyServiceImpl(BinanceService binanceService, CryptoCurrencyRepository cryptoCurrencyRepo) {
        this.binanceService = binanceService;
        this.cryptoCurrencyRepo = cryptoCurrencyRepo;
    }

    @Override
    public CryptoCurrency getCryptoValue(String symbol) {
        CryptoCurrencyEnum.validateCrypto(symbol);
        CryptoCurrency cryptoEntity =  binanceService.getCrypto(symbol);
        String actualTimeAndDate = CurrentDateAndTime.getNewDateAsString();
        cryptoEntity.setLastUpdateDateAndTime(actualTimeAndDate);
        cryptoCurrencyRepo.save(cryptoEntity);
        return cryptoEntity;
    }

    @Override
    public CryptoCurrencyList getAllCryptoValues() {
        CryptoCurrencyList cryptoList = new CryptoCurrencyList();
        for(CryptoCurrencyEnum crypto : CryptoCurrencyEnum.values()) {
            CryptoCurrency currency = binanceService.getCrypto(crypto.name());
            if (currency != null) {
				currency.setLastUpdateDateAndTime(CurrentDateAndTime.getNewDateAsString());
			}
            cryptoList.addCrypto(currency);
        }
        return cryptoList;
    }

    
}
