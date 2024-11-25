package com.unq.dapp_grupo_e.service.impl;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.unq.dapp_grupo_e.model.CryptoCurrency;
import com.unq.dapp_grupo_e.model.CryptoCurrencyEnum;
import com.unq.dapp_grupo_e.model.CryptoCurrencyList;
import com.unq.dapp_grupo_e.model.cryptoCotizationsBody.CryptoFormCotization;
import com.unq.dapp_grupo_e.model.cryptoCotizationsBody.DateTimeCotization;
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
        CryptoCurrency cryptoEntity = binanceService.getCrypto(symbol);
        String actualTimeAndDate = CurrentDateAndTime.getNewDateAsString();
        cryptoEntity.setLastUpdateDateAndTime(actualTimeAndDate);
        cryptoCurrencyRepo.save(cryptoEntity);
        return cryptoEntity;
    }

    @Cacheable(value = "cacheCryptoCotizations", unless = "#result.getCryptos().size() != 14")
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

    @Override
    public CryptoFormCotization getLatestCotizationsOf(String symbolCrypto) {
        var endDate = CurrentDateAndTime.getNewDateAsString();
        var startDate = CurrentDateAndTime.previousDayOf(endDate);
        System.out.println(endDate);
        System.out.println(startDate);
        List<CryptoCurrency> cotizations = cryptoCurrencyRepo.getLatestCotizationsOf(symbolCrypto, startDate, endDate);
        CryptoFormCotization cryptoForm = new CryptoFormCotization(symbolCrypto);

        for(CryptoCurrency cotization:cotizations) {
            cryptoForm.addDateTimeCotization(new DateTimeCotization(cotization.getLastUpdateDateAndTime(), cotization.getPrice()));
        }
        return cryptoForm;
    }

    public void deleteAllCotizationsOf(String symbol) {
        cryptoCurrencyRepo.deleteCryptoPrices(symbol);
    }

}
