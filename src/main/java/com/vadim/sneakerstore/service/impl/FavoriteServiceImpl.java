package com.vadim.sneakerstore.service.impl;

import com.vadim.sneakerstore.dto.FavoriteDto;
import com.vadim.sneakerstore.dto.converter.FavoriteConverter;
import com.vadim.sneakerstore.entity.Customer;
import com.vadim.sneakerstore.entity.Favorite;
import com.vadim.sneakerstore.entity.ProductCustomerId;
import com.vadim.sneakerstore.exception.AlreadyExistsException;
import com.vadim.sneakerstore.exception.NotFoundException;
import com.vadim.sneakerstore.repository.CustomerRepository;
import com.vadim.sneakerstore.repository.FavoriteRepository;
import com.vadim.sneakerstore.repository.ProductRepository;
import com.vadim.sneakerstore.service.FavoriteService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteRepository repository;
    private final FavoriteConverter converter;

    public FavoriteServiceImpl(FavoriteRepository repository, FavoriteConverter converter) {
        this.repository = repository;
        this.converter = converter;
    }

    @Override
    @Transactional
    public FavoriteDto getById(UUID customerId, UUID sizeId) {
        Favorite favorite = repository.findByCustomerIdAndProductId(customerId, sizeId).orElseThrow(() ->
                new NotFoundException("Favorite with customer id = " + customerId + " and size id = " +
                        sizeId + " is not found")
        );
        return converter.convertToDto(favorite);
    }

    @Override
    @Transactional
    public List<FavoriteDto> getAll() {
        return repository.findAll().stream()
                .map(converter::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<FavoriteDto> getByCustomerId(UUID customerId) {
        return repository.findAllByCustomerId(customerId).stream()
                .map(converter::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public FavoriteDto save(FavoriteDto favoriteDto) {
        if (repository.existsByCustomerIdAndProductId(favoriteDto.getCustomerId(), favoriteDto.getSizeId())) {
            throw new AlreadyExistsException("Favorite with customer id = " + favoriteDto.getCustomerId()
            + " and size id = " + favoriteDto.getSizeId() + " already exists");
        }
        Favorite favorite = repository.save(converter.convertToEntity(favoriteDto));
        return converter.convertToDto(favorite);
    }

    @Override
    @Transactional
    public FavoriteDto update(FavoriteDto favoriteDto) {
        if (!repository.existsByCustomerIdAndProductId(favoriteDto.getCustomerId(), favoriteDto.getSizeId())) {
            throw new NotFoundException("Favorite with customer id = " + favoriteDto.getCustomerId()
                    + " and size id = " + favoriteDto.getSizeId() + " is not found");
        }
        Favorite favorite = repository.save(converter.convertToEntity(favoriteDto));
        return converter.convertToDto(favorite);
    }

    @Override
    @Transactional
    public void deleteById(UUID customerId, UUID sizeId) {
        if (!repository.existsByCustomerIdAndProductId(customerId, sizeId)) {
            throw new NotFoundException("Favorite with customer id = " + customerId
                    + " and product id = " + sizeId + " is not found");
        }
        repository.deleteByCustomerIdAndProductId(customerId, sizeId);
    }



}
