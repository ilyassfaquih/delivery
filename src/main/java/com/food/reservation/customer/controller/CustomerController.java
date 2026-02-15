package com.food.reservation.customer.controller;

import com.food.reservation.customer.domain.Customer;
import com.food.reservation.customer.dto.CustomerDTO;
import com.food.reservation.customer.mapper.AppMapper;
import com.food.reservation.customer.repository.CustomerRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerRepository customerRepository;
    private final AppMapper appMapper;

    public CustomerController(CustomerRepository customerRepository, AppMapper appMapper) {
        this.customerRepository = customerRepository;
        this.appMapper = appMapper;
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody CustomerDTO customerDTO) {

        Customer customer = appMapper.toCustomerEntity(customerDTO);


        Customer savedCustomer = customerRepository.save(customer);
        return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);
    }
}