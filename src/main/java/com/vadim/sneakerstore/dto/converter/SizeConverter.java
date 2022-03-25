package com.vadim.sneakerstore.dto.converter;

import com.vadim.sneakerstore.dto.SizeDto;
import com.vadim.sneakerstore.entity.Product;
import com.vadim.sneakerstore.entity.Size;
import com.vadim.sneakerstore.exception.NotFoundException;
import com.vadim.sneakerstore.repository.ProductRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class SizeConverter {

    private final ProductRepository productRepository;

    public SizeConverter(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public SizeDto convertToDto(Size size) {
        UUID id = size.getId();
        Integer productSize = size.getSize();
        Integer amount = size.getAmount();
        UUID productId = size.getProduct().getId();

        return SizeDto.builder()
                .id(id)
                .productId(productId)
                .size(productSize)
                .amount(amount)
                .build();
    }

    public Size convertToEntity(SizeDto sizeDto) {
        UUID id = sizeDto.getId();
        Integer productSize = sizeDto.getSize();
        Integer amount = sizeDto.getAmount();

        Product product = productRepository.findById(sizeDto.getProductId()).orElseThrow(() ->
                new NotFoundException("Product with id = " + sizeDto.getProductId() + " is not found")
        );

        return Size.builder()
                .id(id)
                .size(productSize)
                .amount(amount)
                .product(product)
                .build();
    }
}
