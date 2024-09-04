package com._up.megastore.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NoSuchElementException.class)
    public ExceptionPayload handleNoSuchElementException(NoSuchElementException ex) {
        return new ExceptionPayload(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now(),
                ex.getStackTrace()
        );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ExceptionPayload handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return new ExceptionPayload(
                ex.getBindingResult().getAllErrors().getFirst().getDefaultMessage(),
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now(),
                ex.getStackTrace()
        );
    }

    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @ExceptionHandler(RuntimeException.class)
    public ExceptionPayload handleRuntimeException(RuntimeException ex) {
        return new ExceptionPayload(
                ex.getMessage(),
                HttpStatus.SERVICE_UNAVAILABLE.value(),
                LocalDateTime.now(),
                ex.getStackTrace()
        );
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ExceptionPayload handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        return new ExceptionPayload(
                ex.getMessage(),
                HttpStatus.CONFLICT.value(),
                LocalDateTime.now(),
                ex.getStackTrace()
        );
    }

}