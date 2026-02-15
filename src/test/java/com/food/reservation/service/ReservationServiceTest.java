package com.food.reservation.service;

import com.food.reservation.customer.dto.OrderRequestDTO;
import com.food.reservation.customer.enumeration.DeliveryMode;
import com.food.reservation.customer.repository.CustomerRepository;
import com.food.reservation.customer.repository.OrderRepository;
import com.food.reservation.customer.service.ReservationService;
import com.food.reservation.exception.BusinessException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.food.reservation.customer.service.ReservationService;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) // 1. كيعلم JUnit باش يخدم ب Mockito
class ReservationServiceTest {

    @Mock // 2. كنصاوبو نسخة مزورة من Repository (حيت ما بغيناش نخدمو بالداتا بيز بصح)
    private CustomerRepository customerRepository;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks // 3. كنحطو دوك النسخ المزورة وسط السرفيس ديالنا
    private ReservationService reservationService;

    @Test
    void createOrder_ShouldThrowException_WhenCustomerNotFound() {
        // --- ARRANGE (نوجدو المعطيات) ---
        String fakeCode = "uiid-09-9999";
        // كنقولو لـ Mockito: إلا شي حد سولك على هاد الكود، قوليه "مكاينش" (Optional.empty)
        when(customerRepository.findByCode(fakeCode)).thenReturn(Optional.empty());

        OrderRequestDTO request = new OrderRequestDTO(
                fakeCode,
                LocalTime.of(12, 0),
                DeliveryMode.LIVRAISON,
                List.of(1L, 2L) // IDs ديال الماكلة
        );

        // --- ACT & ASSERT (نجربو ونتأكدو) ---
        // هنا كنتيستيو واش السرفيس غيفركع (Throw Exception)
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            reservationService.createOrder(request);
        });

        // كنتأكدو بلي الكود ديال الخطأ هو "CUSTOMER_NOT_FOUND" نيت
        assertEquals("CUSTOMER_NOT_FOUND", exception.getErrorCode());
    }

}