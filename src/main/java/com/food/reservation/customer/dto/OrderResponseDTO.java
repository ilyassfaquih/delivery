package com.food.reservation.customer.dto;

import com.food.reservation.customer.enumeration.DeliveryMode;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * DTO returned after an order is created or retrieved.
 */
public record OrderResponseDTO(
                Long id,
                String customerName,
                LocalTime deliveryTime,
                DeliveryMode deliveryMode,
                List<String> menuItemNames,
                LocalDateTime createdAt) {
}