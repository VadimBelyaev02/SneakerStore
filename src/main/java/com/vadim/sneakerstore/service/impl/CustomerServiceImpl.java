package com.vadim.sneakerstore.service.impl;

import com.vadim.sneakerstore.dto.CustomerDto;
import com.vadim.sneakerstore.dto.converter.CustomerConverter;
import com.vadim.sneakerstore.entity.Customer;
import com.vadim.sneakerstore.exception.AlreadyExistsException;
import com.vadim.sneakerstore.exception.NotFoundException;
import com.vadim.sneakerstore.repository.CustomerRepository;
import com.vadim.sneakerstore.service.CustomerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repository;
    private final CustomerConverter converter;

    public CustomerServiceImpl(CustomerRepository repository, CustomerConverter converter) {
        this.repository = repository;
        this.converter = converter;
    }

    @Override
    @Transactional
    public List<CustomerDto> getAllCustomers() {
        return repository.findAll().stream()
                .map(converter::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CustomerDto getCustomer(UUID id) {
        Customer customer = repository.findById(id).orElseThrow(() ->
                new NotFoundException("Customer with id=" + id + " is not found")
        );
        return converter.convertToDto(customer);
    }

    @Override
    @Transactional
    public CustomerDto saveCustomer(CustomerDto customerDto) {
        if (repository.existsById(customerDto.getId())) {
            throw new AlreadyExistsException("Customer with id=" + customerDto.getId() + " already exists");
        }
        return null;

    }

    @Override
    @Transactional
    public CustomerDto updateCustomer(CustomerDto customerDto) {
        if (!repository.existsByEmail(customerDto.getEmail())) {
            throw new NotFoundException("Customer is not found");
        }
        Customer customer = repository.save(converter.convertToEntity(customerDto));
        return converter.convertToDto(customer);
    }

    @Override
    @Transactional
    public void deleteCustomer(UUID id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Customer with id=" + id + " is not found");
        }
        repository.deleteById(id);
    }
}
