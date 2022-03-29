package com.vadim.sneakerstore.service.impl;

import com.vadim.sneakerstore.dto.AddressDto;
import com.vadim.sneakerstore.dto.converter.AddressConverter;
import com.vadim.sneakerstore.entity.Address;
import com.vadim.sneakerstore.exception.AlreadyExistsException;
import com.vadim.sneakerstore.exception.NotFoundException;
import com.vadim.sneakerstore.repository.AddressRepository;
import com.vadim.sneakerstore.service.AddressService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository repository;
    private final AddressConverter converter;

    public AddressServiceImpl(AddressRepository repository, AddressConverter converter) {
        this.repository = repository;
        this.converter = converter;
    }

    @Override
    @Transactional
    public AddressDto getById(UUID id) {
        Address address = repository.findById(id).orElseThrow(() ->
                new NotFoundException("Address with id = " + id + " is not found")
        );
        return converter.convertToDto(address);
    }

    @Override
    @Transactional
    public List<AddressDto> getAll() {
        return repository.findAll().stream()
                .map(converter::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public AddressDto save(AddressDto addressDto) {
        if (repository.existsByCityAndCountryAndStreet(addressDto.getCity(), addressDto.getCountry(), addressDto.getStreet())) {
            throw new AlreadyExistsException("Address with city = " + addressDto.getCity() + " and country = "
                    + addressDto.getCountry() + " and street =" + addressDto.getStreet() + " already exists");
        }
        Address address = repository.save(converter.convertToEntity(addressDto));
        return converter.convertToDto(address);
    }

    @Override
    @Transactional
    public AddressDto update(AddressDto addressDto) {
//        if (!repository.existsById(addressDto.getId())) {
//
//        }


            // all three fields may be must be primary key
        if (!repository.existsByCityAndCountryAndStreet(addressDto.getCity(), addressDto.getCountry(), addressDto.getStreet())) {
            throw new NotFoundException("Address with city = " + addressDto.getCity() + " and country = "
                    + addressDto.getCountry() + " and street =" + addressDto.getStreet() + " is not found");
        }
        Address address = repository.save(converter.convertToEntity(addressDto));
        return converter.convertToDto(address);
    }

    @Override
    @Transactional
    public void deleteById(UUID id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Address with id = " + id + " is not found");
        }
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public List<AddressDto> getAllByCustomerId(UUID customerId) {
        return repository.findAllByCustomerId(customerId).stream()
                .map(converter::convertToDto)
                .collect(Collectors.toList());
    }
}
