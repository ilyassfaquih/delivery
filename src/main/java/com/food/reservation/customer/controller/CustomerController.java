package com.food.reservation.customer.controller;

import com.food.reservation.customer.dto.CustomerDTO;
import com.food.reservation.customer.dto.CustomerResponseDTO;
import com.food.reservation.customer.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for customer registration.
 */
@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    /**
     * Registers a new customer.
     *
     * @param dto the customer creation payload
     * @return HTTP 201 with the created customer
     */
    @PostMapping
    public ResponseEntity<CustomerResponseDTO> createCustomer(@Valid @RequestBody CustomerDTO dto) {
        CustomerResponseDTO response = customerService.createCustomer(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}