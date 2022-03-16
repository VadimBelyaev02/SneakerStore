package com.vadim.sneakerstore.dto.converter;

import com.vadim.sneakerstore.dto.CommentDto;
import com.vadim.sneakerstore.entity.Comment;
import com.vadim.sneakerstore.entity.Customer;
import com.vadim.sneakerstore.entity.Product;
import com.vadim.sneakerstore.exception.NotFoundException;
import com.vadim.sneakerstore.repository.CustomerRepository;
import com.vadim.sneakerstore.repository.ProductRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Component
public class CommentConverter {

    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;

    public CommentConverter(ProductRepository productRepository, CustomerRepository customerRepository) {
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
    }

    public Comment convertToEntity(CommentDto comment) {
        final UUID id = comment.getId();
        final String message = comment.getMessage();
        final Integer rate = comment.getRate();
        final LocalDate createdDate = comment.getDate();
        final String username = comment.getCustomer();

        final Product product = productRepository.findById(comment.getProductId()).orElseThrow(() ->
                new NotFoundException("Product with id = " + comment.getProductId() + " is not found")
        );

        final Customer customer = customerRepository.findById(comment.getCustomerId()).orElseThrow(() ->
                new NotFoundException("Customer with id = " + comment.getCustomerId() + " is not found")
        );

        return Comment.builder()
                .id(id)
                .message(message)
                .rate(rate)
                .createdDate(createdDate)
                .username(username)
                .product(product)
                .customer(customer)
                .build();
    }

    public CommentDto convertToDto(Comment comment) {
        final UUID id = comment.getId();
        final UUID productId = comment.getProduct().getId();
        final LocalDate createdDate = comment.getCreatedDate();
        final String message = comment.getMessage();
        final Integer rate = comment.getRate();
        final String username = comment.getUsername();
        final UUID customerId = comment.getCustomer().getId();
        return CommentDto.builder()
                .id(id)
                .productId(productId)
                .date(createdDate)
                .message(message)
                .rate(rate)
                .customer(username)
                .customerId(customerId)
                .build();
    }
}
