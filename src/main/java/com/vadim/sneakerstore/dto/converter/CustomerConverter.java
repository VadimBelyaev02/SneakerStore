package com.vadim.sneakerstore.dto.converter;

import com.vadim.sneakerstore.dto.CustomerDto;
import com.vadim.sneakerstore.entity.Card;
import com.vadim.sneakerstore.entity.Comment;
import com.vadim.sneakerstore.entity.Customer;
import com.vadim.sneakerstore.entity.Product;
import com.vadim.sneakerstore.entity.enums.Role;
import com.vadim.sneakerstore.model.RegistrationRequestDto;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

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
          //      .city(city)
           //     .country(country)
            //    .address(address)
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
     //   final String city = customer.getCity();
     //   final String country = customer.getCountry();
     //   final String address = customer.getAddress();
        final String role = customer.getRole().name();

        List<UUID> favoritesIds = new ArrayList<>();
        if (Objects.nonNull(customer.getFavorites())) {
            favoritesIds = customer.getFavorites().stream()
                    .map(Product::getId)
                    .collect(Collectors.toList());
        }

        List<UUID> inCartIds = new ArrayList<>();
        if (Objects.nonNull(customer.getInCart())) {
            inCartIds = customer.getInCart().stream()
                    .map(Product::getId)
                    .collect(Collectors.toList());
        }

        List<UUID> cardIds = new ArrayList<>();
        if (Objects.nonNull(customer.getCards())) {
            cardIds = customer.getCards().stream()
                    .map(Card::getId)
                    .collect(Collectors.toList());
        }

        List<UUID> commentIds = new ArrayList<>();
        if (Objects.nonNull(customer.getComments())) {
            commentIds = customer.getComments().stream()
                    .map(Comment::getId)
                    .collect(Collectors.toList());
        }

//        List<UUID> favoriteIds = new ArrayList<>();
//        if (Objects.nonNull(customer.getFavorites())) {
//            favoriteIds = customer.getFavorites().stream()
//                    .map(Product::getId)
//                    .collect(Collectors.toList());
//        }

        return CustomerDto.builder()
                .id(id)
                .email(email)
                .firstName(firstName)
          //      .city(city)
          //      .country(country)
          //      .address(address)
                .role(role)
                .phone(phone)
                .lastName(lastName)
                .cardsIds(cardIds)
                .commentsIds(commentIds)
                .inCartIds(inCartIds)
                .favoritesIds(favoritesIds)
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
              //  .city(city)
              //  .address(address)
              //  .country(country)
                .password(password)
                .email(email)
                .firstName(firstName)
                .phone(phone)
                .lastName(lastName)
                .role(role)
                .build();
    }
}
