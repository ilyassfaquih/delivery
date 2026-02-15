package com.food.reservation.customer.domain;

import com.food.reservation.customer.enumeration.DeliveryMode;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalTime;
import java.util.List; // ضروري

@Entity
@Table(name = "food_order")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FoodOrder {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "delivery_time", nullable = false)
        private LocalTime deliveryTime;

        @Enumerated(EnumType.STRING)
        @Column(name = "delivery_mode", nullable = false)
        private DeliveryMode deliveryMode;

        @ManyToOne
        @JoinColumn(name = "customer_id", nullable = false)
        private Customer customer;


        @ManyToMany
        @JoinTable(
                name = "order_items",
                joinColumns = @JoinColumn(name = "order_id"),
                inverseJoinColumns = @JoinColumn(name = "menu_item_id")
        )
        private List<MenuItem> menuItems;
}