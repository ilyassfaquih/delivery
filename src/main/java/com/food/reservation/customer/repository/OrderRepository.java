package com.food.reservation.customer.repository;

import com.food.reservation.customer.domain.FoodOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<FoodOrder, Long> {

}
