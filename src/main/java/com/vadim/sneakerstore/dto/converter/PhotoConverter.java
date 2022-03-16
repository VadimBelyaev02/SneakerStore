package com.vadim.sneakerstore.dto.converter;

import com.vadim.sneakerstore.dto.PhotoDto;
import com.vadim.sneakerstore.entity.Photo;
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

    public Photo convertToEntity(PhotoDto photoDto) {
        final UUID id = photoDto.getId();
        final String url = photoDto.getUrl();
        final Product product = productRepository.findById(photoDto.getProductId()).orElseThrow(() ->
                new NotFoundException("Product with id = " + photoDto.getProductId() + " is not found")
        );
        return Photo.builder()
                .id(id)
                .url(url)
                .product(product)
                .build();
    }

    public PhotoDto convertToDto(Photo photo) {
        final UUID id = photo.getId();
        final String url = photo.getUrl();
        final UUID productId = photo.getProduct().getId();
        return PhotoDto.builder()
                .id(id)
                .url(url)
                .productId(productId)
                .build();
    }
}
