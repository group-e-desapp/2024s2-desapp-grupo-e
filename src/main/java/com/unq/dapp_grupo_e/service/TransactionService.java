package com.unq.dapp_grupo_e.service;

import com.unq.dapp_grupo_e.controller.dto.TransactionFormDTO;

public interface TransactionService {


    void createTransaction(TransactionFormDTO transactionFrom);

    
}
