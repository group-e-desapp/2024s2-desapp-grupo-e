package com.unq.dapp_grupo_e.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unq.dapp_grupo_e.service.CryptoCurrencyService;

import io.swagger.v3.oas.annotations.Operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RequestMapping("/crypto")
@RestController
public class CryptoControllerRest {

    @Autowired
    private final CryptoCurrencyService cryptoService;


    public CryptoControllerRest(CryptoCurrencyService cryptoService) {
        this.cryptoService = cryptoService;
    }

    @Operation(summary = "Consult the current cotization of the crypto currency desired")
    @GetMapping("/price")
    public ResponseEntity<Object> getSymbolPrice(@RequestParam String symbol) {
        var valueResponse = cryptoService.getCryptoValue(symbol);
        return ResponseEntity.ok(valueResponse);
    }
    
    
    
}
