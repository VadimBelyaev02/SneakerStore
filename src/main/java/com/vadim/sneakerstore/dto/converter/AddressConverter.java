package com.vadim.sneakerstore.dto.converter;

import com.vadim.sneakerstore.dto.AddressDto;
import com.vadim.sneakerstore.entity.Address;
import com.vadim.sneakerstore.entity.Customer;
import com.vadim.sneakerstore.repository.CustomerRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class AddressConverter {

    private final CustomerRepository customerRepository;

    public AddressConverter(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Address convertToEntity(AddressDto addressDto) {
        final UUID id = addressDto.getId();
        final String city = addressDto.getCity();
        final String country = addressDto.getCountry();
        final String street = addressDto.getStreet();
        final String house = addressDto.getHouse();
        final String state = addressDto.getState();
        final String apartment = addressDto.getApartment();

        List<Customer> customers = new ArrayList<>();
        if (Objects.nonNull(addressDto.getCustomersIds())) {
            customers = customerRepository.findAllById(addressDto.getCustomersIds());
        }

        return Address.builder()
                .id(id)
                .city(city)
                .street(street)
                .country(country)
                .house(house)
                .apartment(apartment)
                .customers(customers)
                .state(state)
                .build();
    }

    public AddressDto convertToDto(Address address) {
        final UUID id = address.getId();
        final String city = address.getCity();
        final String state = address.getState();
        final String country = address.getCountry();
        final String street = address.getStreet();
        final String house = address.getHouse();
        final String apartment = address.getApartment();


        List<UUID> customersIds = new ArrayList<>();
        if (Objects.nonNull(address.getCustomers())) {
            customersIds = address.getCustomers().stream()
                    .map(Customer::getId)
                    .collect(Collectors.toList());
        }

        return AddressDto.builder()
                .id(id)
                .apartment(apartment)
                .house(house)
                .city(city)
                .street(street)
                .state(state)
                .country(country)
                .customersIds(customersIds)
                .build();
    }
}
