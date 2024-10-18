package com.unq.dapp_grupo_e.service;

import java.util.List;

import com.unq.dapp_grupo_e.controller.dto.TransactionFormDTO;
import com.unq.dapp_grupo_e.controller.dto.TransactionResponseDTO;
import com.unq.dapp_grupo_e.model.CryptoVolume;
import com.unq.dapp_grupo_e.model.Transaction;

public interface TransactionService {


    Transaction createTransaction(TransactionFormDTO transactionFrom);

    List<TransactionResponseDTO> getAllTransactions();

    void deleteAllTransactions();

    CryptoVolume getCryptoVolumeOfUserBetweenDates(Integer userId, String startDate, String endDate);
}
