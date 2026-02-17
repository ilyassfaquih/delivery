package com.food.reservation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.food.reservation.customer.controller.OrderController;
import com.food.reservation.customer.dto.OrderRequestDTO;
import com.food.reservation.customer.enumeration.DeliveryMode;
import com.food.reservation.customer.service.ReservationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Unit tests for {@link OrderController}.
 * Uses {@code @WebMvcTest} to load only the web layer.
 */
@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ReservationService reservationService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createOrder_ShouldReturnBadRequest_WhenTimeIsInvalid() throws Exception {
        // Arrange — delivery time 07:00 is outside business hours
        OrderRequestDTO invalidRequest = new OrderRequestDTO(
                "some-customer-code",
                LocalTime.of(7, 0),
                DeliveryMode.PICKUP,
                List.of(1L));

        // Act & Assert
        mockMvc.perform(post("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Validation Failed"));
    }
}