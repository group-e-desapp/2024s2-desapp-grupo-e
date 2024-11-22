package com.unq.dapp_grupo_e.modelTests;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.unq.dapp_grupo_e.dto.TransactionFormDTO;
import com.unq.dapp_grupo_e.dto.TransactionProcessedDTO;
import com.unq.dapp_grupo_e.dto.UserRegisterDTO;
import com.unq.dapp_grupo_e.exceptions.InvalidActionException;
import com.unq.dapp_grupo_e.exceptions.InvalidCryptoPriceOffer;
import com.unq.dapp_grupo_e.factory.CryptoCurrencyFactory;
import com.unq.dapp_grupo_e.factory.factorydto.TransactionFormFactory;
import com.unq.dapp_grupo_e.factory.factorydto.UserRegisterFactory;
import com.unq.dapp_grupo_e.model.CryptoCurrency;
import com.unq.dapp_grupo_e.model.Transaction;
import com.unq.dapp_grupo_e.model.TransactionStatus;
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
class TransactionActionTests {

    @Mock
    private BinanceService binanceService;

    @Mock
    private DolarApiService dolarApiService;

    @Autowired
    private TransactionRepository transactionRepo;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;
    @Autowired
    private AuthService authUserService;

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
        transactionService.deleteAllTransactions();
        userService.deleteAllUsers();
    }

    @Test
    void checkCorrectInitialStatusOfTransaction () {

        TransactionFormDTO transactionForm = TransactionFormFactory.createFullData("ADAUSDT", 33f, 
                                                                                      104.3, "Sell");

        CryptoCurrency cryptoMock = CryptoCurrencyFactory.createWithSymbolAndPrice("ADAUSDT", 0.36d);

        when(binanceService.getCrypto("ADAUSDT")).thenReturn(cryptoMock);
        when(dolarApiService.getDolarCotization()).thenReturn(300.0);

        Transaction transactionSaved = transactionService.createTransaction(transactionForm);

        Assertions.assertEquals(TransactionStatus.ACTIVE , transactionSaved.getStatus());
    }

    @Test
    void checkProcessTransactionResponseStaticInformation () {

        TransactionFormDTO transactionForm = TransactionFormFactory
                                                .createFullData("ADAUSDT", 33f, 104.3, "SELL");
        UserRegisterDTO userForm = UserRegisterFactory.createWithNameAndSurname("Mark", "Anon");
        
        CryptoCurrency cryptoMock = CryptoCurrencyFactory.createWithSymbolAndPrice("ADAUSDT", 0.36d);
        when(binanceService.getCrypto("ADAUSDT")).thenReturn(cryptoMock);
        when(dolarApiService.getDolarCotization()).thenReturn(300.0);

        authUserService.register(userForm);
        transactionService.createTransaction(transactionForm);

        TransactionProcessedDTO transactionProcessed = transactionService.processTransfer(1);

        Assertions.assertEquals("ADAUSDT", transactionProcessed.getCryptoSymbol());
        Assertions.assertEquals("Mark Anon", transactionProcessed.getUserFullName());
    }

    @Test
    void checkProcessedSellTransaction() {
        TransactionFormDTO transactionForm = TransactionFormFactory
                                                .createFullData("NEOUSDT", 33f, 110.3, "SELL");
        UserRegisterDTO userForm = UserRegisterFactory.createWithCVU("1234567890123456789012");
        
        CryptoCurrency cryptoMock = CryptoCurrencyFactory.createWithSymbolAndPrice("NEOUSDT", 0.36d);
        when(binanceService.getCrypto("NEOUSDT")).thenReturn(cryptoMock);
        when(dolarApiService.getDolarCotization()).thenReturn(300.0);

        authUserService.register(userForm);
        transactionService.createTransaction(transactionForm);

        TransactionProcessedDTO transactionProcessed = transactionService.processTransfer(1);

        Assertions.assertEquals("Realize transfer", transactionProcessed.getAction());
        Assertions.assertEquals("1234567890123456789012", transactionProcessed.getAddressForTransaction());
    }

    @Test
    void checkProcessedBuyTransaction() {
        TransactionFormDTO transactionForm = TransactionFormFactory
                                                .createFullData("NEOUSDT", 33f, 105.3, "BUY");
        UserRegisterDTO userForm = UserRegisterFactory.createWitWalletAddress("YY158851");
        
        CryptoCurrency cryptoMock = CryptoCurrencyFactory.createWithSymbolAndPrice("NEOUSDT", 0.36d);
        when(binanceService.getCrypto("NEOUSDT")).thenReturn(cryptoMock);
        when(dolarApiService.getDolarCotization()).thenReturn(300.0);

        authUserService.register(userForm);
        transactionService.createTransaction(transactionForm);

        TransactionProcessedDTO transactionProcessed = transactionService.processTransfer(1);

        Assertions.assertEquals("Accept offer for crypto value", transactionProcessed.getAction());
        Assertions.assertEquals("YY158851", transactionProcessed.getAddressForTransaction());
    }

    @Test
    void userRealizeTransferForSellTransactionCreatedByFirstUser() {

        TransactionFormDTO transactionForm = TransactionFormFactory
                                                .createFullData("NEOUSDT", 33f, 111.3, "SELL");
        CryptoCurrency cryptoMock = CryptoCurrencyFactory.createWithSymbolAndPrice("NEOUSDT", 0.36d);
        when(binanceService.getCrypto("NEOUSDT")).thenReturn(cryptoMock);
        when(dolarApiService.getDolarCotization()).thenReturn(300.0);

        UserRegisterDTO firstUser = UserRegisterFactory.anyUserRegister();
        UserRegisterDTO secondUser = UserRegisterFactory.createWithEmail("secondUser@mail.com");
        authUserService.register(firstUser);
        authUserService.register(secondUser);
        transactionService.createTransaction(transactionForm);
        transactionService.transferToIntention(1, 2);

        var transactionRecovered = transactionService.getTransaction(1);

        Assertions.assertEquals(TransactionStatus.TRANSFERING, transactionRecovered.getStatus());
    }

    @Test
    void userRealizeTransferForBuyTransactionCreatedByFirstUser() {

        TransactionFormDTO transactionForm = TransactionFormFactory
                                                .createFullData("NEOUSDT", 33f, 105.4, "BUY");
        CryptoCurrency cryptoMock = CryptoCurrencyFactory.createWithSymbolAndPrice("NEOUSDT", 0.36d);
        when(binanceService.getCrypto("NEOUSDT")).thenReturn(cryptoMock);
        when(dolarApiService.getDolarCotization()).thenReturn(300.0);

        UserRegisterDTO firstUser = UserRegisterFactory.anyUserRegister();
        UserRegisterDTO secondUser = UserRegisterFactory.createWithEmail("secondUser@mail.com");
        authUserService.register(firstUser);
        authUserService.register(secondUser);
        transactionService.createTransaction(transactionForm);
        transactionService.transferToIntention(1, 2);

        var transactionRecovered = transactionService.getTransaction(1);

        Assertions.assertEquals(TransactionStatus.TRANSFERING, transactionRecovered.getStatus());
    }

    @Test
    void userConfirmsTransactionAfterTransferRealized() {

        TransactionFormDTO transactionForm = TransactionFormFactory
                                                .createFullData("NEOUSDT", 33f, 111.3, "SELL");
        CryptoCurrency cryptoMock = CryptoCurrencyFactory.createWithSymbolAndPrice("NEOUSDT", 0.36d);
        when(binanceService.getCrypto("NEOUSDT")).thenReturn(cryptoMock);
        when(dolarApiService.getDolarCotization()).thenReturn(300.0);

        UserRegisterDTO firstUser = UserRegisterFactory.anyUserRegister();
        UserRegisterDTO secondUser = UserRegisterFactory.createWithEmail("secondUser@mail.com");
        authUserService.register(firstUser);
        authUserService.register(secondUser);
        transactionService.createTransaction(transactionForm);
        transactionService.transferToIntention(1, 2);
        transactionService.confirmTransaction(1,1);

        var transactionRecovered = transactionService.getTransaction(1);

        Assertions.assertEquals(TransactionStatus.CLOSE, transactionRecovered.getStatus());
    }

    @Test
    void userCancelsItsOwnTransaction() {

        TransactionFormDTO transactionForm = TransactionFormFactory
                                                .createFullData("NEOUSDT", 33f, 111.3, "SELL");
        CryptoCurrency cryptoMock = CryptoCurrencyFactory.createWithSymbolAndPrice("NEOUSDT", 0.36d);
        when(binanceService.getCrypto("NEOUSDT")).thenReturn(cryptoMock);
        when(dolarApiService.getDolarCotization()).thenReturn(300.0);

        UserRegisterDTO cancellingUser = UserRegisterFactory.anyUserRegister();
        authUserService.register(cancellingUser);
        transactionService.createTransaction(transactionForm);
        transactionService.cancelTransaction(1, 1);

        var transactionRecovered = transactionService.getTransaction(1);

        Assertions.assertEquals(TransactionStatus.CANCELLED, transactionRecovered.getStatus());
    }

    @Test
    void userAnsweringCancelsTheTransactionAndGetsActive() {

        TransactionFormDTO transactionForm = TransactionFormFactory
                                                .createFullData("NEOUSDT", 33f, 111.3, "SELL");
        CryptoCurrency cryptoMock = CryptoCurrencyFactory.createWithSymbolAndPrice("NEOUSDT", 0.36d);
        when(binanceService.getCrypto("NEOUSDT")).thenReturn(cryptoMock);
        when(dolarApiService.getDolarCotization()).thenReturn(300.0);

        UserRegisterDTO userOfTransaction = UserRegisterFactory.anyUserRegister();
        UserRegisterDTO userOfActions = UserRegisterFactory.createWithEmail("otherUser@mail.com");
        authUserService.register(userOfTransaction);
        authUserService.register(userOfActions);
        transactionService.createTransaction(transactionForm);
        transactionService.transferToIntention(1, 2);
        transactionService.cancelTransaction(1, 2);

        var transactionRecovered = transactionService.getTransaction(1);

        Assertions.assertEquals(TransactionStatus.ACTIVE, transactionRecovered.getStatus());
    }

    @Test
    void exceptionForUserTransferingtoOwnIntention() {
        TransactionFormDTO transactionForm = TransactionFormFactory
                                                .createFullData("NEOUSDT", 33f, 111.3, "SELL");
        CryptoCurrency cryptoMock = CryptoCurrencyFactory.createWithSymbolAndPrice("NEOUSDT", 0.36d);
        when(binanceService.getCrypto("NEOUSDT")).thenReturn(cryptoMock);
        when(dolarApiService.getDolarCotization()).thenReturn(300.0);

        UserRegisterDTO uniqueUser = UserRegisterFactory.anyUserRegister();
        authUserService.register(uniqueUser);
        transactionService.createTransaction(transactionForm);

        InvalidActionException error = Assertions.assertThrows(InvalidActionException.class, 
                                        () -> transactionService.transferToIntention(1, 1));
        Assertions.assertEquals("Not allowed to realize the transfer for your own intention created", error.getMessage());

    }

    @Test
    void exceptionForUserConfirmingAnActiveIntention() {
        TransactionFormDTO transactionForm = TransactionFormFactory
                                                .createFullData("NEOUSDT", 33f, 111.3, "SELL");
        CryptoCurrency cryptoMock = CryptoCurrencyFactory.createWithSymbolAndPrice("NEOUSDT", 0.36d);
        when(binanceService.getCrypto("NEOUSDT")).thenReturn(cryptoMock);
        when(dolarApiService.getDolarCotization()).thenReturn(300.0);

        UserRegisterDTO uniqueUser = UserRegisterFactory.anyUserRegister();
        authUserService.register(uniqueUser);
        transactionService.createTransaction(transactionForm);

        InvalidActionException error = Assertions.assertThrows(InvalidActionException.class, 
                                        () -> transactionService.confirmTransaction(1, 1));
        Assertions.assertEquals("Not allowed to confirm the transaction in its current state", error.getMessage());
    }

    @Test
    void exceptionForUserCancellingTransactionNotRelatedTo() {
        TransactionFormDTO transactionForm = TransactionFormFactory
                                                .createFullData("NEOUSDT", 33f, 111.3, "SELL");
        CryptoCurrency cryptoMock = CryptoCurrencyFactory.createWithSymbolAndPrice("NEOUSDT", 0.36d);
        when(binanceService.getCrypto("NEOUSDT")).thenReturn(cryptoMock);
        when(dolarApiService.getDolarCotization()).thenReturn(300.0);

        UserRegisterDTO userOfIntention = UserRegisterFactory.anyUserRegister();
        UserRegisterDTO userCancelling = UserRegisterFactory.createWithEmail("errorUser@mail.com");
        authUserService.register(userOfIntention);
        authUserService.register(userCancelling);
        transactionService.createTransaction(transactionForm);

        InvalidActionException error = Assertions.assertThrows(InvalidActionException.class, 
                                        () -> transactionService.cancelTransaction(1, 2));
        Assertions.assertEquals("Not allowed to cancel a transaction that you are not part of", error.getMessage());
    }

    @Test
    void exceptionForUserConfirmingTransactionCreatedByOtherUser() {
        TransactionFormDTO transactionForm = TransactionFormFactory
                                                .createFullData("NEOUSDT", 33f, 111.3, "SELL");
        CryptoCurrency cryptoMock = CryptoCurrencyFactory.createWithSymbolAndPrice("NEOUSDT", 0.36d);
        when(binanceService.getCrypto("NEOUSDT")).thenReturn(cryptoMock);
        when(dolarApiService.getDolarCotization()).thenReturn(300.0);

        UserRegisterDTO userOfIntention = UserRegisterFactory.anyUserRegister();
        UserRegisterDTO userConfirming = UserRegisterFactory.createWithEmail("errorUser@mail.com");
        authUserService.register(userOfIntention);
        authUserService.register(userConfirming);
        transactionService.createTransaction(transactionForm);

        InvalidActionException error = Assertions.assertThrows(InvalidActionException.class, 
                                        () -> transactionService.confirmTransaction(1, 2));
        Assertions.assertEquals("Not allowed to confirm a transaction that you didn't create", error.getMessage());
    }

    @Test
    void exceptionForUserTransferingToIntentionAlreadyInTransferingState() {
        TransactionFormDTO transactionForm = TransactionFormFactory
                                                .createFullData("NEOUSDT", 33f, 111.3, "SELL");
        CryptoCurrency cryptoMock = CryptoCurrencyFactory.createWithSymbolAndPrice("NEOUSDT", 0.36d);
        when(binanceService.getCrypto("NEOUSDT")).thenReturn(cryptoMock);
        when(dolarApiService.getDolarCotization()).thenReturn(300.0);

        UserRegisterDTO userOfIntention = UserRegisterFactory.anyUserRegister();
        UserRegisterDTO firstUserTransfer = UserRegisterFactory.createWithEmail("correctUser@mail.com");
        UserRegisterDTO secondtUserTransfer = UserRegisterFactory.createWithEmail("errorUser@mail.com");
        authUserService.register(userOfIntention);
        authUserService.register(firstUserTransfer);
        authUserService.register(secondtUserTransfer);
        transactionService.createTransaction(transactionForm);

        transactionService.transferToIntention(1, 2);

        InvalidActionException error = Assertions.assertThrows(InvalidActionException.class, 
                                        () -> transactionService.transferToIntention(1, 3));
        Assertions.assertEquals("Transaction is already in process or is not available for interaction", error.getMessage());
    }

    @Test
    void exceptionForPriceOfferDuringTransferFromUserToTransactionCreated() {

        TransactionFormDTO transactionForm = TransactionFormFactory
                                            .createFullData("NEOUSDT", 33f, 107.3, "BUY");

        CryptoCurrency cryptoMock = CryptoCurrencyFactory.createWithSymbolAndPrice("NEOUSDT", 0.36d);
        when(binanceService.getCrypto("NEOUSDT")).thenReturn(cryptoMock);
        when(dolarApiService.getDolarCotization()).thenReturn(300.0);
        
        transactionService.createTransaction(transactionForm);

        UserRegisterDTO userOfIntention = UserRegisterFactory.anyUserRegister();
        UserRegisterDTO userTransfer = UserRegisterFactory.createWithEmail("correctUser@mail.com");
        authUserService.register(userOfIntention);
        authUserService.register(userTransfer);

        cryptoMock = CryptoCurrencyFactory.createWithSymbolAndPrice("NEOUSDT", 0.3d);
        when(binanceService.getCrypto("NEOUSDT")).thenReturn(cryptoMock);

        InvalidCryptoPriceOffer exception = Assertions.assertThrows(InvalidCryptoPriceOffer.class, 
                                                            () -> transactionService.transferToIntention(1, 2));
        
        Transaction transactionRecover = transactionRepo.findById(1).get();
        Assertions.assertEquals(TransactionStatus.CANCELLED, transactionRecover.getStatus());
        Assertions.assertEquals("The current price of the intention is not valid for a transaction, it won't appear again as an option for trading",
                                exception.getMessage());
    }

}