package com.vadim.sneakerstore.dto.converter;

import com.vadim.sneakerstore.dto.PhotoDto;
import com.vadim.sneakerstore.entity.Picture;
import com.vadim.sneakerstore.entity.Product;
import com.vadim.sneakerstore.exception.NotFoundException;
import com.vadim.sneakerstore.repository.ProductRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PhotoConverter {

    private final ProductRepository productRepository;

    public PhotoConverter(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Picture convertToEntity(PhotoDto photoDto) {
        final UUID id = photoDto.getId();
        final String link = photoDto.getLink();
        final Product product = productRepository.findById(photoDto.getProductId()).orElseThrow(() ->
                new NotFoundException("Product with id = " + photoDto.getProductId() + " is not found")
        );
        return Picture.builder()
                .id(id)
                .link(link)
                .product(product)
                .build();
    }

    public PhotoDto convertToDto(Picture picture) {
        final UUID id = picture.getId();
        final String link = picture.getLink();
        final UUID productId = picture.getProduct().getId();
        return PhotoDto.builder()
                .id(id)
                .link(link)
                .productId(productId)
                .build();
    }
}
