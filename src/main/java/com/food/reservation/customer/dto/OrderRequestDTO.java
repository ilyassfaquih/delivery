package com.food.reservation.customer.dto;

import com.food.reservation.customer.annotation.ValidDeliveryTime;
import com.food.reservation.customer.enumeration.DeliveryMode;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;
import java.util.List;

/**
 * DTO for placing a new food order.
 */
public record OrderRequestDTO(
                @NotNull(message = "Customer code is required") String customerCode,

                @NotNull(message = "Delivery time is required") @ValidDeliveryTime(message = "Outside business hours (08:00 - 00:00)") LocalTime deliveryTime,

                @NotNull(message = "Delivery mode is required") DeliveryMode deliveryMode,

                @NotEmpty(message = "The order must contain at least one menu item") List<Long> menuItemIds) {
}