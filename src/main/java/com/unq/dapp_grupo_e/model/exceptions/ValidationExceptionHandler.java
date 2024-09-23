package com.unq.dapp_grupo_e.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ValidationExceptionHandler {
    

    @ExceptionHandler(InvalidLengthException.class)
    public ResponseEntity<String> handleXValidation(InvalidLengthException exc) {
        return  new ResponseEntity<>(exc.getMessage(), HttpStatus.CONFLICT);
    }
}
