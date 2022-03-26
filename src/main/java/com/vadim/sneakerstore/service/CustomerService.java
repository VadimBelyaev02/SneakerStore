package com.vadim.sneakerstore.service;

import com.vadim.sneakerstore.dto.CustomerDto;

import java.util.List;
import java.util.UUID;

public interface CustomerService {

    List<CustomerDto> getAll();

    CustomerDto getById(UUID id);

    CustomerDto save(CustomerDto customerDto);

    CustomerDto update(CustomerDto customerDto);

    void deleteById(UUID id);
}
