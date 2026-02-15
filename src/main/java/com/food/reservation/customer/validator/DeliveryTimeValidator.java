package com.food.reservation.customer.validator;

import com.food.reservation.customer.annotation.ValidDeliveryTime;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalTime;

public class DeliveryTimeValidator implements ConstraintValidator<ValidDeliveryTime, LocalTime> {

    private static final LocalTime START_TIME = LocalTime.of(8, 0); // 08:00

    @Override
    public boolean isValid(LocalTime time, ConstraintValidatorContext context) {
        // C'est une bonne pratique de laisser passer 'null' ici.
        // Si le champ est obligatoire, on utilise @NotNull dans le DTO en plus.
        if (time == null) {
            return true;
        }

        // Logique : Valide si c'est minuit PILE (00:00) OU si c'est après 08:00
        // Cela exclut implicitement la plage 00:01 à 07:59
        return time.equals(LocalTime.MIDNIGHT) || !time.isBefore(START_TIME);
    }
}