package com.unq.dapp_grupo_e.exceptions;

public class UserNotAuthenticatedException extends RuntimeException {
    
    public UserNotAuthenticatedException() {
        super("There is no authenticated user that could create this intention");
    }
    
}
