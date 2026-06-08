package com.CrystalShop.api.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ExceptionResponse> handleApiException(ApiException e) {
        ExceptionResponse response = new ExceptionResponse(e.getMessage(), e.getHttpStatus().value(), LocalDateTime.now());
        return new ResponseEntity<>(response, e.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleGeneralException(Exception e) {
        ExceptionResponse response = new ExceptionResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleValidationException(org.springframework.web.bind.MethodArgumentNotValidException e) {

        String errorMessage = e.getBindingResult().getFieldError().getDefaultMessage();

        ExceptionResponse response = new ExceptionResponse(errorMessage, HttpStatus.BAD_REQUEST.value(), LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
