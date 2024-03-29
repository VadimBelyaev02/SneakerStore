package com.vadim.sneakerstore.dto.converter;

import com.vadim.sneakerstore.dto.CustomerDto;
import com.vadim.sneakerstore.entity.*;
import com.vadim.sneakerstore.entity.enums.Role;
import com.vadim.sneakerstore.entity.enums.Theme;
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
        final String password = customerDto.getPassword();
        final String firstName = customerDto.getFirstName();
        final String phone = customerDto.getPhone();
        final String lastName = customerDto.getLastName();
        final String avatar = customerDto.getAvatar();
        final Theme theme = customerDto.getTheme();

        Customer customer = Customer.builder()
                .id(id)
                .theme(theme)
                .email(email)
                .firstName(firstName)
                .role(role)
                .phone(phone)
                .password(password)
                .lastName(lastName)
                .avatar(avatar)
                .build();

        List<Address> addresses = new ArrayList<>();
        if (Objects.nonNull(customerDto.getAddressesIds())) {
            addresses = addressRepository.findAllById(customerDto.getAddressesIds());
            customer.setAddresses(addresses);
        }

        List<Order> orders = new ArrayList<>();
        if (Objects.nonNull(customerDto.getOrdersIds())) {
            orders = orderRepository.findAllById(customerDto.getOrdersIds());
            customer.setOrders(orders);
        }

        List<Card> cards = new ArrayList<>();
        if (Objects.nonNull(customerDto.getCardsIds())) {
            cards = cardRepository.findAllById(customerDto.getCardsIds());
            customer.setCards(cards);
        }

        List<Comment> comments = new ArrayList<>();
        if (Objects.nonNull(customerDto.getCommentsIds())) {
            comments = commentRepository.findAllById(customerDto.getCommentsIds());
            customer.setComments(comments);
        }

        List<Product> favorites = new ArrayList<>();
        if (Objects.nonNull(customerDto.getFavoritesIds())) {
            favorites = productRepository.findAllById(customerDto.getFavoritesIds());
            customer.setFavorites(favorites);
        }


        List<Product> inCart = new ArrayList<>();
        if (Objects.nonNull(customerDto.getInCartIds())) {
            inCart = productRepository.findAllById(customerDto.getInCartIds());
            customer.setInCart(inCart);
        }

        return customer;
    }

    public CustomerDto convertToDto(Customer customer) {
        final UUID id = customer.getId();
        final String email = customer.getEmail();
        final String firstName = customer.getFirstName();
        final String phone = customer.getPhone();
        final String lastName = customer.getLastName();
        final String role = customer.getRole().name();
        final String avatar = customer.getAvatar();
        final Theme theme = customer.getTheme();

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
                .theme(theme)
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

    public void updateCustomer(Customer customer, CustomerDto customerDto) {
        customer.setEmail(customerDto.getEmail());
        customer.setAvatar(customerDto.getAvatar());
        customer.setFirstName(customerDto.getFirstName());
        customer.setLastName(customerDto.getLastName());
        customer.setPhone(customerDto.getPhone());
        customer.setRole(Role.valueOf(customerDto.getRole()));

        List<Address> addresses = new ArrayList<>();
        if (Objects.nonNull(customerDto.getAddressesIds())) {
            addresses = addressRepository.findAllById(customerDto.getAddressesIds());
        }

        List<Order> orders = new ArrayList<>();
        if (Objects.nonNull(customerDto.getOrdersIds())) {
            orders = orderRepository.findAllById(customerDto.getOrdersIds());
        }

        List<Card> cards = new ArrayList<>();
        if (Objects.nonNull(customerDto.getCardsIds())) {
            cards = cardRepository.findAllById(customerDto.getCardsIds());
        }

        List<Comment> comments = new ArrayList<>();
        if (Objects.nonNull(customerDto.getCommentsIds())) {
            comments = commentRepository.findAllById(customerDto.getCommentsIds());
        }

        List<Product> favorites = new ArrayList<>();
        if (Objects.nonNull(customerDto.getFavoritesIds())) {
            favorites = productRepository.findAllById(customerDto.getFavoritesIds());
        }

        List<Product> inCart = new ArrayList<>();
        if (Objects.nonNull(customerDto.getInCartIds())) {
            inCart = productRepository.findAllById(customerDto.getInCartIds());
        }

        customer.setCards(cards);
        customer.setComments(comments);
        customer.setAddresses(addresses);
        customer.setFavorites(favorites);
        customer.setInCart(inCart);
        customer.setOrders(orders);
    }
}
