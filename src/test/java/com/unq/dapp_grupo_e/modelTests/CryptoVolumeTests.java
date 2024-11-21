package com.unq.dapp_grupo_e.modelTests;

import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.unq.dapp_grupo_e.dto.TransactionFormDTO;
import com.unq.dapp_grupo_e.factory.CryptoCurrencyFactory;
import com.unq.dapp_grupo_e.factory.factoriesdto.TransactionFormFactory;
import com.unq.dapp_grupo_e.model.CryptoActive;
import com.unq.dapp_grupo_e.model.CryptoCurrency;
import com.unq.dapp_grupo_e.model.CryptoVolume;
import com.unq.dapp_grupo_e.repository.TransactionRepository;
import com.unq.dapp_grupo_e.repository.UserRepository;
import com.unq.dapp_grupo_e.service.BinanceService;
import com.unq.dapp_grupo_e.service.DolarApiService;
import com.unq.dapp_grupo_e.service.TransactionService;
import com.unq.dapp_grupo_e.service.impl.TransactionServiceImpl;
import com.unq.dapp_grupo_e.utilities.CurrentDateAndTime;

@ActiveProfiles("test") 
@SpringBootTest
class CryptoVolumeTests {

    @Mock
    private BinanceService binanceService;

    @Mock
    private DolarApiService dolarApiService;

    @Autowired
    private TransactionRepository transactionRepo;

    @Autowired
    private UserRepository userRepository;

    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        transactionService = new TransactionServiceImpl(
            dolarApiService,
            binanceService,
            transactionRepo,
            userRepository
        );
    }

    @Test
    void checkCryptoActiveData() {
        CryptoActive cryptoAct = new CryptoActive("NEOUSDT", 10d, 4100d);
        Assertions.assertEquals("NEOUSDT", cryptoAct.getSymbol());
        Assertions.assertEquals(10d, cryptoAct.getTotalNominalValue());
        Assertions.assertEquals(4100d, cryptoAct.getCurrentCotization());
        Assertions.assertEquals(41000d, cryptoAct.getTotalCotizationARS());
    }

    @Test
    void checkCryptoVolumeWithCryptoActiveAdded() {
        CryptoActive cryptoNEO = new CryptoActive("NEOUSDT", 10d, 4100d);
        CryptoActive cryptoADA = new CryptoActive("ADAUSDT", 40d, 740d);
        CryptoVolume cryptoVolume = new CryptoVolume();

        cryptoVolume.addCryptoActive(cryptoADA);
        cryptoVolume.addCryptoActive(cryptoNEO);
        cryptoVolume.calculateTotalInARS();
        cryptoVolume.calculateTotalInUSD(200d);

        Assertions.assertEquals(70600, cryptoVolume.getTotalOperationARS());
        Assertions.assertEquals(353, cryptoVolume.getTotalOperationUSD());
        Assertions.assertEquals(2, cryptoVolume.getCryptoActives().size());
        Assertions.assertTrue(cryptoVolume.getCryptoActives().stream()
                    .allMatch(crypto -> List.of("NEOUSDT", "ADAUSDT").contains(crypto.getSymbol())));
    }

    @Test
    void checkCreationOfCryptoVolume() {
        transactionService.deleteAllTransactions();

        TransactionFormDTO transactionForm = TransactionFormFactory.createFullData("NEOUSDT", 15f, 
                                                                                    4105d, "Sell");

        TransactionFormDTO transactionForm2 = TransactionFormFactory.createFullData("BNBUSDT", 7f, 
                                                                                    243200d, "Buy");

        TransactionFormDTO transactionForm3 = TransactionFormFactory.createFullData("NEOUSDT", 20f, 
                                                                                    4075d, "Sell");

        CryptoCurrency mockNEOUSDT = CryptoCurrencyFactory.createWithSymbolAndPrice("NEOUSDT", 10.5d);
        CryptoCurrency mockBNBUSDT = CryptoCurrencyFactory.createWithSymbolAndPrice("BNBUSDT", 600d);

        try (MockedStatic<CurrentDateAndTime> mockedStatic = 
            Mockito.mockStatic(CurrentDateAndTime.class)) {

            when(binanceService.getCrypto("NEOUSDT")).thenReturn(mockNEOUSDT);
            when(binanceService.getCrypto("BNBUSDT")).thenReturn(mockBNBUSDT);
            when(dolarApiService.getDolarCotization()).thenReturn(400.0);
        
            mockedStatic.when(CurrentDateAndTime::getNewDateAsString)
                        .thenReturn("29/09/2024 12:15:00");
            
            transactionService.createTransaction(transactionForm);
            transactionService.createTransaction(transactionForm2);
            transactionService.createTransaction(transactionForm3);

            CryptoVolume responseVolume = transactionService
                                        .getCryptoVolumeOfUserBetweenDates(1, "20/09/2024 00:00:00", "15/10/2024 23:00:00");
            
            Map<String, Double> cryptoActives = responseVolume.getCryptoActives().stream()
                                                .collect(Collectors.toMap(CryptoActive::getSymbol, CryptoActive::getTotalNominalValue));

            Assertions.assertEquals(2, responseVolume.getCryptoActives().size());
            Assertions.assertEquals(35d, cryptoActives.get("NEOUSDT"));
            Assertions.assertEquals(7d, cryptoActives.get("BNBUSDT"));
            Assertions.assertEquals("29/09/2024 12:15:00", responseVolume.getDateTimeRequest());
            Assertions.assertEquals(1827000, responseVolume.getTotalOperationARS());
            Assertions.assertEquals(4567.5, responseVolume.getTotalOperationUSD());
        }                                                              
    }
    
}
