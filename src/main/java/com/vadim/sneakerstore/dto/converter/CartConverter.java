package com.vadim.sneakerstore.dto.converter;

import com.vadim.sneakerstore.dto.CartDto;
import com.vadim.sneakerstore.entity.Cart;
import com.vadim.sneakerstore.entity.Customer;
import com.vadim.sneakerstore.entity.Product;
import com.vadim.sneakerstore.entity.ProductCustomerId;
import com.vadim.sneakerstore.exception.NotFoundException;
import com.vadim.sneakerstore.repository.CustomerRepository;
import com.vadim.sneakerstore.repository.ProductRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CartConverter {

    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;

    public CartConverter(ProductRepository productRepository, CustomerRepository customerRepository) {
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
    }

    public Cart convertToEntity(CartDto cartDto) {
        ProductCustomerId productCustomerId = new ProductCustomerId();

        final Customer customer = customerRepository.findById(cartDto.getCustomerId()).orElseThrow(() ->
                new NotFoundException("Customer with id = " + cartDto.getCustomerId() + " is not found")
        );

        final Product product = productRepository.findById(cartDto.getProductId()).orElseThrow(() ->
                new NotFoundException("Product with id = " + cartDto.getProductId() + " is not found")
        );

        productCustomerId.setProduct(product);
        productCustomerId.setCustomer(customer);

        return Cart.builder()
                .id(productCustomerId)
                .build();
    }

    public CartDto convertToDto(Cart cart) {
     //   final UUID customerId = cart.getCustomerId();
     //   final UUID productId = cart.getProductId();

           final UUID customerId = cart.getId().getCustomer().getId();
           final UUID productId = cart.getId().getProduct().getId();

        return CartDto.builder()
                .customerId(customerId)
                .productId(productId)
                .build();
    }
}
