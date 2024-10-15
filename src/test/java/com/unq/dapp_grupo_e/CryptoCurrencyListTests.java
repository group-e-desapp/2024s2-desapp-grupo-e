package com.unq.dapp_grupo_e;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.unq.dapp_grupo_e.model.CryptoCurrency;
import com.unq.dapp_grupo_e.model.CryptoCurrencyList;
import com.unq.dapp_grupo_e.model.exceptions.InvalidCurrencyException;
import com.unq.dapp_grupo_e.utilities.factories.CryptoCurrencyFactory;

@ActiveProfiles("test") 
@SpringBootTest
class CryptoCurrencyListTests {

    @Test
    void additionOfNewCryptoToList() {
        CryptoCurrencyList cryptoList = new CryptoCurrencyList();
        CryptoCurrency newCrypto = CryptoCurrencyFactory.createWithSymbol("BTCUSDT");
        cryptoList.addCrypto(newCrypto);

        CryptoCurrency recoveredCrypto = cryptoList.getCrypto("BTCUSDT");
        Assertions.assertEquals(newCrypto.getSymbol(), recoveredCrypto.getSymbol());
        Assertions.assertEquals(newCrypto.getPrice(), recoveredCrypto.getPrice());
        Assertions.assertEquals(newCrypto.getLastUpdateDateAndTime(), recoveredCrypto.getLastUpdateDateAndTime());
    }

    @Test
    void invalidGetCryptoCurrencyOnCryptoList() {
        CryptoCurrencyList cryptoList = new CryptoCurrencyList();
        CryptoCurrency crypto = CryptoCurrencyFactory.createWithSymbol("BTCUSDT");
        cryptoList.addCrypto(crypto);

        Assertions.assertThrows(InvalidCurrencyException.class, () -> cryptoList.getCrypto("FalseCrypto"));
    }

    @Test
    void checkMessageOfInvalidCryptoCurrencyOnCryptoList() {
        CryptoCurrencyList cryptoList = new CryptoCurrencyList();
        CryptoCurrency crypto = CryptoCurrencyFactory.createWithSymbol("BTCUSDT");
        cryptoList.addCrypto(crypto);

        InvalidCurrencyException exception = Assertions.assertThrows(InvalidCurrencyException.class, () -> cryptoList.getCrypto("FalseCrypto"));
        Assertions.assertEquals("The crypto of name 'FalseCrypto' doesn't exist", exception.getMessage());
    }
    
}