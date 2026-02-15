package com.food.reservation.customer.mapper;

import com.food.reservation.customer.domain.Customer;
import com.food.reservation.customer.dto.CustomerDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AppMapper {

    @Mapping(target = "code", ignore = true)
    Customer toCustomerEntity(CustomerDTO dto);

    CustomerDTO toCustomerDto(Customer entity);
}