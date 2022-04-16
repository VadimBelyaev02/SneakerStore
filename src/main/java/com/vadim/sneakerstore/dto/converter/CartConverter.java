package com.vadim.sneakerstore.dto.converter;

import com.vadim.sneakerstore.dto.CartDto;
import com.vadim.sneakerstore.entity.*;
import com.vadim.sneakerstore.exception.NotFoundException;
import com.vadim.sneakerstore.repository.CustomerRepository;
import com.vadim.sneakerstore.repository.SizeRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CartConverter {

    private final SizeRepository sizeRepository;
    private final CustomerRepository customerRepository;

    public CartConverter(SizeRepository sizeRepository, CustomerRepository customerRepository) {
        this.sizeRepository = sizeRepository;
        this.customerRepository = customerRepository;
    }

    public Cart convertToEntity(CartDto cartDto) {
        ProductCustomerId productCustomerId = new ProductCustomerId();


        final Customer customer = customerRepository.findById(cartDto.getCustomerId()).orElseThrow(() ->
                new NotFoundException("Customer with id = " + cartDto.getCustomerId() + " is not found")
        );

        final Size size = sizeRepository.findById(cartDto.getSizeId()).orElseThrow(() ->
                new NotFoundException("Size with id = " + cartDto.getSizeId() + " is not found")
        );

        productCustomerId.setSize(size);
        productCustomerId.setCustomer(customer);

        return Cart.builder()
                .id(productCustomerId)
                .build();
    }

    public CartDto convertToDto(Cart cart) {
        final UUID customerId = cart.getCustomerId();
        final UUID sizeId = cart.getProductId();

        return CartDto.builder()
                .customerId(customerId)
                .sizeId(sizeId)
                .build();
    }
}
