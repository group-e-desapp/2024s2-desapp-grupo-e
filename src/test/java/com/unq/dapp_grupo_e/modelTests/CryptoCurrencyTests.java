package com.unq.dapp_grupo_e.modelTests;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.unq.dapp_grupo_e.exceptions.InvalidCurrencyException;
import com.unq.dapp_grupo_e.factory.CryptoCurrencyFactory;
import com.unq.dapp_grupo_e.model.CryptoCurrency;
import com.unq.dapp_grupo_e.model.CryptoCurrencyEnum;
import com.unq.dapp_grupo_e.repository.CryptoCurrencyRepository;
import com.unq.dapp_grupo_e.service.BinanceService;
import com.unq.dapp_grupo_e.service.CryptoCurrencyService;
import com.unq.dapp_grupo_e.service.impl.CryptoCurrencyServiceImpl;

@ActiveProfiles("test") 
@SpringBootTest
class CryptoCurrencyTests {

    @Mock
    private  BinanceService binanceService;
    @Mock
    private  CryptoCurrencyRepository cryptoCurrencyRepo;

    private CryptoCurrencyService cryptoService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        cryptoService = new CryptoCurrencyServiceImpl(
            binanceService,
            cryptoCurrencyRepo
        );
        
    }

    @Test
    void obtainPriceOfCryptoCurrency() {
        CryptoCurrency cryptoMocked = CryptoCurrencyFactory.createWithSymbolAndPrice("NEOUSDT", 3.58);
        when(binanceService.getCrypto("NEOUSDT")).thenReturn(cryptoMocked);

        CryptoCurrency cryptoConsulted = cryptoService.getCryptoValue("NEOUSDT");
        
        Assertions.assertEquals("NEOUSDT", cryptoConsulted.getSymbol());
        Assertions.assertEquals(3.58, cryptoConsulted.getPrice());

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
