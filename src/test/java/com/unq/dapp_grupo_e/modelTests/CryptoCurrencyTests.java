package com.unq.dapp_grupo_e.modelTests;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.unq.dapp_grupo_e.exceptions.InvalidCurrencyException;
import com.unq.dapp_grupo_e.model.CryptoCurrencyEnum;


@ActiveProfiles("test") 
@SpringBootTest
class CryptoCurrencyTests {


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
