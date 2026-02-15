package com.food.reservation.customer.controller;

import com.food.reservation.customer.domain.FoodOrder;
import com.food.reservation.customer.dto.OrderRequestDTO;
import com.food.reservation.customer.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller responsible for creating new food orders. It exposes a single
 * POST endpoint under {@code /api/orders}. Requests are validated using
 * {@link jakarta.validation.Valid}.
 */
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final ReservationService reservationService;

    /**
     * Creates a new food order for a customer. The request must include a
     * customer code, delivery time and delivery mode. Additional order details
     * (such as menu items) can be added to {@link OrderRequestDTO} if needed.
     *
     * @param request the order request payload
     * @return HTTP 201 containing the persisted {@link FoodOrder}
     */
    @PostMapping
    public ResponseEntity<FoodOrder> createOrder(@Valid @RequestBody OrderRequestDTO request) {
        FoodOrder newOrder = reservationService.createOrder(request);
        return new ResponseEntity<>(newOrder, HttpStatus.CREATED);
    }
}