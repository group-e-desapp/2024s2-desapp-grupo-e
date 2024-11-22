package com.unq.dapp_grupo_e.dto;

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

    public DolarApiResponseDTO(String currency, Double salePrice) {
        this.currency = currency;
        this.sale = salePrice;
    }
    
}
