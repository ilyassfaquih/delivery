package com.food.reservation.customer.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Standardized error response returned by all exception handlers.
 * Fields that are {@code null} are omitted from the JSON output.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(
        LocalDateTime timestamp,
        int status,
        String error,
        String code,
        String message,
        Map<String, String> fieldErrors) {

    public ErrorResponse(int status, String error, String code, String message) {
        this(LocalDateTime.now(), status, error, code, message, null);
    }

    public ErrorResponse(int status, String error, String code, String message, Map<String, String> fieldErrors) {
        this(LocalDateTime.now(), status, error, code, message, fieldErrors);
    }
}
