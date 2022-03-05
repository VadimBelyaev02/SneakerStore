package com.vadim.sneakerstore.service.impl;

import com.vadim.sneakerstore.dto.ProductDto;
import com.vadim.sneakerstore.dto.converter.ProductConverter;
import com.vadim.sneakerstore.entity.Product;
import com.vadim.sneakerstore.exception.NotFoundException;
import com.vadim.sneakerstore.repository.ProductRepository;
import com.vadim.sneakerstore.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;
    private final ProductConverter converter;

    public ProductServiceImpl(ProductRepository repository, ProductConverter converter) {
        this.repository = repository;
        this.converter = converter;
    }

    @Override
    @Transactional
    public List<ProductDto> getAllProducts() {
        return repository.findAll().stream()
                .map(converter::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ProductDto getProduct(UUID id) {
        Product product = repository.findById(id).orElseThrow(() ->
                new NotFoundException("Product with id=" + id + " is not found")
        );
        return converter.convertToDto(product);
    }

    @Override
    @Transactional
    public ProductDto saveProduct(ProductDto productDto) {
        return null;
    }

    @Override
    @Transactional
    public ProductDto updateProduct(ProductDto productDto) {
        if (!repository.existsById(productDto.getId())) {
            throw new NotFoundException("Product with id=" + productDto.getId() + " is not found");
        }
        Product product = converter.convertToEntity(productDto);
        return converter.convertToDto(product);
    }

    @Override
    @Transactional
    public void deleteProduct(UUID id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Product with id=" + id + " is not found");
        }
        repository.deleteById(id);
    }
}
