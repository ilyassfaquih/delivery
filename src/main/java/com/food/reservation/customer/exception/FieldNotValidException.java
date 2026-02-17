package com.food.reservation.customer.exception;

import com.food.reservation.customer.enumeration.ValidationError;
import lombok.Getter;

/**
 * Exception thrown when a specific field fails validation.
 * Resolves the error code and description from {@link ValidationError} if
 * possible.
 */
@Getter
public class FieldNotValidException extends Exception {

    private static final long serialVersionUID = 1L;

    private final String errorField;
    private final String errorCode;

    public FieldNotValidException(String errorField, String errorCode) {
        super("Validation failed for field: " + errorField);
        this.errorField = errorField;
        this.errorCode = errorCode;
    }

    public String getResolvedErrorCode() {
        try {
            return ValidationError.valueOf(this.errorCode).getCode();
        } catch (IllegalArgumentException e) {
            return this.errorCode;
        }
    }

    public String getErrorDescription() {
        try {
            return ValidationError.valueOf(this.errorCode).getDescription();
        } catch (IllegalArgumentException e) {
            return "Validation error";
        }
    }
}