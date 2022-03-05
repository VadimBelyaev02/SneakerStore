package com.vadim.sneakerstore.dto.converter;

import com.vadim.sneakerstore.dto.CustomerDto;
import com.vadim.sneakerstore.entity.Customer;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class CustomerConverter {

    public Customer convertToEntity(CustomerDto customerDto) {
        final UUID id = customerDto.getId();
        final String email = customerDto.getEmail();
  //      final List<CardacustomerDto.getCards();
        //customerDto.getCart();
       // customerDto.getFavorites();
        final String firstName = customerDto.getFirstName();
        final String phone = customerDto.getPhone();
        final String lastName = customerDto.getLastName();
        return Customer.builder()
                .id(id)
                .email(email)
                .firstName(firstName)
                .phone(phone)
                .lastName(lastName)
                .build();
    }

    public CustomerDto convertToDto(Customer customer) {
        final UUID id = customer.getId();
        final String email = customer.getEmail();
        //      final List<CardacustomerDto.getCards();
        //customerDto.getCart();
        // customerDto.getFavorites();
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
}
