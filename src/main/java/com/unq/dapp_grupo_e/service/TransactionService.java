package com.unq.dapp_grupo_e.service;

import java.util.ArrayList;

import com.unq.dapp_grupo_e.controller.dto.TransactionFormDTO;
import com.unq.dapp_grupo_e.model.Transaction;

public interface TransactionService {


    Transaction createTransaction(TransactionFormDTO transactionFrom);

    ArrayList<Transaction> getAllTransactions();

    void deleteAllTransactions();
}
