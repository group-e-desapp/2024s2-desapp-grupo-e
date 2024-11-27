package com.unq.dapp_grupo_e.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ValidationExceptionHandler {
    

    public Map<String, String> createResponseError(Exception exc) {
        Map<String, String> response = new HashMap<>();
        response.put("error", exc.getClass().getSimpleName());
        response.put("message", exc.getMessage());
        return response;
    }

    @ExceptionHandler(InvalidLengthException.class)
    public ResponseEntity<Map<String, String>> handleLengthValidation(InvalidLengthException exc) {
        return new ResponseEntity<>(createResponseError(exc), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicationDataException.class)
    public ResponseEntity<Map<String, String>> handleDuplicationValidation(DuplicationDataException exc) {
        return new ResponseEntity<>(createResponseError(exc), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidEmailException.class)
    public ResponseEntity<Map<String, String>> handleEmailValidation(InvalidEmailException exc) {
        return new ResponseEntity<>(createResponseError(exc), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidCharactersException.class)
    public ResponseEntity<Map<String, String>> handleMissingCharacterValidation(InvalidCharactersException exc) {
        return new ResponseEntity<>(createResponseError(exc), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidCurrencyException.class)
    public ResponseEntity<Map<String, String>> handleCryptoSymbolValidation(InvalidCurrencyException exc) {
        return new ResponseEntity<>(createResponseError(exc), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidCryptoPriceOffer.class)
    public ResponseEntity<Map<String, String>> handlePriceOfferValidation(InvalidCryptoPriceOffer exc) {
        return new ResponseEntity<>(createResponseError(exc), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidEmptyFieldException.class)
    public ResponseEntity<Map<String, String>> handleEmptyFieldValidation(InvalidEmptyFieldException exc) {
        return new ResponseEntity<>(createResponseError(exc), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidActionException.class)
    public ResponseEntity<Map<String, String>> handleInvalidActionValidation(InvalidActionException exc) {
        return new ResponseEntity<>(createResponseError(exc), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleUserNotFound(UserNotFoundException exc) {
        return new ResponseEntity<>(createResponseError(exc), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TransactionNotFundException.class)
    public ResponseEntity<Map<String, String>> handleTransactionNotFound(TransactionNotFundException exc) {
        return new ResponseEntity<>(createResponseError(exc), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidDateFormatException.class)
    public ResponseEntity<Map<String, String>> handleInvalidDateFormat(InvalidDateFormatException exc) {
        return new ResponseEntity<>(createResponseError(exc), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotAuthenticatedException.class)
    public ResponseEntity<Map<String, String>> handleUserNotAuthenticated(UserNotAuthenticatedException exc) {
        return new ResponseEntity<>(createResponseError(exc), HttpStatus.UNAUTHORIZED);
    }
}
