package com.unq.dapp_grupo_e;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.unq.dapp_grupo_e.model.CryptoCurrency;
import com.unq.dapp_grupo_e.model.CryptoCurrencyEnum;
import com.unq.dapp_grupo_e.service.CryptoCurrencyService;

@SpringBootTest
public class CryptoCurrencyTests {

    @Autowired
    private CryptoCurrencyService cryptoService;


    @Test
    void getCurrentPriceOfCryptoCurrency() {
        CryptoCurrencyEnum cryptoSelected = CryptoCurrencyEnum.ADAUSDT;
        CryptoCurrency cryptoConsulted = cryptoService.getCryptoValue(cryptoSelected.name());
        Assertions.assertNotNull(cryptoConsulted.getPrice());
    }
    
}
