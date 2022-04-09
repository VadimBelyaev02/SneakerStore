package com.vadim.sneakerstore.service;

import com.vadim.sneakerstore.dto.ProductDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface ProductService {

    List<ProductDto> getAll();

    ProductDto getById(UUID id);

    ProductDto save(ProductDto productDto);

    ProductDto update(ProductDto productDto);

    void deleteById(UUID id);

    List<ProductDto> getAllPaging(int page, int size, String sortBy);

    List<ProductDto> getAllByIds(List<UUID> ids);

    List<ProductDto> getSortingAndFiltering(Map<String, List<String>> productDto, Pageable pageable);

    List<ProductDto> saveAll(List<ProductDto> productDtos);
}
