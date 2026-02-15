package com.food.reservation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.food.reservation.customer.controller.OrderController;
import com.food.reservation.customer.dto.OrderRequestDTO;
import com.food.reservation.customer.enumeration.DeliveryMode;
import com.food.reservation.customer.service.ReservationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class) // كنشارجيو غير الكونترولر بوحدو باش يكون التيست خفيف
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc; // هاد الأداة هي باش كنصيفطو Request

    @MockBean // كنحتاجو نموكيو السرفيس حيت الكونترولر كيعيط عليه
    private ReservationService reservationService;

    @Autowired
    private ObjectMapper objectMapper; // باش نحولو Java Object ل JSON

    @Test
    void createOrder_ShouldReturnBadRequest_WhenTimeIsInvalid() throws Exception {
        // --- ARRANGE ---
        // درنا وقت غالط (07:00 صباحاً) - خارج أوقات العمل
        OrderRequestDTO invalidRequest = new OrderRequestDTO(
                "uiid-09-1234",
                LocalTime.of(7, 0),
                DeliveryMode.PRISE,
                List.of(1L)
        );

        // --- ACT & ASSERT ---
        mockMvc.perform(post("/api/orders") // نصيفطو POST Request
                        .contentType(MediaType.APPLICATION_JSON) // النوع JSON
                        .content(objectMapper.writeValueAsString(invalidRequest))) // كنحطو الداتا
                .andExpect(status().isBadRequest()) // كنتوقعو يجاوبنا ب 400 Bad Request
                .andExpect(jsonPath("$.error").value("Validation Failed")); // كنتأكد من الميساج
    }
}