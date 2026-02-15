package com.food.reservation.customer.dto;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record OrderResponseDTO(
        UUID orderId,
        String customerName,
        String orderType,
        BigDecimal amount,
        LocalDateTime date
) {}