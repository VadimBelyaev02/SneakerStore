package com.vadim.sneakerstore.dto.converter;

import com.vadim.sneakerstore.dto.CardDto;
import com.vadim.sneakerstore.entity.Card;
import com.vadim.sneakerstore.entity.Customer;
import com.vadim.sneakerstore.exception.NotFoundException;
import com.vadim.sneakerstore.repository.CustomerRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class CardConverter {

    private final CustomerRepository customerRepository;

    public CardConverter(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public CardDto convertToDto(Card card) {

        final UUID id = card.getId();
        final String cvv = card.getCvv();
        final String number = card.getNumber();
        final LocalDate date = card.getValidityDate();
        final String owner = card.getOwner();

        final List<UUID> customersIds = card.getCustomers().stream()
                .map(Customer::getId)
                .collect(Collectors.toList());

        return CardDto.builder()
                .id(id)
                .cvv(cvv)
                .number(number)
                .validityDate(date)
                .owner(owner)
                .customersIds(customersIds)
                .build();
    }

    public Card convertToEntity(CardDto cardDto) {
        final UUID id = cardDto.getId();
        final String cvv = cardDto.getCvv();
        final String number = cardDto.getNumber();
        final LocalDate date = cardDto.getValidityDate();
        final String owner = cardDto.getOwner();

        List<Customer> customers = new ArrayList<>();
        if (Objects.nonNull(cardDto.getCustomersIds())) {
            customers = customerRepository.findAllById(cardDto.getCustomersIds());
        }

        return Card.builder()
                .id(id)
                .cvv(cvv)
                .number(number)
                .validityDate(date)
                .owner(owner)
                .customers(customers)
                .build();
    }
}
