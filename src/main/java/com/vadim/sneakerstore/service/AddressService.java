package com.vadim.sneakerstore.service;

import com.vadim.sneakerstore.dto.AddressDto;

import java.util.List;
import java.util.UUID;

public interface AddressService {

    AddressDto getById(UUID id);

    List<AddressDto> getAll();

    AddressDto save(AddressDto addressDto);

    AddressDto update(AddressDto addressDto);

    void deleteById(UUID id);

    List<AddressDto> getAllByCustomerId(UUID customerId);
}
