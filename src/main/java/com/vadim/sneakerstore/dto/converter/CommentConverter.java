package com.vadim.sneakerstore.dto.converter;

import com.vadim.sneakerstore.dto.CommentDto;
import com.vadim.sneakerstore.entity.Comment;
import com.vadim.sneakerstore.entity.Product;
import com.vadim.sneakerstore.repository.ProductRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Component
public class CommentConverter {

    private final ProductRepository productRepository;

    public CommentConverter(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Comment convertToEntity(CommentDto comment) {
        final UUID id = comment.getId();
        final String message = comment.getMessage();
        final Integer rate = comment.getRate();
        final LocalDate createdDate = comment.getDate();
        final String username = comment.getCustomer();
        final Product product = Objects.requireNonNull(productRepository.getById(comment.getProductId()));
        return Comment.builder()
                .id(id)
                .message(message)
                .rate(rate)
                .createdDate(createdDate)
                .username(username)
                .product(product)
                .build();
    }

    public CommentDto convertToDto(Comment comment) {
        final UUID id = comment.getId();
        final UUID productId = comment.getProduct().getId();
        final LocalDate createdDate = comment.getCreatedDate();
        final String message = comment.getMessage();
        final Integer rate = comment.getRate();
        final String username = comment.getUsername();
        return CommentDto.builder()
                .id(id)
                .productId(productId)
                .date(createdDate)
                .message(message)
                .rate(rate)
                .customer(username)
                .build();
    }
}
