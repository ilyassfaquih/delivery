package com.food.reservation.customer.controller;

import com.food.reservation.customer.dto.OrderRequestDTO;
import com.food.reservation.customer.dto.OrderResponseDTO;
import com.food.reservation.customer.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for placing food orders.
 */
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final ReservationService reservationService;

    /**
     * Creates a new food order.
     *
     * @param request the order request payload
     * @return HTTP 201 with the created order details
     */
    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(@Valid @RequestBody OrderRequestDTO request) {
        OrderResponseDTO response = reservationService.createOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}