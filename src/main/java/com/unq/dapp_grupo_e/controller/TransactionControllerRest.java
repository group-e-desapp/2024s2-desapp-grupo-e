package com.unq.dapp_grupo_e.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.unq.dapp_grupo_e.dto.TransactionFormDTO;
import com.unq.dapp_grupo_e.dto.TransactionProcessedDTO;
import com.unq.dapp_grupo_e.dto.TransactionResponseDTO;
import com.unq.dapp_grupo_e.model.CryptoVolume;
import com.unq.dapp_grupo_e.model.Transaction;
import com.unq.dapp_grupo_e.service.TransactionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Transactional
@SecurityRequirement(name = "Authorization")
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
    public ResponseEntity<List<TransactionResponseDTO>> getAllTransactions() {
        var response = transactionService.getAllTransactions();
        return ResponseEntity.ok(response);
    }

    @ApiResponse(responseCode = "200")
    @ApiResponse(responseCode = "400", content = @Content)
    @Operation(summary = "Consult the total operation volume of the given user",
        description = "Consult the total operation volumen of the user indicated with the operations set between the two dates given")
    @GetMapping("/getVolume")
    public ResponseEntity<CryptoVolume> getCryptoVolumeOfUser(@RequestParam int idUser, 
                                                              @RequestParam String startDate, 
                                                              @RequestParam String endDate) {
        var response = transactionService.getCryptoVolumeOfUserBetweenDates(idUser, startDate, endDate);
        return ResponseEntity.ok().body(response);
    }


    @ApiResponse(responseCode = "200")
    @ApiResponse(responseCode = "400", content = @Content)
    @Operation(summary = "Confirm the transaction chosen")
    @PutMapping("/confirm")
    public ResponseEntity<String> confirmTransaction(@RequestParam int idTransaction,
                                                     @RequestParam int idUser) {
        transactionService.confirmTransaction(idTransaction, idUser);
        return ResponseEntity.ok("Confirmation of transaction realized successfully");
    }

    @ApiResponse(responseCode = "200")
    @ApiResponse(responseCode = "400", content = @Content)
    @Operation(summary = "Cancel the transaction chosen")
    @PutMapping("/cancel")
    public ResponseEntity<String> cancelTransaction(@RequestParam Integer idTransaction,
                                                        @RequestParam Integer idUser) {
        transactionService.cancelTransaction(idTransaction, idUser);
        return ResponseEntity.ok("Cancel of operation realized successfully");
    }

    @ApiResponse(responseCode = "200")
    @ApiResponse(responseCode = "400", content = @Content)
    @Operation(summary = "Realise transfer for the transaction chosen")
    @PutMapping("/transfer")
    public ResponseEntity<String> transferTransaction(@RequestParam int idTransaction,
                                                          @RequestParam int idUser) {
        transactionService.transferToIntention(idTransaction, idUser);
        return ResponseEntity.ok("Transfer operation realized successfully");
    }

    @ApiResponse(responseCode = "200")
    @ApiResponse(responseCode = "400", content = @Content)
    @Operation(summary = "Process the transaction chosen")
    @GetMapping("/process")
    public ResponseEntity<TransactionProcessedDTO> processTransaction(@RequestParam int idTransaction) {
        var transactionResponse = transactionService.processTransfer(idTransaction);
        return ResponseEntity.ok().body(transactionResponse);
    }


    
}
