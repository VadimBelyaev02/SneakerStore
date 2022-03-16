package com.vadim.sneakerstore.dto.converter;

import com.vadim.sneakerstore.dto.CardDto;
import com.vadim.sneakerstore.entity.Card;
import com.vadim.sneakerstore.entity.Customer;
import com.vadim.sneakerstore.exception.NotFoundException;
import com.vadim.sneakerstore.repository.CustomerRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.UUID;

@Component
public class CardConverter {

    private final CustomerRepository customerRepository;

    public CardConverter(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public CardDto convertToDto(Card card) {
        final UUID id = card.getId();
        final UUID customerId = card.getCustomer().getId();
        final Integer cvv = card.getCvv();
        final String number = card.getNumber();
        final LocalDate date = card.getValidityDate();
        final String owner = card.getOwner();
        return CardDto.builder()
                .id(id)
                .customerId(customerId)
                .cvv(cvv)
                .number(number)
                .validityDate(date)
                .owner(owner)
                .build();
    }

    public Card convertToEntity(CardDto cardDto) {
        final UUID id = cardDto.getId();
        final Integer cvv = cardDto.getCvv();
        final String number = cardDto.getNumber();
        final LocalDate date = cardDto.getValidityDate();
        final String owner = cardDto.getOwner();

        final Customer customer = customerRepository.findById(cardDto.getCustomerId()).orElseThrow(() ->
                new NotFoundException("Customer with id " + cardDto.getCustomerId() + " is not found")
        );

        return Card.builder()
                .id(id)
                .cvv(cvv)
                .number(number)
                .validityDate(date)
                .owner(owner)
                .customer(customer)
                .build();
    }
}