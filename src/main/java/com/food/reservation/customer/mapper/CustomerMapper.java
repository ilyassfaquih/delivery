package com.food.reservation.customer.mapper;

import com.food.reservation.customer.domain.Customer;
import com.food.reservation.customer.dto.CustomerDTO;
import com.food.reservation.customer.dto.CustomerResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Maps between {@link Customer} entity and its DTOs.
 */
@Mapper(componentModel = "spring")
public interface CustomerMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "code", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Customer toEntity(CustomerDTO dto);

    CustomerResponseDTO toResponseDto(Customer entity);
}