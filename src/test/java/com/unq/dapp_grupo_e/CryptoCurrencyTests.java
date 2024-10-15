package com.unq.dapp_grupo_e;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.unq.dapp_grupo_e.model.CryptoCurrency;
import com.unq.dapp_grupo_e.model.CryptoCurrencyEnum;
import com.unq.dapp_grupo_e.model.exceptions.InvalidCurrencyException;
import com.unq.dapp_grupo_e.service.CryptoCurrencyService;

@SpringBootTest(properties = "spring.main.web-application-type=none")
class CryptoCurrencyTests {

    @Autowired
    private CryptoCurrencyService cryptoService;

    @EnabledIfSystemProperty(named = "enable.binance.tests", matches = "true")
    @Test
    void getCurrentPriceOfCryptoCurrency() {
        CryptoCurrencyEnum cryptoSelected = CryptoCurrencyEnum.ADAUSDT;
        CryptoCurrency cryptoConsulted = cryptoService.getCryptoValue(cryptoSelected.name());
        Assertions.assertNotNull(cryptoConsulted.getPrice());
    }

    @Test
    void checkSymbolBelongsToTheSymbolsRegistered() {
        String nameSymbol = "CAKEUSDT";
        Assertions.assertTrue(CryptoCurrencyEnum.isAValidCurrency(nameSymbol));
    }

    @Test
    void checkSymbolDoesntBelongToTheSymbolsRegistered() {
        String nameSymbol = "MyNewCoin";
        Assertions.assertFalse(CryptoCurrencyEnum.isAValidCurrency(nameSymbol));
    }

    @Test
    void exceptionForInvalidCurrencySymbol() {
        String nameSymbol = "FalseCoin";
        Assertions.assertThrows(InvalidCurrencyException.class, () -> CryptoCurrencyEnum.validateCrypto(nameSymbol));
    }
    
}
