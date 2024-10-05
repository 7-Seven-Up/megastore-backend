package com._up.megastore.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import static com._up.megastore.exception.ExceptionMessages.INVALID_FORMAT_EXCEPTION_MESSAGE;

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
  public ExceptionPayload handleMethodArgumentNotValidException(
      MethodArgumentNotValidException ex) {
    return new ExceptionPayload(
        ex.getBindingResult().getAllErrors().getFirst().getDefaultMessage(),
        HttpStatus.BAD_REQUEST.value(),
        LocalDateTime.now(),
        ex.getStackTrace()
    );
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(IllegalArgumentException.class)
  public ExceptionPayload handleIllegalArgumentException(IllegalArgumentException ex) {
    return new ExceptionPayload(
        ex.getMessage(),
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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ResponseStatusException.class)
    public ExceptionPayload handleResponseStatusException(ResponseStatusException ex) {
        return new ExceptionPayload(
                ex.getReason(),
                ex.getStatusCode().value(),
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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ExceptionPayload handleHttpMessageNoReadableException(HttpMessageNotReadableException ex) {
      return switch (ex.getCause()) {
          case InvalidFormatException e -> handleInvalidFormatException(e);
          default -> new ExceptionPayload(
                  ex.getMessage(),
                  HttpStatus.BAD_REQUEST.value(),
                  LocalDateTime.now(),
                  ex.getStackTrace()
          );
      };
    }

    private ExceptionPayload handleInvalidFormatException(InvalidFormatException ex) {
      return new ExceptionPayload(
              INVALID_FORMAT_EXCEPTION_MESSAGE,
              HttpStatus.BAD_REQUEST.value(),
              LocalDateTime.now(),
              ex.getStackTrace()
      );
    }

}