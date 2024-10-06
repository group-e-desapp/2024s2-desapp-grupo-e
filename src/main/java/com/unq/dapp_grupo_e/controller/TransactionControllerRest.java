package com.unq.dapp_grupo_e.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unq.dapp_grupo_e.controller.dto.TransactionFormDTO;
import com.unq.dapp_grupo_e.service.TransactionService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RequestMapping("/transaction")
@RestController
public class TransactionControllerRest {

    private final TransactionService transactionService;

    public TransactionControllerRest(TransactionService transactionService) {
        this.transactionService = transactionService;
    }
    
    @PostMapping("/create")
    public ResponseEntity<Object> postMethodName(@Valid @RequestBody TransactionFormDTO entity) {
        transactionService.createTransaction(entity);
        return ResponseEntity.ok(entity);
    }
    
}
