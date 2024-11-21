package com.unq.dapp_grupo_e;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.unq.dapp_grupo_e.factory.TransactionFactory;
import com.unq.dapp_grupo_e.factory.UserFactory;
import com.unq.dapp_grupo_e.model.CryptoCurrencyList;
import com.unq.dapp_grupo_e.model.Transaction;
import com.unq.dapp_grupo_e.model.User;
import com.unq.dapp_grupo_e.repository.TransactionRepository;
import com.unq.dapp_grupo_e.repository.UserRepository;
import com.unq.dapp_grupo_e.service.CryptoCurrencyService;
import com.unq.dapp_grupo_e.service.DolarApiService;
import com.unq.dapp_grupo_e.service.TransactionService;
import com.unq.dapp_grupo_e.service.UserService;

@Configuration
@Profile("prod")
public class DatabaseInitializer {


    @Bean
    public CommandLineRunner initializeDatabase(UserService userService,
                                        CryptoCurrencyService cryptoService,
                                        TransactionService transactionService,
                                        UserRepository userRepo,
                                        TransactionRepository transactionRepo,
                                        DolarApiService dolarApiService) {
        
        return args -> {

            userService.deleteAllUsers();
            transactionService.deleteAllTransactions();
            
            User user1 = UserFactory.createWithIdDataAndOperations(1, 
                                                                    "Ann", 
                                                                    "ann85@mail.com", 
                                                                    12, 
                                                                    50);
            userRepo.save(user1);

            User user2 = UserFactory.createWithIdDataAndOperations(2, 
                                                                    "Mark", 
                                                                    "mark25@mail.com", 
                                                                    4, 
                                                                    30);
            userRepo.save(user2);

            User user3 = UserFactory.createWithIdDataAndOperations(3, 
                                                                    "Maka", 
                                                                    "maka91@mail.com", 
                                                                    20, 
                                                                    110);
            userRepo.save(user3);

            User user4 = UserFactory.createWithIdDataAndOperations(4, 
                                                                    "John", 
                                                                    "john75@mail.com", 
                                                                    16, 
                                                                    80);
            userRepo.save(user4);

            User user5 = UserFactory.createWithIdDataAndOperations(5, 
                                                                    "Frank", 
                                                                    "frank21@mail.com", 
                                                                    8, 
                                                                    45);
            userRepo.save(user5);

            final String cryptoDOT = "DOTUSDT";
            final String cryptoATOM = "ATOMUSDT";
            final String cryptoADA = "ADAUSDT";
            final String cryptoAXS = "AXSUSDT";
            final String cryptoBTC = "BTCUSDT";
            final String cryptoNEO = "NEOUSDT";
            final String cryptoMATIC = "MATICUSDT";

            cryptoService.getCryptoValue(cryptoDOT).getPrice();
            cryptoService.getCryptoValue(cryptoATOM).getPrice();
            cryptoService.getCryptoValue(cryptoADA).getPrice();
            cryptoService.getCryptoValue(cryptoAXS).getPrice();
            cryptoService.getCryptoValue(cryptoBTC).getPrice();
            cryptoService.getCryptoValue(cryptoNEO).getPrice();
            cryptoService.getCryptoValue(cryptoMATIC).getPrice();

            CryptoCurrencyList cryptoList = cryptoService.getAllCryptoValues();
            Double cotizationARS = dolarApiService.getDolarCotization();
            Double priceDOTUSDT = cryptoList.getCrypto(cryptoDOT).getPrice() * cotizationARS;
            Double priceATOMUSDT = cryptoList.getCrypto(cryptoATOM).getPrice() * cotizationARS;
            Double priceADAUSDT = cryptoList.getCrypto(cryptoADA).getPrice() * cotizationARS;
            Double priceAXSUSDT = cryptoList.getCrypto(cryptoAXS).getPrice() * cotizationARS;
            Double priceBTCUSDT = cryptoList.getCrypto(cryptoBTC).getPrice() * cotizationARS;
            Double priceNEOUSDT = cryptoList.getCrypto(cryptoNEO).getPrice() * cotizationARS;
            Double priceMATICUSDT = cryptoList.getCrypto(cryptoMATIC).getPrice() * cotizationARS;

            Transaction transaction1 = TransactionFactory.createWithFullData(3, cryptoDOT, 
                                                            20f, (priceDOTUSDT * 1.03), 
                                                            "SELL", "17/10/2024 17:00:00");
            transactionRepo.save(transaction1);

            Transaction transaction2 = TransactionFactory.createWithFullData(1, cryptoATOM, 
                                                            20f, (priceATOMUSDT * 1.028), 
                                                            "SELL", "25/09/2024 13:00:00");
            transactionRepo.save(transaction2);


            Transaction transaction3 = TransactionFactory.createWithFullData(2, cryptoADA, 
                                                            30f, (priceADAUSDT * 1.027), 
                                                            "SELL", "21/09/2024 15:00:00");
            transactionRepo.save(transaction3);


            Transaction transaction4 = TransactionFactory.createWithFullData(4, cryptoAXS, 
                                                            25f, (priceAXSUSDT * 1.019), 
                                                            "SELL", "07/10/2024 00:00:00");
            transactionRepo.save(transaction4);

            Transaction transaction5 = TransactionFactory.createWithFullData(5, cryptoDOT, 
                                                            18f, (priceDOTUSDT * 1.041), 
                                                            "SELL", "19/09/2024 14:00:00");
            transactionRepo.save(transaction5);

            Transaction transaction6 = TransactionFactory.createWithFullData(5, cryptoNEO, 
                                                            20f, (priceNEOUSDT * 0.974), 
                                                            "BUY", "17/10/2024 09:00:00");
            transactionRepo.save(transaction6);

            Transaction transaction7 = TransactionFactory.createWithFullData(2, cryptoBTC, 
                                                            4f, (priceBTCUSDT * 0.961), 
                                                            "BUY", "12/10/2024 10:00:00");
            transactionRepo.save(transaction7);

            Transaction transaction8 = TransactionFactory.createWithFullData(2, cryptoADA, 
                                                            20f, (priceNEOUSDT * 1.027), 
                                                            "SELL", "10/10/2024 13:00:00");
            transactionRepo.save(transaction8);

            Transaction transaction9 = TransactionFactory.createWithFullData(3, cryptoMATIC, 
                                                            30f, (priceMATICUSDT * 0.98), 
                                                            "BUY", "28/09/2024 12:00:00");
            transactionRepo.save(transaction9);

            Transaction transaction10 = TransactionFactory.createWithFullData(1, cryptoATOM, 
                                                            28f, (priceATOMUSDT * 1.033), 
                                                            "SELL", "13/10/2024 09:00:00");
            transactionRepo.save(transaction10);

            Transaction transaction11 = TransactionFactory.createWithFullData(4, cryptoAXS, 
                                                            35f, (priceAXSUSDT * 1.024), 
                                                            "SELL", "11/10/2024 12:00:00");
            transactionRepo.save(transaction11);


            Transaction transactionInvalid = TransactionFactory.createWithFullData(3, cryptoMATIC, 
                                                            30f, (priceMATICUSDT * 1.275), 
                                                            "BUY", "28/09/2024 12:00:00");
            transactionRepo.save(transactionInvalid);
        };

    }

    
}
