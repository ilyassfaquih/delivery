package com.food.reservation.customer.service;

import com.food.reservation.customer.domain.Customer;
import com.food.reservation.customer.domain.FoodOrder;
import com.food.reservation.customer.domain.MenuItem;
import com.food.reservation.customer.dto.OrderRequestDTO;
import com.food.reservation.customer.dto.OrderResponseDTO;
import com.food.reservation.customer.exception.BusinessException;
import com.food.reservation.customer.mapper.OrderMapper;
import com.food.reservation.customer.repository.CustomerRepository;
import com.food.reservation.customer.repository.MenuRepository;
import com.food.reservation.customer.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service responsible for processing food orders.
 */
@Service
@RequiredArgsConstructor
public class ReservationService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final MenuRepository menuRepository;
    private final OrderMapper orderMapper;

    /**
     * Creates a new food order for the customer identified by the given code.
     *
     * @param request the order request payload
     * @return the created order as a response DTO
     * @throws BusinessException if the customer is not found or no valid menu items
     *                           are provided
     */
    @Transactional
    public OrderResponseDTO createOrder(OrderRequestDTO request) {
        Customer customer = customerRepository.findByCode(request.customerCode())
                .orElseThrow(() -> new BusinessException(
                        "CUSTOMER_NOT_FOUND",
                        "No customer found with code: " + request.customerCode(),
                        HttpStatus.NOT_FOUND));

        List<MenuItem> items = menuRepository.findAllById(request.menuItemIds());

        if (items.isEmpty()) {
            throw new BusinessException(
                    "INVALID_MENU_ITEMS",
                    "None of the provided menu item IDs are valid",
                    HttpStatus.BAD_REQUEST);
        }

        FoodOrder order = FoodOrder.builder()
                .customer(customer)
                .deliveryTime(request.deliveryTime())
                .deliveryMode(request.deliveryMode())
                .menuItems(items)
                .build();

        FoodOrder saved = orderRepository.save(order);
        return orderMapper.toResponseDto(saved);
    }
}