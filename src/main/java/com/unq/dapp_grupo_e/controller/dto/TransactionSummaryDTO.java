package com.unq.dapp_grupo_e.controller.dto;

public class TransactionSummaryDTO {
    
    private String symbolTrade;
    private Double totalNominalValue;

    public TransactionSummaryDTO(String symbolTrade, Double totalNominalValue) {
        this.symbolTrade = symbolTrade;
        this.totalNominalValue = totalNominalValue;
    }

    public String getSymbolTrade() {
        return symbolTrade;
    }

    public Double getTotalNominalValue() {
        return totalNominalValue;
    }

    

}
