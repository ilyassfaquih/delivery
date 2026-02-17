package com.food.reservation.customer.validator;

import com.food.reservation.customer.annotation.ValidDeliveryTime;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalTime;

/**
 * Validates that a delivery time falls within business hours (08:00–00:00).
 * Allows {@code null} values — use {@code @NotNull} separately if required.
 */
public class DeliveryTimeValidator implements ConstraintValidator<ValidDeliveryTime, LocalTime> {

    private static final LocalTime OPENING_TIME = LocalTime.of(8, 0);

    @Override
    public boolean isValid(LocalTime time, ConstraintValidatorContext context) {
        if (time == null) {
            return true;
        }
        // Valid if exactly midnight (00:00) or at/after 08:00
        // This excludes the range 00:01–07:59
        return time.equals(LocalTime.MIDNIGHT) || !time.isBefore(OPENING_TIME);
    }
}