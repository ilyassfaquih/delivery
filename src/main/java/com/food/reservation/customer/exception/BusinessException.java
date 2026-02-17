package com.food.reservation.customer.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Exception thrown when a business rule is violated.
 * Carries an error code, a human-readable description, and
 * the HTTP status that the global handler should return.
 */
@Getter
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final String errorCode;
    private final String errorDescription;
    private final HttpStatus httpStatus;

    public BusinessException(String errorCode, String errorDescription, HttpStatus httpStatus) {
        super(errorDescription);
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
        this.httpStatus = httpStatus;
    }

    public BusinessException(String errorCode, String errorDescription) {
        this(errorCode, errorDescription, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}