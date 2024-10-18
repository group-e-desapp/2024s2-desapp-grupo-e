package com.unq.dapp_grupo_e.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.unq.dapp_grupo_e.controller.dto.TransactionFormDTO;
import com.unq.dapp_grupo_e.controller.dto.TransactionSummaryDTO;
import com.unq.dapp_grupo_e.model.CryptoActive;
import com.unq.dapp_grupo_e.model.CryptoCurrencyEnum;
import com.unq.dapp_grupo_e.model.CryptoVolume;
import com.unq.dapp_grupo_e.model.Transaction;
import com.unq.dapp_grupo_e.model.exceptions.InvalidCryptoPriceOffer;
import com.unq.dapp_grupo_e.repository.TransactionRepository;
import com.unq.dapp_grupo_e.service.BinanceService;
import com.unq.dapp_grupo_e.service.DolarApiService;
import com.unq.dapp_grupo_e.service.TransactionService;
import com.unq.dapp_grupo_e.utilities.CurrentDateAndTime;


@Service
public class TransactionServiceImpl implements TransactionService  {

    private final DolarApiService dolarApiService;
    private final BinanceService binanceService;
    private final TransactionRepository transactionRepo;

    public TransactionServiceImpl(DolarApiService dolarApiService, BinanceService binanceService, TransactionRepository transactionRepo) {
        this.dolarApiService = dolarApiService;
        this.binanceService = binanceService;
        this.transactionRepo = transactionRepo;
    }

    @Override
    public Transaction createTransaction(TransactionFormDTO transactionForm) {
        var symbolCrypto = transactionForm.symbolCrypto;
        CryptoCurrencyEnum.validateCrypto(symbolCrypto);
        var cryptoPrice = binanceService.getCrypto(symbolCrypto).getPrice();

        Double cotizationToARS = dolarApiService.getDolarCotization();
        Transaction transaction = transactionForm.toModel();
        if (!transaction.isAValidMarginForTransaction(cryptoPrice * cotizationToARS)) {
            throw new InvalidCryptoPriceOffer("Price is not acceptable within the margin expected for the currency");
        } else {
            String actualTimeAndDate = CurrentDateAndTime.getNewDateAsString();
            transaction.setDateTimeCreated(actualTimeAndDate);
            return transactionRepo.save(transaction);
        }
    }

    @Override
    public ArrayList<Transaction> getAllTransactions() {
        ArrayList<Transaction> transactions = new ArrayList<>();
        transactionRepo.findAll().forEach(transactions::add);
        return transactions;
    }

    @Override
    public void deleteAllTransactions() {
        transactionRepo.deleteAll();
        transactionRepo.resetIdTransaction();
    }

    @Override
    public CryptoVolume getCryptoVolumeOfUserBetweenDates(Integer userId, String startDate, String endDate) {
        CryptoVolume responseVolume = new CryptoVolume();

        List<TransactionSummaryDTO> listCryptoActives = transactionRepo.getTotalNominalValuesOfUserBetweenDates(userId, startDate, endDate);
        Double cotizationToARS = dolarApiService.getDolarCotization();
        System.out.println(listCryptoActives.size());
        for (TransactionSummaryDTO active:listCryptoActives) {
            Double cryptoPrice = binanceService.getCrypto(active.getSymbolTrade()).getPrice();
            responseVolume.addCryptoActive(new CryptoActive(active.getSymbolTrade(), 
                                                            active.getTotalNominalValue(), 
                                                            cryptoPrice * cotizationToARS));
        }
        responseVolume.calculateTotalInARS();
        responseVolume.calculateTotalInUSD(cotizationToARS);
        
        return responseVolume;

    }
    
}
