package com.vadim.sneakerstore.service;

import com.vadim.sneakerstore.dto.ProductDto;
import com.vadim.sneakerstore.entity.Product;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    List<ProductDto> getAll();

    ProductDto getById(UUID id);

    ProductDto save(ProductDto productDto);

    ProductDto update(ProductDto productDto);

    void deleteById(UUID id);

    List<ProductDto> getAllPaging(Pageable pageable);
}
