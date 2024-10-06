package com.unq.dapp_grupo_e.service.impl;

import org.springframework.stereotype.Service;

import com.unq.dapp_grupo_e.controller.dto.TransactionFormDTO;
import com.unq.dapp_grupo_e.model.CryptoCurrencyEnum;
import com.unq.dapp_grupo_e.model.Transaction;
import com.unq.dapp_grupo_e.model.exceptions.InvalidCryptoPriceOffer;
import com.unq.dapp_grupo_e.repository.TransactionRepository;
import com.unq.dapp_grupo_e.service.BinanceService;
import com.unq.dapp_grupo_e.service.DolarApiService;
import com.unq.dapp_grupo_e.service.TransactionService;

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
    public void createTransaction(TransactionFormDTO transactionForm) {
        var symbolCrypto = transactionForm.symbolCrypto;
        CryptoCurrencyEnum.validateCrypto(symbolCrypto);
        var cryptoPrice = binanceService.getPrice(symbolCrypto).getPrice();

        Double cotizationToARS = dolarApiService.getDolarCotization();
        Transaction transaction = transactionForm.toModel();
        if (!transaction.isAValidMarginForTransaction(cryptoPrice * cotizationToARS)) {
            throw new InvalidCryptoPriceOffer("Price is not acceptable within the margin expected for the currency");
        } else {
            transactionRepo.save(transaction);
        }
    }
    
}
