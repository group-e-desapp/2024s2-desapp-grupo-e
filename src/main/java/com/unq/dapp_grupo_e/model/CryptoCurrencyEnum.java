package com.unq.dapp_grupo_e.model;

import java.util.Arrays;

import com.unq.dapp_grupo_e.model.exceptions.InvalidCurrencyException;

public enum CryptoCurrencyEnum {
    ALICEUSDT,
    MATICUSDT,
    AXSUSDT,
    AAVEUSDT,
    ATOMUSDT,
    NEOUSDT,
    DOTUSDT,
    ETHUSDT,
    CAKEUSDT,
    BTCUSDT,
    BNBUSDT,
    ADAUSDT,
    TRXUSDT,
    AUDIOUSDT;

    public static boolean isAValidCurrency(String symbolCurrency) {
        return Arrays.stream(CryptoCurrencyEnum.values())
                        .anyMatch(crypto -> crypto.name().equals(symbolCurrency));
    }

    public static void validateCrypto(String symbolCurrency) {
        
        if (!isAValidCurrency(symbolCurrency)) {
            throw new InvalidCurrencyException(symbolCurrency + " is not a valid crypto currency");
        }
    }
    
}

