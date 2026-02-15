package com.food.reservation.customer.dto;

import jakarta.validation.constraints.*;

public record CustomerDTO(
        // ID nkhlliweh optional (null f creation)
        Long id,

        @NotBlank(message = "Smiya daroriya")
        @Size(min = 2, max = 50, message = "Smiya khass tkon bin 2 w 50 7arf")
        @Pattern(regexp = "^[a-zA-Z\\s\\-']+$", message = "Smiya fiha huruf mamzyanin")
        String firstName,

        @NotBlank(message = "Knya daroriya")
        @Size(min = 2, max = 50, message = "Knya khass tkon bin 2 w 50 7arf")
        @Pattern(regexp = "^[a-zA-Z\\s\\-']+$", message = "Knya fiha huruf mamzyanin")
        String lastName,

        @NotBlank(message = "Email darori")
        @Email(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "Email machi s7i7")
        String email,

        @NotBlank(message = "Nemra d tele daroriya")
        @Pattern(regexp = "^\\+?[0-9. ()-]{7,25}$", message = "Nemra d tele machi s7i7a")
        String phone
) {}