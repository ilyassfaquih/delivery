package com.food.reservation.service;

import com.food.reservation.customer.dto.OrderRequestDTO;
import com.food.reservation.customer.enumeration.DeliveryMode;
import com.food.reservation.customer.exception.BusinessException;
import com.food.reservation.customer.mapper.OrderMapper;
import com.food.reservation.customer.repository.CustomerRepository;
import com.food.reservation.customer.repository.MenuRepository;
import com.food.reservation.customer.repository.OrderRepository;
import com.food.reservation.customer.service.ReservationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link ReservationService}.
 */
@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private MenuRepository menuRepository;

    @Mock
    private OrderMapper orderMapper;

    @InjectMocks
    private ReservationService reservationService;

    @Test
    void createOrder_ShouldThrowException_WhenCustomerNotFound() {
        // Arrange — use a non-existent customer code
        String fakeCode = "non-existent-code";
        when(customerRepository.findByCode(fakeCode)).thenReturn(Optional.empty());

        OrderRequestDTO request = new OrderRequestDTO(
                fakeCode,
                LocalTime.of(12, 0),
                DeliveryMode.DELIVERY,
                List.of(1L, 2L));

        // Act & Assert — the service should throw BusinessException
        BusinessException exception = assertThrows(BusinessException.class,
                () -> reservationService.createOrder(request));

        assertEquals("CUSTOMER_NOT_FOUND", exception.getErrorCode());
    }
}