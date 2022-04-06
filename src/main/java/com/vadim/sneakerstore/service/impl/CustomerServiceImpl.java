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
    public List<CustomerDto> getAll() {
        return repository.findAll().stream()
                .map(converter::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CustomerDto getById(UUID id) {
        Customer customer = repository.findById(id).orElseThrow(() ->
                new NotFoundException("Customer with id = " + id + " is not found")
        );
        return converter.convertToDto(customer);
    }

    @Override
    @Transactional
    public CustomerDto save(CustomerDto customerDto) {
        // will be deleted
        if (repository.existsByPhoneAndEmail(customerDto.getPhone(), customerDto.getEmail())) {
            throw new AlreadyExistsException("Customer with phone  = " + customerDto.getPhone()
                    + " and email = " + customerDto.getEmail() + " already exists");
        }
        if (repository.existsByPhone(customerDto.getPhone())) {
            throw new AlreadyExistsException("Customer with phone = " + customerDto.getPhone() + " already exists");
        }
        if (repository.existsByEmail(customerDto.getEmail())) {
            throw new AlreadyExistsException("Customer with email = " + customerDto.getEmail() + " already exists");
        }
        Customer customer = repository.save(converter.convertToEntity(customerDto));
        return converter.convertToDto(customer);
    }

    @Override
    @Transactional
    public CustomerDto update(CustomerDto customerDto) {
        Customer customer = repository.findById(customerDto.getId()).orElseThrow(() ->
            new NotFoundException("Customer with id " + customerDto.getId() + " is not found")
        );
        converter.updateCustomer(customer, customerDto);
        return converter.convertToDto(customer);
    }

    @Override
    @Transactional
    public void deleteById(UUID id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Customer with id=" + id + " is not found");
        }
        repository.deleteById(id);
    }
}
