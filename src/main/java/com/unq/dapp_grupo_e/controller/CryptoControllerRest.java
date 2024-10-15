package com.unq.dapp_grupo_e.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unq.dapp_grupo_e.model.CryptoCurrency;
import com.unq.dapp_grupo_e.model.CryptoCurrencyList;
import com.unq.dapp_grupo_e.service.CryptoCurrencyService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Tag(name = "Crypto Currency", description = "Methods for the crypto currency of the CryptoAPI")
@RequestMapping("/crypto")
@RestController
public class CryptoControllerRest {

    private final CryptoCurrencyService cryptoService;

    
    public CryptoControllerRest(CryptoCurrencyService cryptoService) {
        this.cryptoService = cryptoService;
    }

    @ApiResponse(responseCode = "200")
    @ApiResponse(responseCode = "404", description = "Crypto currency given is not valid", content = @Content)
    @Operation(summary = "Consult cotization of a crypto currency",
               description = "Consult the latest cotization registered of the crypto currency indicated")
    @GetMapping("/price")
    public ResponseEntity<CryptoCurrency> getSymbolPrice(@RequestParam(name = "Crypto symbol") String symbol) {
        var valueResponse = cryptoService.getCryptoValue(symbol);
        return ResponseEntity.ok(valueResponse);
    }

    @ApiResponse(responseCode = "200")
    @ApiResponse(responseCode = "400", content = @Content)
    @Operation(summary = "Consult all cryptos cotizations",
               description = "Consult the current cotization of all the crypto currencies available for the API")
    @GetMapping("/allPrices")
    public ResponseEntity<CryptoCurrencyList> getMethodName() {
        CryptoCurrencyList listCrypto = cryptoService.getAllCryptoValues();
        return ResponseEntity.ok().body(listCrypto);
    }
    
    
}
