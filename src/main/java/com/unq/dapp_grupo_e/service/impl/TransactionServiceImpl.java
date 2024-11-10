package com.unq.dapp_grupo_e.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.unq.dapp_grupo_e.controller.dto.TransactionFormDTO;
import com.unq.dapp_grupo_e.controller.dto.TransactionProcessedDTO;
import com.unq.dapp_grupo_e.controller.dto.TransactionResponseDTO;
import com.unq.dapp_grupo_e.controller.dto.TransactionSummaryDTO;
import com.unq.dapp_grupo_e.model.CryptoActive;
import com.unq.dapp_grupo_e.model.CryptoCurrencyEnum;
import com.unq.dapp_grupo_e.model.CryptoVolume;
import com.unq.dapp_grupo_e.model.Transaction;
import com.unq.dapp_grupo_e.model.TransactionStatus;
import com.unq.dapp_grupo_e.model.User;
import com.unq.dapp_grupo_e.model.exceptions.InvalidCryptoPriceOffer;
import com.unq.dapp_grupo_e.model.exceptions.TransactionNotFundException;
import com.unq.dapp_grupo_e.model.exceptions.UserNotFoundException;
import com.unq.dapp_grupo_e.repository.TransactionRepository;
import com.unq.dapp_grupo_e.repository.UserRepository;
import com.unq.dapp_grupo_e.service.BinanceService;
import com.unq.dapp_grupo_e.service.DolarApiService;
import com.unq.dapp_grupo_e.service.TransactionService;
import com.unq.dapp_grupo_e.utilities.CurrentDateAndTime;


@Service
public class TransactionServiceImpl implements TransactionService  {

    private final DolarApiService dolarApiService;
    private final BinanceService binanceService;
    private final TransactionRepository transactionRepo;
    private final UserRepository userRepository;

    public TransactionServiceImpl(DolarApiService dolarApiService, BinanceService binanceService, 
            TransactionRepository transactionRepo, UserRepository userRepository) {
        this.dolarApiService = dolarApiService;
        this.binanceService = binanceService;
        this.transactionRepo = transactionRepo;
        this.userRepository = userRepository;
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
    public List<TransactionResponseDTO> getAllTransactions() {
        ArrayList<TransactionResponseDTO> transactions = new ArrayList<>();
        for(Transaction transaction:transactionRepo.findAllActiveTransaction()) {
            User userTransaction = userRepository.findById(transaction.getIdUser())
                        .orElseThrow(() -> new NoSuchElementException("Invalid transaction registered, please try again later"));
            transactions.add(TransactionResponseDTO.from(transaction, userTransaction));
        }
        return transactions;
    }

    @Override
    public Transaction getTransaction(Integer transactionId) {
        Transaction transactionDTO =  transactionRepo.findById(transactionId)
                                        .orElseThrow(() -> new TransactionNotFundException());
        return transactionDTO;
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

    public boolean isAValidPriceCurently(Transaction transaction, Double currentPrice) {
        var cotizationDolar = dolarApiService.getDolarCotization();
        if (transaction.getOperationType().equals("SELL")) {
            return transaction.getPriceOffered() > (currentPrice * cotizationDolar);
        } else {
            return transaction.getPriceOffered() < (currentPrice * cotizationDolar);
        }
    }

    public void processValidationOfIntention(Transaction transaction) {
        var actualCotization = binanceService.getCrypto(transaction.getSymbolTrade()).getPrice();
        if (!isAValidPriceCurently(transaction, actualCotization)) {
            transaction.setStatus(TransactionStatus.CANCELLED);
            transactionRepo.save(transaction);
            throw new InvalidCryptoPriceOffer("The current price of the intention is not valid for a transaction, it won't appear again as an option for trading");
        }
    }

    @Override
    public TransactionProcessedDTO processTransfer(Integer transactionId) {
        Transaction transactionTransfer = transactionRepo.findById(transactionId)
            .orElseThrow(() -> new TransactionNotFundException());
        User userOfTransfer = userRepository.findById(transactionTransfer.getIdUser())
            .orElseThrow(() -> new UserNotFoundException());

        return TransactionProcessedDTO.from(transactionTransfer, userOfTransfer);
    }

    @Override
    public void transferToIntention(Integer transactionId, Integer userId) {
        Transaction transactionTransfer = transactionRepo.findById(transactionId)
            .orElseThrow(() -> new TransactionNotFundException());
        this.processValidationOfIntention(transactionTransfer);
        transactionTransfer.realizeTransfer(userId);
        transactionRepo.save(transactionTransfer);
    }

    @Override
    public void cancelTransaction(Integer transactionId, Integer userId) {
        Transaction transactionToCancel = transactionRepo.findById(transactionId)
            .orElseThrow(() -> new TransactionNotFundException());
        User userCancelling = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException());

        transactionToCancel.cancelTransaction(userId);
        userCancelling.discountReputation();
        transactionRepo.save(transactionToCancel);
        userRepository.save(userCancelling);
    }

    @Override
    public void confirmTransaction(Integer transactionId, Integer userId) {
        Transaction transactionToConfirm = transactionRepo.findById(transactionId)
            .orElseThrow(() -> new TransactionNotFundException());
        User userConfirming = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException());

        transactionToConfirm.confirmTransactionDone(userId);

        User userAnsweringIntent = userRepository.findById(transactionToConfirm.getIdUserAnswering())
            .orElseThrow(() -> new UserNotFoundException());
        String dateTransaction = transactionToConfirm.getDateTimeCreated();
        if (CurrentDateAndTime.achieveRequirementOfTransacion(dateTransaction)) {
            userConfirming.addReputation(10);
            userAnsweringIntent.addReputation(10);
        } else {
            userConfirming.addReputation(5);
            userAnsweringIntent.addReputation(5);
        }
        userConfirming.countANewOperation();
        userAnsweringIntent.countANewOperation();
        
        transactionRepo.save(transactionToConfirm);
        userRepository.save(userConfirming);
    }

    
    
    
}
