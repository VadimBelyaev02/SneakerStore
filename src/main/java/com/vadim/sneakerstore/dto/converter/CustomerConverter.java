package com.vadim.sneakerstore.dto.converter;

import com.vadim.sneakerstore.dto.CustomerDto;
import com.vadim.sneakerstore.entity.*;
import com.vadim.sneakerstore.entity.enums.Role;
import com.vadim.sneakerstore.model.RegistrationRequestDto;
import com.vadim.sneakerstore.repository.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class CustomerConverter {

    private final AddressRepository addressRepository;
    private final OrderRepository orderRepository;
    private final CardRepository cardRepository;
    private final CommentRepository commentRepository;
    private final ProductRepository productRepository;

    public CustomerConverter(AddressRepository addressRepository,
                             OrderRepository orderRepository,
                             CardRepository cardRepository,
                             CommentRepository commentRepository,
                             ProductRepository productRepository) {
        this.addressRepository = addressRepository;
        this.orderRepository = orderRepository;
        this.cardRepository = cardRepository;
        this.commentRepository = commentRepository;
        this.productRepository = productRepository;
    }

    public Customer convertToEntity(CustomerDto customerDto) {
        final UUID id = customerDto.getId();
        final String email = customerDto.getEmail();
        final Role role = Role.valueOf(customerDto.getRole());
        final String firstName = customerDto.getFirstName();
        final String phone = customerDto.getPhone();
        final String lastName = customerDto.getLastName();
        final String avatar = customerDto.getAvatar();

        List<Address> addresses = new ArrayList<>();
        if (Objects.nonNull(customerDto.getAddressesIds())) {
            addressRepository.findAllById(customerDto.getAddressesIds());
        }

        List<Order> orders = new ArrayList<>();
        if (Objects.nonNull(customerDto.getOrdersIds())) {
            orderRepository.findAllById(customerDto.getOrdersIds());
        }

        List<Card> cards = new ArrayList<>();
        if (Objects.nonNull(customerDto.getCardsIds())) {
            cardRepository.findAllById(customerDto.getCardsIds());
        }

        List<Comment> comments = new ArrayList<>();
        if (Objects.nonNull(customerDto.getCommentsIds())) {
            commentRepository.findAllById(customerDto.getCommentsIds());
        }

        List<Product> favorites = new ArrayList<>();
        if (Objects.nonNull(customerDto.getFavoritesIds())) {
            productRepository.findAllById(customerDto.getFavoritesIds());
        }

        List<Product> inCart = new ArrayList<>();
        if (Objects.nonNull(customerDto.getInCartIds())) {
            productRepository.findAllById(customerDto.getInCartIds());
        }

        return Customer.builder()
                .id(id)
                .email(email)
                .firstName(firstName)
                .role(role)
                .phone(phone)
                .lastName(lastName)
                .avatar(avatar)
                .addresses(addresses)
                .orders(orders)
                .cards(cards)
                .comments(comments)
                .favorites(favorites)
                .inCart(inCart)
                .build();
    }

    public CustomerDto convertToDto(Customer customer) {
        final UUID id = customer.getId();
        final String email = customer.getEmail();
        final String firstName = customer.getFirstName();
        final String phone = customer.getPhone();
        final String lastName = customer.getLastName();
        final String role = customer.getRole().name();
        final String avatar = customer.getAvatar();

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

        List<UUID> addressesIds = new ArrayList<>();
        if (Objects.nonNull(customer.getAddresses())) {
            addressesIds = customer.getAddresses().stream()
                    .map(Address::getId)
                    .collect(Collectors.toList());
        }

        List<UUID> ordersIds = new ArrayList<>();
        if (Objects.nonNull(customer.getOrders())) {
            ordersIds = customer.getOrders().stream()
                    .map(Order::getId)
                    .collect(Collectors.toList());
        }

        return CustomerDto.builder()
                .id(id)
                .email(email)
                .firstName(firstName)
                .role(role)
                .phone(phone)
                .lastName(lastName)
                .avatar(avatar)
                .cardsIds(cardIds)
                .commentsIds(commentIds)
                .inCartIds(inCartIds)
                .favoritesIds(favoritesIds)
                .addressesIds(addressesIds)
                .ordersIds(ordersIds)
                .build();
    }

    public Customer convertToEntity(RegistrationRequestDto requestDto) {
        final String email = requestDto.getEmail();
        final String password = requestDto.getPassword();
        final String firstName = requestDto.getFirstName();
        final String phone = requestDto.getPhone();
        final String lastName = requestDto.getLastName();
        final Role role = Role.USER;

        return Customer.builder()
                 .password(password)
                .email(email)
                .firstName(firstName)
                .phone(phone)
                .lastName(lastName)
                .role(role)
                .build();
    }
}
