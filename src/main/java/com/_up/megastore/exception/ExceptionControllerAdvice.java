package com._up.megastore.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    private final LocalDateTime NOW = LocalDateTime.now();

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NoSuchElementException.class)
    public ExceptionPayload handleNoSuchElementException(NoSuchElementException ex) {
        return new ExceptionPayload(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                NOW,
                ex.getStackTrace()
        );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ExceptionPayload handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return new ExceptionPayload(
                ex.getBindingResult().getAllErrors().get(0).getDefaultMessage(),
                HttpStatus.BAD_REQUEST.value(),
                NOW,
                ex.getStackTrace()
        );
    }

    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @ExceptionHandler(RuntimeException.class)
    public ExceptionPayload handleRuntimeException(RuntimeException ex) {
        return new ExceptionPayload(
                ex.getMessage(),
                HttpStatus.SERVICE_UNAVAILABLE.value(),
                NOW,
                ex.getStackTrace()
        );
    }

}