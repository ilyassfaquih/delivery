package com.food.reservation.customer.mapper;

import com.food.reservation.customer.domain.MenuItem;
import com.food.reservation.customer.dto.MenuItemDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Maps between {@link MenuItem} entity and {@link MenuItemDTO}.
 */
@Mapper(componentModel = "spring")
public interface MenuItemMapper {

    @Mapping(target = "id", ignore = true)
    MenuItem toEntity(MenuItemDTO dto);

    MenuItemDTO toDto(MenuItem entity);
}
