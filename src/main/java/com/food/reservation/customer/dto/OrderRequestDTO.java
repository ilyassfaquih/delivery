package com.food.reservation.customer.dto;

import com.food.reservation.customer.annotation.ValidDeliveryTime;
import com.food.reservation.customer.enumeration.DeliveryMode;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalTime;
import java.util.List;

public record OrderRequestDTO(
        @NotNull(message = "Le code client est obligatoire")
        @Pattern(regexp = "^uiid-09-\\d{4}$", message = "Format invalide")
        String customerCode,

        @NotNull(message = "L'heure de livraison est obligatoire")
        @ValidDeliveryTime(message = "Hors horaires de travail (08:00 - 00:00)")
        LocalTime deliveryTime,

        @NotNull(message = "Le mode de livraison est obligatoire")
        DeliveryMode deliveryMode,

        // --- هادي هي اللي خاصك تكون زدت ---
        @NotEmpty(message = "La commande doit contenir au moins un plat")
        List<Long> menuItemIds
) {}