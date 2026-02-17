package com.food.reservation.customer.mapper;

import com.food.reservation.customer.domain.FoodOrder;
import com.food.reservation.customer.domain.MenuItem;
import com.food.reservation.customer.dto.OrderResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

/**
 * Maps between {@link FoodOrder} entity and {@link OrderResponseDTO}.
 */
@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "customerName", expression = "java(order.getCustomer().getFirstName() + \" \" + order.getCustomer().getLastName())")
    @Mapping(target = "menuItemNames", source = "menuItems", qualifiedByName = "toMenuItemNames")
    OrderResponseDTO toResponseDto(FoodOrder order);

    @Named("toMenuItemNames")
    default List<String> toMenuItemNames(List<MenuItem> items) {
        if (items == null) {
            return List.of();
        }
        return items.stream().map(MenuItem::getName).toList();
    }
}
