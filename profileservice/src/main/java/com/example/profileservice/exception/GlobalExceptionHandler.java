package com.example.profileservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handlerValidationException(MethodArgumentNotValidException methodArgumentNotValidException) {
        Map<String,String> errors = new HashMap<>();
        methodArgumentNotValidException.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Map<String,String>> handlerNotFound(NoSuchElementException noSuchElementException) {
        Map<String,String> error = new HashMap<>();
        error.put("error", "Profile not found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String ,String>> handleGeneric(Exception exception) {
        Map<String, String> error = new HashMap<>();
        error.put("error",exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
