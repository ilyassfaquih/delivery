package com.food.reservation.customer.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

/**
 * DTO for creating or updating a menu item.
 */
public record MenuItemDTO(
        @NotBlank(message = "Menu item name is required") @Size(max = 100, message = "Name cannot exceed 100 characters") String name,

        @NotNull(message = "Price is required") @Positive(message = "Price must be a positive value") BigDecimal price,

        boolean available) {
}
