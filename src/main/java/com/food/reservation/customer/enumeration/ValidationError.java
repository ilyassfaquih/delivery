package com.food.reservation.customer.enumeration;



public enum ValidationError {

    NotBlank("EMPTY_FIELD", "Empty Field"),
    NotEmpty("EMPTY_FIELD", "Empty Field"),
    Min("INVALID_VALUE", "Invalid value"),
    Max("INVALID_VALUE", "Invalid value"),
    Positive("INVALID_VALUE", "Invalid value"),
    Digits("INVALID_LENGTH", "Invalid length"),
    Size("INVALID_LENGTH", "Invalid length"),
    INVALID_JSON("INVALID_JSON", "Invalid JSON"),
    INVALID_FORMAT("INVALID_FORMAT", "Invalid Format"),
    NotNull("MISSING_REQUIRED_FIELD", "Missing Required Field");

    private final String code;
    private final String description;

    ValidationError(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}