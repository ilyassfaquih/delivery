package com.food.reservation.customer.service;

import com.food.reservation.customer.domain.Customer;
import com.food.reservation.customer.dto.CustomerDTO;
import com.food.reservation.customer.dto.CustomerResponseDTO;
import com.food.reservation.customer.mapper.CustomerMapper;
import com.food.reservation.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service responsible for customer registration and retrieval.
 */
@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    /**
     * Creates a new customer from the given DTO.
     *
     * @param dto the customer creation payload
     * @return the created customer as a response DTO
     */
    @Transactional
    public CustomerResponseDTO createCustomer(CustomerDTO dto) {
        Customer customer = customerMapper.toEntity(dto);
        Customer saved = customerRepository.save(customer);
        return customerMapper.toResponseDto(saved);
    }
}
