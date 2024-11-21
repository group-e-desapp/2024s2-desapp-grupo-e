package com.unq.dapp_grupo_e.model;

public class TransactionSummary {
    
    private String symbolTrade;
    private Double totalNominalValue;

    public TransactionSummary(String symbolTrade, Double totalNominalValue) {
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
