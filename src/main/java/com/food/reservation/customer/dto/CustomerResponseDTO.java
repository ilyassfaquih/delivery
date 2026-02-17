package com.food.reservation.customer.dto;

import java.time.LocalDateTime;

/**
 * DTO returned when a customer is created or retrieved.
 */
public record CustomerResponseDTO(
        Long id,
        String code,
        String firstName,
        String lastName,
        String email,
        String phone,
        LocalDateTime createdAt) {
}
