package com.food.reservation.customer.annotation;

import com.food.reservation.customer.validator.DeliveryTimeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DeliveryTimeValidator.class)
@Documented
public @interface ValidDeliveryTime {
    String message() default "L'heure doit être entre 08:00 et 00:00";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}