package com.vadim.sneakerstore.dto.converter;

import com.vadim.sneakerstore.dto.CustomerDto;
import com.vadim.sneakerstore.entity.Customer;
import com.vadim.sneakerstore.entity.enums.Role;
import com.vadim.sneakerstore.model.RegistrationRequestDto;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.UUID;

@Component
public class CustomerConverter {

    public Customer convertToEntity(CustomerDto customerDto) {
        final UUID id = customerDto.getId();
        final String email = customerDto.getEmail();
        final Role role = Role.valueOf(customerDto.getRole());
        final String country = customerDto.getCountry();
        final String city = customerDto.getCity();
        final String address = customerDto.getAddress();
        final String firstName = customerDto.getFirstName();
        final String phone = customerDto.getPhone();
        final String lastName = customerDto.getLastName();
        return Customer.builder()
                .id(id)
                .email(email)
                .firstName(firstName)
                .role(role)
                .city(city)
                .country(country)
                .address(address)
                .phone(phone)
                .lastName(lastName)
                .build();
    }

    public CustomerDto convertToDto(Customer customer) {
        final UUID id = customer.getId();
        final String email = customer.getEmail();
        final String firstName = customer.getFirstName();
        final String phone = customer.getPhone();
        final String lastName = customer.getLastName();
        return CustomerDto.builder()
                .id(id)
                .email(email)
                .firstName(firstName)
                .phone(phone)
                .lastName(lastName)
                .build();
    }

    public Customer convertToEntity(RegistrationRequestDto requestDto) {
        final String email = requestDto.getEmail();
        final String city = requestDto.getCity();
        final String address = requestDto.getAddress();
        final String country = requestDto.getCountry();
        final String password = requestDto.getPassword();
        final String firstName = requestDto.getFirstName();
        final String phone = requestDto.getPhone();
        final String lastName = requestDto.getLastName();
        final Role role = Role.USER;
        return Customer.builder()
                .city(city)
                .address(address)
                .country(country)
                .password(password)
                .email(email)
                .firstName(firstName)
                .phone(phone)
                .lastName(lastName)
                .role(role)
                .build();
    }
}
