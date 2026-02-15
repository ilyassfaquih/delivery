package com.food.reservation.exception;

import com.food.reservation.customer.enumeration.ValidationError;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {


  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
    Map<String, Object> response = new HashMap<>();

    response.put("timestamp", LocalDateTime.now());
    response.put("status", HttpStatus.BAD_REQUEST.value());
    response.put("error", "Validation Failed");
    response.put("code", ValidationError.INVALID_JSON.getCode());

    Map<String, String> fieldErrors = new HashMap<>();
    ex.getBindingResult().getAllErrors().forEach((error) -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      fieldErrors.put(fieldName, errorMessage);
    });

    response.put("messages", fieldErrors);

    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }


  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<Map<String, Object>> handleBusinessException(BusinessException ex) {
    Map<String, Object> response = new HashMap<>();

    response.put("timestamp", LocalDateTime.now());
    response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
    response.put("error", "Business Error");
    response.put("code", ex.getErrorCode());
    response.put("message", ex.getErrorDescription());

    if (ex.getErrorParam() != null) {
      response.put("param", ex.getErrorParam());
    }

    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
  }


  @ExceptionHandler(FieldNotValidException.class)
  public ResponseEntity<Map<String, Object>> handleFieldNotValidException(FieldNotValidException ex) {
    Map<String, Object> response = new HashMap<>();

    response.put("timestamp", LocalDateTime.now());
    response.put("status", HttpStatus.BAD_REQUEST.value());
    response.put("error", "Field Validation Error");

    response.put("code", ex.getErrorCode());
    response.put("message", ex.getErrorDescription());
    response.put("field", ex.getErrorField());

    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }


  @ExceptionHandler(Exception.class)
  public ResponseEntity<Map<String, Object>> handleGeneralException(Exception ex) {
    Map<String, Object> response = new HashMap<>();

    response.put("timestamp", LocalDateTime.now());
    response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
    response.put("error", "Internal Server Error");
    response.put("message", "Une erreur interne est survenue.");



    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
  }
  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<Map<String, Object>> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
    Map<String, Object> response = new HashMap<>();
    response.put("timestamp", LocalDateTime.now());
    response.put("status", HttpStatus.CONFLICT.value());
    response.put("error", "Data Integrity Violation");
    response.put("message", "Violation de contrainte en base : email ou code déjà utilisé.");
    return new ResponseEntity<>(response, HttpStatus.CONFLICT);
  }
}