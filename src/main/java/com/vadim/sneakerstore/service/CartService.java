package com.vadim.sneakerstore.service;

import com.vadim.sneakerstore.dto.CartDto;
import com.vadim.sneakerstore.dto.FavoriteDto;
import com.vadim.sneakerstore.entity.Cart;

import java.util.List;
import java.util.UUID;

public interface CartService {

    CartDto getById(UUID customerId, UUID productId);

    List<CartDto> getAll();

    CartDto save(CartDto cartDto);

    CartDto update(CartDto cartDto);

    void deleteById(UUID customerId, UUID productId);

    List<CartDto> getByCustomerId(UUID customerId);
}
