package com.food.reservation.customer.annotation;

import com.food.reservation.customer.validator.DeliveryTimeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * Validates that the annotated {@link java.time.LocalTime} falls within
 * business hours (08:00–00:00).
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DeliveryTimeValidator.class)
@Documented
public @interface ValidDeliveryTime {
    String message() default "Delivery time must be between 08:00 and 00:00";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}