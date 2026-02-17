package com.food.reservation.customer.exception;

import com.food.reservation.customer.enumeration.ValidationError;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Centralized exception handler for the entire application.
 * Converts exceptions into structured {@link ErrorResponse} objects.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
    Map<String, String> fieldErrors = new HashMap<>();
    ex.getBindingResult().getAllErrors().forEach(error -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      fieldErrors.put(fieldName, errorMessage);
    });

    ErrorResponse response = new ErrorResponse(
        HttpStatus.BAD_REQUEST.value(),
        "Validation Failed",
        ValidationError.INVALID_JSON.getCode(),
        "One or more fields failed validation",
        fieldErrors);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }

  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException ex) {
    HttpStatus status = ex.getHttpStatus();
    ErrorResponse response = new ErrorResponse(
        status.value(),
        "Business Error",
        ex.getErrorCode(),
        ex.getErrorDescription());
    return ResponseEntity.status(status).body(response);
  }

  @ExceptionHandler(FieldNotValidException.class)
  public ResponseEntity<ErrorResponse> handleFieldNotValidException(FieldNotValidException ex) {
    Map<String, String> fieldErrors = Map.of(ex.getErrorField(), ex.getErrorDescription());
    ErrorResponse response = new ErrorResponse(
        HttpStatus.BAD_REQUEST.value(),
        "Field Validation Error",
        ex.getResolvedErrorCode(),
        ex.getErrorDescription(),
        fieldErrors);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<ErrorResponse> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
    ErrorResponse response = new ErrorResponse(
        HttpStatus.CONFLICT.value(),
        "Data Integrity Violation",
        "DUPLICATE_ENTRY",
        "A database constraint was violated (e.g. duplicate email or code)");
    return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
    ErrorResponse response = new ErrorResponse(
        HttpStatus.INTERNAL_SERVER_ERROR.value(),
        "Internal Server Error",
        "INTERNAL_ERROR",
        "An unexpected internal error occurred");
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
  }
}