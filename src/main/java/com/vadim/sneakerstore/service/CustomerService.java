package com.vadim.sneakerstore.service;

import com.vadim.sneakerstore.dto.CustomerDto;

import java.util.List;
import java.util.UUID;

public interface CustomerService {

    List<CustomerDto> getAllCustomers();

    CustomerDto getCustomer(UUID id);

    CustomerDto saveCustomer(CustomerDto customerDto);

    CustomerDto updateCustomer(CustomerDto customerDto);

    void deleteCustomer(UUID id);
}
