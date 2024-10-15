package com.unq.dapp_grupo_e.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unq.dapp_grupo_e.controller.dto.TransactionFormDTO;
import com.unq.dapp_grupo_e.model.Transaction;
import com.unq.dapp_grupo_e.service.TransactionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Tag( name = "Transactions", description = "Methods for transactions of CryptoAPI")
@RequestMapping("/transaction")
@RestController
public class TransactionControllerRest {

    private final TransactionService transactionService;

    public TransactionControllerRest(TransactionService transactionService) {
        this.transactionService = transactionService;
    }
    
    @ApiResponse(responseCode = "201", description = "New transaction registered")
    @ApiResponse(responseCode = "400", description = "Required field was empty", content = @Content)
    @Operation(summary = "Create a new transaction",
               description = "Create a new intention of transaction, either 'Sell' or 'Buy' operation, with the described data")
    @PostMapping("/create")
    public ResponseEntity<Transaction> createNewTransaction(@Valid @RequestBody TransactionFormDTO entity) {
        var response = transactionService.createTransaction(entity);
        return ResponseEntity.ok(response);
    }

    @ApiResponse(responseCode = "200")
    @ApiResponse(responseCode = "400", content = @Content)
    @Operation(summary = "Consult all the transactions",
               description = "Consult all the transactions registered available for all the different crypto currencies")
    @GetMapping("/getAll")
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        var response = transactionService.getAllTransactions();
        return ResponseEntity.ok(response);
    }
    
}
