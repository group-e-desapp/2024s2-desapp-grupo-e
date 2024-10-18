package com.unq.dapp_grupo_e.model;

public class CryptoActive {

    private String symbol;
    private Double totalNominalValue;
    private Double currentCotization;
    private Double totalCotizationARS;

    public CryptoActive(String symbol, Double nominalValue, Double currentCotization) {
        this.symbol = symbol;
        this.totalNominalValue = nominalValue;
        this.currentCotization = currentCotization;
        this.totalCotizationARS = nominalValue * currentCotization;
    }

    public String getSymbol() {
        return symbol;
    }

    public Double getTotalNominalValue() {
        return totalNominalValue;
    }

    public Double getCurrentCotization() {
        return currentCotization;
    }

    public Double getTotalCotizationARS() {
        return totalCotizationARS;
    }
    
    
}
