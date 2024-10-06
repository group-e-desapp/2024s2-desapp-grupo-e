package com.unq.dapp_grupo_e.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DolarApiResponseDTO {

    private String currency;
    private Double sale;

    @JsonProperty("moneda")
    public String getCurrency() {
        return currency;
    }

    @JsonProperty("venta")
    public Double getSale() {
        return sale;
    }

    
    
}
