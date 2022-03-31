package com.vadim.sneakerstore.dto.converter;

import com.vadim.sneakerstore.dto.ProductDto;
import com.vadim.sneakerstore.dto.StockDto;
import com.vadim.sneakerstore.entity.Product;
import com.vadim.sneakerstore.entity.Stock;
import com.vadim.sneakerstore.exception.NotFoundException;
import com.vadim.sneakerstore.repository.ProductRepository;
import liquibase.pro.packaged.S;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Component
public class StockConverter {

    private final ProductRepository productRepository;

    public StockConverter(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Stock convertToEntity(StockDto stockDto) {
        final UUID id = stockDto.getId();
        final Integer amount = stockDto.getAmount();
        final Integer size = stockDto.getSize();

        Product product = new Product();
        if (Objects.nonNull(stockDto.getProductId())) {
            product = productRepository.findById(stockDto.getProductId()).orElseThrow(() ->
                    new NotFoundException("Product with id = " + stockDto.getId() + " is not found")
            );
        }

        return Stock.builder()
                .id(id)
                .size(size)
                .amount(amount)
                .product(product)
                .build();
    }

    public StockDto convertToDto(Stock stock) {
        final UUID id = stock.getId();
        final Integer amount = stock.getAmount();
        final Integer size = stock.getSize();
        final UUID productId = stock.getProduct().getId();

        return StockDto.builder()
                .id(id)
                .amount(amount)
                .size(size)
                .productId(productId)
                .build();
    }
}
