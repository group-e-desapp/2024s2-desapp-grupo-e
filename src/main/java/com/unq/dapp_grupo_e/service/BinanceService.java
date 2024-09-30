package com.unq.dapp_grupo_e.service;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.unq.dapp_grupo_e.model.CryptoCurrency;

@Service
public class BinanceService {

    private final RestTemplate restTemplate;
    private static final String BINANCEURL = "https://api.binance.com/api/v3/ticker/"; 

    public BinanceService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public CryptoCurrency getPrice(String symbol) {
        String fullUrl = BINANCEURL + "price?symbol=" + symbol;
        ResponseEntity<CryptoCurrency> response =  restTemplate.getForEntity(fullUrl, CryptoCurrency.class);
        return response.getBody();
    }


    
}
