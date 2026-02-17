package com.food.reservation.customer.dto;

import jakarta.validation.constraints.*;

/**
 * DTO for creating a new customer. Does not include {@code id} or {@code code}
 * since those are generated server-side.
 */
public record CustomerDTO(
                @NotBlank(message = "First name is required") @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters") @Pattern(regexp = "^[a-zA-Z\\s\\-']+$", message = "First name can only contain letters, spaces, hyphens, and apostrophes") String firstName,

                @NotBlank(message = "Last name is required") @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters") @Pattern(regexp = "^[a-zA-Z\\s\\-']+$", message = "Last name can only contain letters, spaces, hyphens, and apostrophes") String lastName,

                @NotBlank(message = "Email is required") @Email(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "Email should be valid") String email,

                @NotBlank(message = "Phone number is required") @Pattern(regexp = "^\\+?[0-9. ()-]{7,25}$", message = "Phone number is invalid") String phone) {
}