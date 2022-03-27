package com.vadim.sneakerstore.service;

import com.vadim.sneakerstore.dto.FavoriteDto;
import com.vadim.sneakerstore.dto.OrderDto;

import java.util.List;
import java.util.UUID;

public interface FavoriteService {

    FavoriteDto getById(UUID customerId, UUID productId);

    List<FavoriteDto> getAll();

    FavoriteDto save(FavoriteDto favoriteDto);

    FavoriteDto update(FavoriteDto favoriteDto);

    void deleteById(UUID customerId, UUID productId);

    List<FavoriteDto> getByCustomerId(UUID customerId);
}
