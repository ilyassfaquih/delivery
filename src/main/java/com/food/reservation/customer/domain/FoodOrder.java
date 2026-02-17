package com.food.reservation.customer.domain;

import com.food.reservation.customer.enumeration.DeliveryMode;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * Represents a food order placed by a {@link Customer}.
 * An order contains one or more {@link MenuItem}s.
 */
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

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "customer_id", nullable = false)
        private Customer customer;

        @ManyToMany
        @JoinTable(name = "order_items", joinColumns = @JoinColumn(name = "order_id"), inverseJoinColumns = @JoinColumn(name = "menu_item_id"))
        private List<MenuItem> menuItems;

        @CreationTimestamp
        @Column(name = "created_at", updatable = false)
        private LocalDateTime createdAt;
}