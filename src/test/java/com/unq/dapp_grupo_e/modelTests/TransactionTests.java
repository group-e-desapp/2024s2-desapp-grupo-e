package com.unq.dapp_grupo_e.modelTests;

import static org.mockito.Mockito.when;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.unq.dapp_grupo_e.dto.TransactionFormDTO;
import com.unq.dapp_grupo_e.dto.TransactionResponseDTO;
import com.unq.dapp_grupo_e.dto.UserRegisterDTO;
import com.unq.dapp_grupo_e.exceptions.InvalidCryptoPriceOffer;
import com.unq.dapp_grupo_e.factory.CryptoCurrencyFactory;
import com.unq.dapp_grupo_e.factory.TransactionFactory;
import com.unq.dapp_grupo_e.factory.factoriesdto.TransactionFormFactory;
import com.unq.dapp_grupo_e.factory.factoriesdto.UserRegisterFactory;
import com.unq.dapp_grupo_e.model.CryptoCurrency;
import com.unq.dapp_grupo_e.model.Transaction;
import com.unq.dapp_grupo_e.repository.TransactionRepository;
import com.unq.dapp_grupo_e.repository.UserRepository;
import com.unq.dapp_grupo_e.service.AuthService;
import com.unq.dapp_grupo_e.service.BinanceService;
import com.unq.dapp_grupo_e.service.DolarApiService;
import com.unq.dapp_grupo_e.service.TransactionService;
import com.unq.dapp_grupo_e.service.UserService;
import com.unq.dapp_grupo_e.service.impl.TransactionServiceImpl;

@ActiveProfiles("test") 
@SpringBootTest
class TransactionTests {

    @Mock
    private BinanceService binanceService;

    @Mock
    private DolarApiService dolarApiService;

    @Autowired
    private TransactionRepository transactionRepo;

    @Autowired
    private UserRepository userRepository;

    private TransactionService transactionService;

    @Autowired
    private AuthService authUserService;
    @Autowired
    private UserService userService;

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

        TransactionFormDTO transactionForm = TransactionFormFactory.createFullData("ADAUSDT", 33f, 
                                                                                      250.5, "Sell");

        CryptoCurrency cryptoMock = CryptoCurrencyFactory.createWithSymbolAndPrice("ADAUSDT", 0.36d);

        when(dolarApiService.getDolarCotization()).thenReturn(300.0);
        when(binanceService.getCrypto("ADAUSDT")).thenReturn(cryptoMock);

        Assertions.assertThrows(InvalidCryptoPriceOffer.class, () -> transactionService.createTransaction(transactionForm));
    }

    @Test
    void checkValidTransactionWasSaved() {
        transactionService.deleteAllTransactions();

        TransactionFormDTO transactionForm = TransactionFormFactory.createFullData("ADAUSDT", 33f, 
                                                                                      104.3, "Sell");

        CryptoCurrency cryptoMock = CryptoCurrencyFactory.createWithSymbolAndPrice("ADAUSDT", 0.36d);

        when(binanceService.getCrypto("ADAUSDT")).thenReturn(cryptoMock);
        when(dolarApiService.getDolarCotization()).thenReturn(300.0);

        Transaction transactionSaved = transactionService.createTransaction(transactionForm);
        SimpleDateFormat formatApplied = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String currentDate = formatApplied.format(new Date());

        Assertions.assertEquals(1, transactionSaved.getIdExchange());
        Assertions.assertEquals(33, transactionSaved.getCryptoNominalValue());
        Assertions.assertEquals(104.3, transactionSaved.getPriceOffered());
        Assertions.assertEquals("Sell", transactionSaved.getOperationType());
        Assertions.assertEquals("ADAUSDT", transactionSaved.getSymbolTrade());
        Assertions.assertTrue(transactionSaved.getDateTimeCreated().contains(currentDate));
    }


    @Test
    void checkGettingOnlyActiveTransactions() {
        transactionService.deleteAllTransactions();
        userService.deleteAllUsers();

        TransactionFormDTO transactionFormBuy = TransactionFormFactory.createFullData("ADAUSDT", 33f, 
                                                                                      104.3, "BUY");

        TransactionFormDTO transactionFormSell = TransactionFormFactory.createFullData("ADAUSDT", 50f, 
                                                                                      110.3, "SELL");

        CryptoCurrency cryptoMock = CryptoCurrencyFactory.createWithSymbolAndPrice("ADAUSDT", 0.36d);

        when(binanceService.getCrypto("ADAUSDT")).thenReturn(cryptoMock);
        when(dolarApiService.getDolarCotization()).thenReturn(300.0);

        UserRegisterDTO uniqueUser = UserRegisterFactory.anyUserRegister();
        authUserService.register(uniqueUser);

        transactionService.createTransaction(transactionFormBuy);
        transactionService.createTransaction(transactionFormSell);

        transactionService.cancelTransaction(1, 1);

        List<TransactionResponseDTO> listRecovered = transactionService.getAllTransactions();

        Assertions.assertEquals(1, listRecovered.size());
        Assertions.assertEquals(2, listRecovered.get(0).getIdTransaction());
        Assertions.assertEquals("ADAUSDT", listRecovered.get(0).getCryptoSymbol());
        Assertions.assertEquals(110.3, listRecovered.get(0).getPriceOfferCotization());
        Assertions.assertEquals(50, listRecovered.get(0).getNominalValue());
    }

    
}
