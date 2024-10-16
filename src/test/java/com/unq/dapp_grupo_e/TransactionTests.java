package com.unq.dapp_grupo_e;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.unq.dapp_grupo_e.controller.dto.TransactionFormDTO;
import com.unq.dapp_grupo_e.model.CryptoCurrency;
import com.unq.dapp_grupo_e.model.Transaction;
import com.unq.dapp_grupo_e.model.exceptions.InvalidCryptoPriceOffer;
import com.unq.dapp_grupo_e.repository.TransactionRepository;
import com.unq.dapp_grupo_e.service.BinanceService;
import com.unq.dapp_grupo_e.service.DolarApiService;
import com.unq.dapp_grupo_e.service.TransactionService;
import com.unq.dapp_grupo_e.service.impl.TransactionServiceImpl;
import com.unq.dapp_grupo_e.utilities.CurrentDateAndTime;
import com.unq.dapp_grupo_e.utilities.factories.CryptoCurrencyFactory;
import com.unq.dapp_grupo_e.utilities.factories.TransactionFactory;

@ActiveProfiles("test") 
@SpringBootTest
class TransactionTests {

    //@Autowired

    @Mock
    private BinanceService binanceService;

    @Mock
    private DolarApiService dolarApiService;

    @Autowired
    private TransactionRepository transactionRepo;

    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        transactionService = new TransactionServiceImpl(
            dolarApiService,
            binanceService,
            transactionRepo
        );
    }

    @Test
    void validationOfPriceOfferForTransactionLowerMargin() {
        Transaction transaction = TransactionFactory.createWithPrice((double) 103.4);
        Double currentCryptoPriceInARS = 108.0;
        Assertions.assertTrue(transaction.isAValidMarginForTransaction(currentCryptoPriceInARS));
    }

    @Test
    void validationOfPriceOfferForTransactionUpperMargin() {
        Transaction transaction = TransactionFactory.createWithPrice((double) 112.7);
        Double currentCryptoPriceInARS = 108.0;
        Assertions.assertTrue(transaction.isAValidMarginForTransaction(currentCryptoPriceInARS));
    }

    @Test
    void invalidPriceOfferForTransaction() {
        Transaction transaction = TransactionFactory.createWithPrice((double) 225.7);
        Double currentCryptoPriceInARS = 110.0;
        Assertions.assertFalse(transaction.isAValidMarginForTransaction(currentCryptoPriceInARS));
    }

    @Test
    void checkOfTotalSumOfOperation() {
        Transaction transaction = TransactionFactory.createWithPriceAndNominalValue((double) 520.0, (float) 10.0);
        Assertions.assertEquals(5200.0f, transaction.totalSumOfOperation());
    }

    @Test
    void invalidPriceOfferedForTransaction() {

        TransactionFormDTO transactionForm = new TransactionFormDTO();
        transactionForm.cryptoNominalValue = 33f;
        transactionForm.priceOffered = 250.5;
        transactionForm.symbolCrypto = "ADAUSDT";
        transactionForm.operationType = "Sell";

        CryptoCurrency cryptoMock = CryptoCurrencyFactory.createWithSymbolAndPrice("ADAUSDT", 0.36d);

        when(dolarApiService.getDolarCotization()).thenReturn(300.0);
        when(binanceService.getCrypto("ADAUSDT")).thenReturn(cryptoMock);

        Assertions.assertThrows(InvalidCryptoPriceOffer.class, () -> transactionService.createTransaction(transactionForm));
    }

    @Test
    void checkValidTransactionWasSaved() {
        transactionService.deleteAllTransactions();

        TransactionFormDTO transactionForm = new TransactionFormDTO();
        transactionForm.cryptoNominalValue = 33f;
        transactionForm.priceOffered = 104.3;
        transactionForm.symbolCrypto = "ADAUSDT";
        transactionForm.operationType = "Sell";

        CryptoCurrency cryptoMock = CryptoCurrencyFactory.createWithSymbolAndPrice("ADAUSDT", 0.36d);

        when(binanceService.getCrypto("ADAUSDT")).thenReturn(cryptoMock);
        when(dolarApiService.getDolarCotization()).thenReturn(300.0);

        Transaction transactionSaved = transactionService.createTransaction(transactionForm);
        String currentDate = CurrentDateAndTime.getNewDateAsString();

        Assertions.assertEquals(1, transactionSaved.getIdExchange());
        Assertions.assertEquals(33, transactionSaved.getCryptoNominalValue());
        Assertions.assertEquals(104.3, transactionSaved.getPriceOffered());
        Assertions.assertEquals("Sell", transactionSaved.getOperationType());
        Assertions.assertEquals("ADAUSDT", transactionSaved.getSymbolTrade());
        Assertions.assertEquals(currentDate, transactionSaved.getDateTimeCreated());
    }
    
}
