package com.unq.dapp_grupo_e;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.unq.dapp_grupo_e.model.CryptoCurrencyList;
import com.unq.dapp_grupo_e.model.Transaction;
import com.unq.dapp_grupo_e.model.User;
import com.unq.dapp_grupo_e.repository.TransactionRepository;
import com.unq.dapp_grupo_e.repository.UserRepository;
import com.unq.dapp_grupo_e.service.CryptoCurrencyService;
import com.unq.dapp_grupo_e.service.DolarApiService;
import com.unq.dapp_grupo_e.service.UserService;
import com.unq.dapp_grupo_e.utilities.factories.UserFactory;

@Configuration
@Profile("prod")
public class DatabaseInitializer {

    @Bean
    public CommandLineRunner initializeDatabase(UserService userService,
                                        CryptoCurrencyService cryptoService,
                                        UserRepository userRepo,
                                        TransactionRepository transactionRepo,
                                        DolarApiService dolarApiService) {
        
        return args -> {

            userService.deleteUsers();
            userService.resetIdUser();
            transactionRepo.deleteAll();
            
            User user1 = UserFactory.createWithIdDataAndOperations((long) 1, 
                                                                    "Ann", 
                                                                    "ann85@mail.com", 
                                                                    12, 
                                                                    50);
            userRepo.save(user1);

            User user2 = UserFactory.createWithIdDataAndOperations((long) 2, 
                                                                    "Mark", 
                                                                    "mark25@mail.com", 
                                                                    4, 
                                                                    30);
            userRepo.save(user2);

            User user3 = UserFactory.createWithIdDataAndOperations((long) 3, 
                                                                    "Maka", 
                                                                    "maka91@mail.com", 
                                                                    20, 
                                                                    110);
            userRepo.save(user3);

            User user4 = UserFactory.createWithIdDataAndOperations((long) 4, 
                                                                    "John", 
                                                                    "john75@mail.com", 
                                                                    16, 
                                                                    80);
            userRepo.save(user4);

            User user5 = UserFactory.createWithIdDataAndOperations((long) 5, 
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

            Transaction transaction1 = new Transaction((long) 1, 3, 
                                                    cryptoDOT, 
                                                    (float) 20, 
                                                    (priceDOTUSDT * 1.03), 
                                                    "Sell");
            transactionRepo.save(transaction1);

            Transaction transaction2 = new Transaction((long) 2, 1, 
                                                    cryptoATOM, 
                                                    (float) 20, 
                                                    (priceATOMUSDT * 1.028), 
                                                    "Sell");
            transactionRepo.save(transaction2);

            Transaction transaction3 = new Transaction((long) 3, 2, 
                                                    cryptoADA, 
                                                    (float) 20, 
                                                    (priceADAUSDT * 0.98), 
                                                    "Buy");
            transactionRepo.save(transaction3);

            Transaction transaction4 = new Transaction((long) 4, 4, 
                                                    cryptoAXS, 
                                                    (float) 20, 
                                                    (priceAXSUSDT * 1.019), 
                                                    "Sell");
            transactionRepo.save(transaction4);

            Transaction transaction5 = new Transaction((long) 5, 5, 
                                                    cryptoDOT, 
                                                    (float) 20, 
                                                    (priceDOTUSDT * 1.041), 
                                                    "Sell");
            transactionRepo.save(transaction5);

            Transaction transaction6 = new Transaction((long) 6, 5, 
                                                    cryptoADA, 
                                                    (float) 20, 
                                                    (priceADAUSDT * 0.974), 
                                                    "Buy");
            transactionRepo.save(transaction6);

            Transaction transaction7 = new Transaction((long) 7, 2, 
                                                    cryptoBTC, 
                                                    (float) 20, 
                                                    (priceBTCUSDT * 0.968), 
                                                    "Buy");
            transactionRepo.save(transaction7);

            Transaction transaction8 = new Transaction((long) 8, 2, 
                                                    cryptoNEO, 
                                                    (float) 20, 
                                                    (priceNEOUSDT * 1.017), 
                                                    "Sell");
            transactionRepo.save(transaction8);

            Transaction transaction9 = new Transaction((long) 9, 3, 
                                                    cryptoMATIC, 
                                                    (float) 20, 
                                                    (priceMATICUSDT * 0.975), 
                                                    "Buy");
            transactionRepo.save(transaction9);

            Transaction transaction10 = new Transaction((long) 10, 1, 
                                                    cryptoATOM,
                                                    (float) 20, 
                                                    (priceATOMUSDT * 1.033), 
                                                    "Sell");
            transactionRepo.save(transaction10);
        };

    }

    
}
