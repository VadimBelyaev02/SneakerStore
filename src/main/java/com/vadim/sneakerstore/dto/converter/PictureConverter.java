package com.vadim.sneakerstore.dto.converter;

import com.vadim.sneakerstore.dto.PictureDto;
import com.vadim.sneakerstore.entity.Picture;
import com.vadim.sneakerstore.entity.Product;
import com.vadim.sneakerstore.exception.NotFoundException;
import com.vadim.sneakerstore.repository.ProductRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PictureConverter {

    private final ProductRepository productRepository;

    public PictureConverter(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Picture convertToEntity(PictureDto pictureDto) {
        final UUID id = pictureDto.getId();
        final String link = pictureDto.getLink();
        final Product product = productRepository.findById(pictureDto.getProductId()).orElseThrow(() ->
                new NotFoundException("Product with id = " + pictureDto.getProductId() + " is not found")
        );
        return Picture.builder()
                .id(id)
                .link(link)
                .product(product)
                .build();
    }

    public PictureDto convertToDto(Picture picture) {
        final UUID id = picture.getId();
        final String link = picture.getLink();
        final UUID productId = picture.getProduct().getId();
        return PictureDto.builder()
                .id(id)
                .link(link)
                .productId(productId)
                .build();
    }
}
