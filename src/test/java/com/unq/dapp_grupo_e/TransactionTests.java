package com.unq.dapp_grupo_e;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.unq.dapp_grupo_e.factories.TransactionFactory;
import com.unq.dapp_grupo_e.model.Transaction;

@ActiveProfiles("test") 
@SpringBootTest
class TransactionTests {

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
    void checkOfTotalSumOfOperation() {
        Transaction transaction = TransactionFactory.createWithPriceAndNominalValue((double) 520.0, (float) 10.0);
        Assertions.assertEquals((float) 5200.0, transaction.totalSumOfOperation());
    }
    
}
