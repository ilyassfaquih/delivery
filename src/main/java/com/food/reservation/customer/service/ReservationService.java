package com.food.reservation.customer.service;

import com.food.reservation.customer.domain.Customer;
import com.food.reservation.customer.domain.FoodOrder;
import com.food.reservation.customer.domain.MenuItem; // import جديد
import com.food.reservation.customer.dto.OrderRequestDTO;
import com.food.reservation.customer.repository.CustomerRepository;
import com.food.reservation.customer.repository.MenuRepository; // import جديد
import com.food.reservation.customer.repository.OrderRepository;
import com.food.reservation.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReservationService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final MenuRepository menuRepository;

    public ReservationService(OrderRepository orderRepository,
                              CustomerRepository customerRepository,
                              MenuRepository menuRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.menuRepository = menuRepository;
    }

    @Transactional
    public FoodOrder createOrder(OrderRequestDTO request) {
        Customer customer = customerRepository.findByCode(request.customerCode())
                .orElseThrow(() -> BusinessException.builder()
                        .errorCode("CUSTOMER_NOT_FOUND")
                        .errorDescription("aucun client : " + request.customerCode())
                        .build()
                );

        List<MenuItem> items = menuRepository.findAllById(request.menuItemIds());

        if (items.isEmpty()) {
            throw new BusinessException("INVALID_MENU_ITEMS", "tu peux choisir un menu item ");
        }

        FoodOrder order = new FoodOrder();
        order.setCustomer(customer);
        order.setDeliveryTime(request.deliveryTime());
        order.setDeliveryMode(request.deliveryMode());
        order.setMenuItems(items);

        return orderRepository.save(order);
    }
}