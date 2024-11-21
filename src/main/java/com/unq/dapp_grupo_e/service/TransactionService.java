package com.unq.dapp_grupo_e.service;

import java.util.List;

import com.unq.dapp_grupo_e.dto.TransactionFormDTO;
import com.unq.dapp_grupo_e.dto.TransactionProcessedDTO;
import com.unq.dapp_grupo_e.dto.TransactionResponseDTO;
import com.unq.dapp_grupo_e.model.CryptoVolume;
import com.unq.dapp_grupo_e.model.Transaction;

public interface TransactionService {


    Transaction createTransaction(TransactionFormDTO transactionFrom);

    Transaction getTransaction(Integer transactionId);

    List<TransactionResponseDTO> getAllTransactions();

    void deleteAllTransactions();

    CryptoVolume getCryptoVolumeOfUserBetweenDates(Integer userId, String startDate, String endDate);

    TransactionProcessedDTO processTransfer(Integer transactionId);

    void cancelTransaction(Integer transactionId, Integer userId);

    void confirmTransaction(Integer transactionId, Integer userId);

    void transferToIntention(Integer transactionId, Integer userId);
}
