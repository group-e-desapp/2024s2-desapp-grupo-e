package com.unq.dapp_grupo_e.exceptions;

public class UserNotFoundException extends RuntimeException {
    
    public UserNotFoundException() {
        super("The indicated user was not valid");
    }
    
}
