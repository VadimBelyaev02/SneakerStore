package com.vadim.sneakerstore.service.impl;

import com.vadim.sneakerstore.dto.ProductDto;
import com.vadim.sneakerstore.dto.converter.ProductConverter;
import com.vadim.sneakerstore.entity.Product;
import com.vadim.sneakerstore.exception.AlreadyExistsException;
import com.vadim.sneakerstore.exception.NotFoundException;
import com.vadim.sneakerstore.repository.ProductRepository;
import com.vadim.sneakerstore.service.ProductService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
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
    public List<ProductDto> getAll() {
        return repository.findAll().stream()
                .map(converter::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ProductDto getById(UUID id) {
        Product product = repository.findById(id).orElseThrow(() ->
                new NotFoundException("Product with id = " + id + " is not found")
        );
        return converter.convertToDto(product);
    }

    @Override
    @Transactional
    public ProductDto save(ProductDto productDto) {
        if (repository.existsByName(productDto.getName())) {
            throw new AlreadyExistsException("Product with name = " + productDto.getName() + " already exists");
        }
        Product product = repository.save(converter.convertToEntity(productDto));
        return converter.convertToDto(product);
    }

    @Override
    @Transactional
    public ProductDto update(ProductDto productDto) {
        if (!repository.existsById(productDto.getId())) {
            throw new NotFoundException("Product with id = " + productDto.getId() + " is not found");
        }

//        if (!repository.existsByName(productDto.getName())) {
//            throw new NotFoundException("Product with name =" + productDto.getName() + " is not found");
//        }
//        if (Objects.nonNull(productDto.getId()) && repository.existsById(productDto.getId())) {
//            throw new AlreadyExistsException("Product with id = " + productDto.getId() + " already exists");
//        }
        Product product = converter.convertToEntity(productDto);
        return converter.convertToDto(product);
    }

    @Override
    @Transactional
    public void deleteById(UUID id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Product with id = " + id + " is not found");
        }
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public List<ProductDto> getAllPaging(int page, int size, String sortBy) {
        Pageable paging = PageRequest.of(page, size, Sort.by(sortBy));
        return repository.findAll(paging).stream()
                .map(converter::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<ProductDto> getAllByIds(List<UUID> ids) {
        return repository.findAllById(ids).stream()
                .map(converter::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getSortingAndFiltering(Map<String, List<String>> productDto, Pageable pageable) {
        return null;
    }

    @Override
    @Transactional
    public List<ProductDto> saveAll(List<ProductDto> productDtos) {
        return repository.saveAll(productDtos.stream()
                .map(converter::convertToEntity)
                .collect(Collectors.toList())).stream()
                .map(converter::convertToDto)
                .collect(Collectors.toList());

    }


//    @Override
//    public List<ProductDto> getSortingAndFiltering(Map<String, List<String>> fields, Pageable pageable) {
//        List<Product> products = repository.findAll(pageable).stream()
//                .filter(product -> fields.getOrDefault("brand", new ArrayList<>(Collections.singleton("*")))
//                        .contains(product.getBrand().))
//                .filter(product -> fields.get(""))
//
//
//                .collect(Collectors.toList());
//    }
}
