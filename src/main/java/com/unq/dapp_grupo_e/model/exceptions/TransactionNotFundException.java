package com.unq.dapp_grupo_e.model.exceptions;

public class TransactionNotFundException extends RuntimeException {
    
    public TransactionNotFundException() {
        super("The indicated transaction was not valid");
    }
}
