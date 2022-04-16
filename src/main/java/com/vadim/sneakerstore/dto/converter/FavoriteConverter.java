package com.vadim.sneakerstore.dto.converter;

import com.vadim.sneakerstore.dto.FavoriteDto;
import com.vadim.sneakerstore.entity.*;
import com.vadim.sneakerstore.exception.NotFoundException;
import com.vadim.sneakerstore.repository.CustomerRepository;
import com.vadim.sneakerstore.repository.SizeRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class FavoriteConverter {

    private final CustomerRepository customerRepository;
    private final SizeRepository sizeRepository;

    public FavoriteConverter(CustomerRepository customerRepository, SizeRepository sizeRepository) {
        this.customerRepository = customerRepository;
        this.sizeRepository = sizeRepository;
    }

    public Favorite convertToEntity(FavoriteDto favoriteDto) {
        ProductCustomerId productCustomerId = new ProductCustomerId();

        Customer customer = customerRepository.findById(favoriteDto.getCustomerId()).orElseThrow(() ->
                new NotFoundException("Customer with id = " + favoriteDto.getCustomerId() + " is not found")
        );

        Size size = sizeRepository.findById(favoriteDto.getSizeId()).orElseThrow(() ->
                new NotFoundException("Size with id = " + favoriteDto.getSizeId() + " is not found")
        );

        productCustomerId.setCustomer(customer);
        productCustomerId.setSize(size);

        return Favorite.builder()
                .id(productCustomerId)
                .build();
    }

    public FavoriteDto convertToDto(Favorite favorite) {
    //    final UUID customerId = favorite.getCustomerId();
    //    final UUID productId = favorite.getProductId();

        final UUID customerId = favorite.getId().getCustomer().getId();
        final UUID sizeId = favorite.getId().getSize().getId();


        return FavoriteDto.builder()
                .customerId(customerId)
                .sizeId(sizeId)
                .build();
    }
}
