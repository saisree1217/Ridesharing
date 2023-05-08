package com.gocarshare.scheduleservice.scheduleservice.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

@org.springframework.web.bind.annotation.RestControllerAdvice
public class RestControllerAdvice {

  @ExceptionHandler(ExceptionResponse.class)
  public ResponseEntity<Object> handleExceptionResponse(ExceptionResponse ex) {
    return new ResponseEntity<>(ex, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
