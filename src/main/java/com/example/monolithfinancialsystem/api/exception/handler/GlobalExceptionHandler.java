package com.example.monolithfinancialsystem.api.exception.handler;

import com.example.model.ErrorResponse;
import com.example.monolithfinancialsystem.exception.BusinessValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BusinessValidationException.class)
    public ErrorResponse handleBusinessValidationException(BusinessValidationException e) {
        return ErrorResponse.builder()
                .message(e.getMessage())
                .build();
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EntityNotFoundException.class)
    public ErrorResponse handleEntityNotFoundException(EntityNotFoundException e) {
        return ErrorResponse.builder()
                .message(e.getMessage())
                .build();
    }
}