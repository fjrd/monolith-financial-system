package com.example.monolithfinancialsystem.exception;

public class BusinessValidationException extends RuntimeException {

    public BusinessValidationException() {
        super();
    }

    public BusinessValidationException(String message) {
        super(message);
    }
}
