package com.vadim.sneakerstore.service;

import com.vadim.sneakerstore.dto.ProductDto;
import com.vadim.sneakerstore.entity.Product;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    List<ProductDto> getAllProducts();

    ProductDto getProduct(UUID id);

    ProductDto saveProduct(ProductDto productDto);

    ProductDto updateProduct(ProductDto productDto);

    void deleteProduct(UUID id);

    List<ProductDto> findAllPaging(Pageable pageable);
}
