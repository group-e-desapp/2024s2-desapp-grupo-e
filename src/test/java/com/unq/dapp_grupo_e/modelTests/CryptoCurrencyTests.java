package com.unq.dapp_grupo_e.modelTests;

import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.unq.dapp_grupo_e.exceptions.InvalidCurrencyException;
import com.unq.dapp_grupo_e.factory.CryptoCurrencyFactory;
import com.unq.dapp_grupo_e.model.CryptoCurrency;
import com.unq.dapp_grupo_e.model.CryptoCurrencyEnum;
import com.unq.dapp_grupo_e.model.cryptoCotizationsBody.CryptoFormCotization;
import com.unq.dapp_grupo_e.repository.CryptoCurrencyRepository;
import com.unq.dapp_grupo_e.service.BinanceService;
import com.unq.dapp_grupo_e.service.CryptoCurrencyService;
import com.unq.dapp_grupo_e.service.impl.CryptoCurrencyServiceImpl;
import com.unq.dapp_grupo_e.utilities.CurrentDateAndTime;

@ActiveProfiles("test") 
@SpringBootTest
class CryptoCurrencyTests {

    @Mock
    private  BinanceService binanceService;
    @Autowired
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

    @Test
    void checkListCotizationsOfCrypto() {
        cryptoService.deleteAllCotizationsOf("TRXUSDT");
        String dateNow = CurrentDateAndTime.getNewDateAsString();

        CryptoCurrency trxRegister1 = CryptoCurrencyFactory.createWithSymbolAndPrice("TRXUSDT", 2.48);
        CryptoCurrency trxRegister2 = CryptoCurrencyFactory.createWithSymbolAndPrice("TRXUSDT", 2.70);
        CryptoCurrency trxRegister3 = CryptoCurrencyFactory.createWithSymbolAndPrice("TRXUSDT", 2.65);
        trxRegister1.setLastUpdateDateAndTime(CurrentDateAndTime.previousTimeAs(dateNow, 32));
        trxRegister2.setLastUpdateDateAndTime(CurrentDateAndTime.previousTimeAs(dateNow, 15));
        trxRegister3.setLastUpdateDateAndTime(CurrentDateAndTime.previousTimeAs(dateNow, 12));

        System.out.println(trxRegister2.getLastUpdateDateAndTime());

        cryptoCurrencyRepo.save(trxRegister1);
        cryptoCurrencyRepo.save(trxRegister2);
        cryptoCurrencyRepo.save(trxRegister3);

        CryptoFormCotization cryptoFormObtained = cryptoService.getLatestCotizationsOf("TRXUSDT");

        Assertions.assertEquals("TRXUSDT", cryptoFormObtained.getCryptoSymbol());
        Assertions.assertEquals(2, cryptoFormObtained.getListCotizations().size());
        Assertions.assertTrue(cryptoFormObtained.getListCotizations()
                                .stream().map(entry -> entry.getCotization())
                                    .allMatch(cotization -> List.of(2.70,2.65).contains(cotization)));

    }

}
