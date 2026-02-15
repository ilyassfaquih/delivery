package com.food.reservation.exception;

import com.food.reservation.customer.enumeration.ValidationError;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode(callSuper = false)
public class FieldNotValidException extends Exception {

    private static final long serialVersionUID = 1L;

    private final String errorField;
    private final String errorCode;

    public String getErrorCode() {
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