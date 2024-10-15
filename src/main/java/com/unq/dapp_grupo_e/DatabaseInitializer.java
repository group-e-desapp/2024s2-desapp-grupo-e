package com.unq.dapp_grupo_e;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.unq.dapp_grupo_e.model.CryptoCurrencyList;
import com.unq.dapp_grupo_e.model.Transaction;
import com.unq.dapp_grupo_e.model.User;
import com.unq.dapp_grupo_e.repository.TransactionRepository;
import com.unq.dapp_grupo_e.repository.UserRepository;
import com.unq.dapp_grupo_e.service.CryptoCurrencyService;
import com.unq.dapp_grupo_e.service.DolarApiService;
import com.unq.dapp_grupo_e.service.UserService;

@Configuration
public class DatabaseInitializer {

    @Bean
    CommandLineRunner initializeDatabase(UserService userService, 
                                        UserRepository userRepo, 
                                        TransactionRepository transactionRepo,
                                        CryptoCurrencyService cryptoService,
                                        DolarApiService dolarApiService) {

        return args -> {

            userService.deleteUsers();
            userService.resetIdUser();
            transactionRepo.deleteAll();

            User user1 = new User((long) 1, 
                                "Ann", "Martie", 
                                "ann85@mail.com", 
                                "Name#85", 
                                "1512196481521231516250", 
                                "AA126547", 
                                12, 50); 
            userRepo.save(user1);

            User user2 = new User((long) 2, 
                                "Mark", "Johansenn", 
                                "mark25@mail.com", 
                                "Name#25", 
                                "3434523437365251516250", 
                                "AB421321", 
                                4, 30); 
            userRepo.save(user2);

            User user3 = new User((long) 3, 
                                "Maka", "Maple", 
                                "maka91@mail.com", 
                                "Name#91", 
                                "5145846152221231516250", 
                                "AD645941", 
                                20, 110); 
            userRepo.save(user3);

            User user4 = new User((long) 4, 
                                "John", "Mirk", 
                                "john75@mail.com", 
                                "Name#75", 
                                "4415159663639984521251", 
                                "AE156437", 
                                16, 80); 
            userRepo.save(user4);

            User user5 = new User((long) 5, 
                                "Frank", "Barp", 
                                "frank21@mail.com", 
                                "Name#21", 
                                "3226598645795641521121", 
                                "AG689556", 
                                8, 45); 
            userRepo.save(user5);


            cryptoService.getCryptoValue("DOTUSDT").getPrice();
            cryptoService.getCryptoValue("ATOMUSDT").getPrice();
            cryptoService.getCryptoValue("ADAUSDT").getPrice();
            cryptoService.getCryptoValue("AXSUSDT").getPrice();
            cryptoService.getCryptoValue("BTCUSDT").getPrice();
            cryptoService.getCryptoValue("NEOUSDT").getPrice();
            cryptoService.getCryptoValue("MATICUSDT").getPrice();

            CryptoCurrencyList cryptoList = cryptoService.getAllCryptoValues();
            Double cotizationARS = dolarApiService.getDolarCotization();
            Double priceDOTUSDT = cryptoList.getCrypto("DOTUSDT").getPrice() * cotizationARS;
            Double priceATOMUSDT = cryptoList.getCrypto("ATOMUSDT").getPrice() * cotizationARS;
            Double priceADAUSDT = cryptoList.getCrypto("ADAUSDT").getPrice() * cotizationARS;
            Double priceAXSUSDT = cryptoList.getCrypto("AXSUSDT").getPrice() * cotizationARS;
            Double priceBTCUSDT = cryptoList.getCrypto("BTCUSDT").getPrice() * cotizationARS;
            Double priceNEOUSDT = cryptoList.getCrypto("NEOUSDT").getPrice() * cotizationARS;
            Double priceMATICUSDT = cryptoList.getCrypto("MATICUSDT").getPrice() * cotizationARS;

            Transaction transaction1 = new Transaction((long) 1, 3, 
                                                    "DOTUSDT", 
                                                    (float) 20, 
                                                    (priceDOTUSDT * 1.03), 
                                                    "Sell");
            transactionRepo.save(transaction1);

            Transaction transaction2 = new Transaction((long) 2, 1, 
                                                    "ATOMUSDT", 
                                                    (float) 20, 
                                                    (priceATOMUSDT * 1.028), 
                                                    "Sell");
            transactionRepo.save(transaction2);

            Transaction transaction3 = new Transaction((long) 3, 2, 
                                                    "ADAUSDT", 
                                                    (float) 20, 
                                                    (priceADAUSDT * 0.98), 
                                                    "Buy");
            transactionRepo.save(transaction3);

            Transaction transaction4 = new Transaction((long) 4, 4, 
                                                    "AXSUSDT", 
                                                    (float) 20, 
                                                    (priceAXSUSDT * 1.019), 
                                                    "Sell");
            transactionRepo.save(transaction4);

            Transaction transaction5 = new Transaction((long) 5, 5, 
                                                    "DOTUSDT", 
                                                    (float) 20, 
                                                    (priceDOTUSDT * 1.041), 
                                                    "Sell");
            transactionRepo.save(transaction5);

            Transaction transaction6 = new Transaction((long) 6, 5, 
                                                    "ADAUSDT", 
                                                    (float) 20, 
                                                    (priceADAUSDT * 0.974), 
                                                    "Buy");
            transactionRepo.save(transaction6);

            Transaction transaction7 = new Transaction((long) 7, 2, 
                                                    "BTCUSDT", 
                                                    (float) 20, 
                                                    (priceBTCUSDT * 0.968), 
                                                    "Buy");
            transactionRepo.save(transaction7);

            Transaction transaction8 = new Transaction((long) 8, 2, 
                                                    "NEOUSDT", 
                                                    (float) 20, 
                                                    (priceNEOUSDT * 1.017), 
                                                    "Sell");
            transactionRepo.save(transaction8);

            Transaction transaction9 = new Transaction((long) 9, 3, 
                                                    "MATICUSDT", 
                                                    (float) 20, 
                                                    (priceMATICUSDT * 0.975), 
                                                    "Buy");
            transactionRepo.save(transaction9);

            Transaction transaction10 = new Transaction((long) 10, 1, 
                                                    "ATOMUSDT", 
                                                    (float) 20, 
                                                    (priceATOMUSDT * 1.033), 
                                                    "Sell");
            transactionRepo.save(transaction10);
        };

    }

    
}
