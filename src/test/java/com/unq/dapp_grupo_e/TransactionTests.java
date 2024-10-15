package com.unq.dapp_grupo_e;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.unq.dapp_grupo_e.controller.dto.TransactionFormDTO;
import com.unq.dapp_grupo_e.model.Transaction;
import com.unq.dapp_grupo_e.service.TransactionService;
import com.unq.dapp_grupo_e.utilities.factories.TransactionFactory;

@ActiveProfiles("test") 
@SpringBootTest
class TransactionTests {

    @Autowired
    private TransactionService transactionService;

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
        Assertions.assertEquals((float) 5200.0, transaction.totalSumOfOperation());
    }

    @Test
    void checkTransactionDataSaved() {
        transactionService.deleteAllTransactions();

        TransactionFormDTO transactionForm = new TransactionFormDTO();
        transactionForm.cryptoNominalValue = (float) 33;
        transactionForm.priceOffered = 351.33;
        transactionForm.symbolCrypto = "ADAUSDT";
        transactionForm.operationType = "Sell";

        Transaction transactionSaved = transactionService.createTransaction(transactionForm);

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String currentDate = formatter.format(new Date());

        Assertions.assertEquals(1, transactionSaved.getIdExchange());
        Assertions.assertEquals(33, transactionSaved.getCryptoNominalValue());
        Assertions.assertEquals(351.33, transactionSaved.getPriceOffered());
        Assertions.assertEquals("Sell", transactionSaved.getOperationType());
        Assertions.assertEquals("ADAUSDT", transactionSaved.getSymbolTrade());
        Assertions.assertTrue(transactionSaved.getDateTimeCreated().contains(currentDate));
    }
    
}
