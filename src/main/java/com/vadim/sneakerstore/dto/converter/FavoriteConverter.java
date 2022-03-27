package com.vadim.sneakerstore.dto.converter;

import com.vadim.sneakerstore.dto.FavoriteDto;
import com.vadim.sneakerstore.entity.Customer;
import com.vadim.sneakerstore.entity.Favorite;
import com.vadim.sneakerstore.entity.Product;
import com.vadim.sneakerstore.entity.ProductCustomerId;
import com.vadim.sneakerstore.exception.NotFoundException;
import com.vadim.sneakerstore.repository.CustomerRepository;
import com.vadim.sneakerstore.repository.ProductRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class FavoriteConverter {

    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    public FavoriteConverter(CustomerRepository customerRepository, ProductRepository productRepository) {
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }

    public Favorite convertToEntity(FavoriteDto favoriteDto) {
        ProductCustomerId productCustomerId = new ProductCustomerId();

        Customer customer = customerRepository.findById(favoriteDto.getCustomerId()).orElseThrow(() ->
                new NotFoundException("Customer with id = " + favoriteDto.getCustomerId() + " is not found")
        );

        Product product = productRepository.findById(favoriteDto.getProductId()).orElseThrow(() ->
                new NotFoundException("Product with id = " + favoriteDto.getProductId() + " is not found")
        );

        productCustomerId.setCustomer(customer);
        productCustomerId.setProduct(product);

        return Favorite.builder()
                .id(productCustomerId)
                .build();
    }

    public FavoriteDto convertToDto(Favorite favorite) {
        final UUID customerId = favorite.getCustomerId();
        final UUID productId = favorite.getProductId();

        return FavoriteDto.builder()
                .customerId(customerId)
                .productId(productId)
                .build();
    }
}
