package com.vadim.sneakerstore.service.impl;

import com.vadim.sneakerstore.dto.CartDto;
import com.vadim.sneakerstore.dto.converter.CartConverter;
import com.vadim.sneakerstore.entity.Cart;
import com.vadim.sneakerstore.exception.AlreadyExistsException;
import com.vadim.sneakerstore.exception.NotFoundException;
import com.vadim.sneakerstore.repository.CartRepository;
import com.vadim.sneakerstore.service.CartService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository repository;
    private final CartConverter converter;

    public CartServiceImpl(CartRepository repository, CartConverter converter) {
        this.repository = repository;
        this.converter = converter;
    }

    @Override
    @Transactional
    public CartDto getById(UUID customerId, UUID productId) {
//        Cart cart = repository.findByCustomerIdAndProductId(customerId, productId).orElseThrow(() ->
//                new NotFoundException("Cart with customer's id = " + customerId + " and product's id = " +
//                        productId + " is not found")
//        );
//        return converter.convertToDto(cart);
        return null;
    }

    @Override
    @Transactional
    public List<CartDto> getAll() {
        return repository.findAll().stream()
                .map(converter::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CartDto save(CartDto cartDto) {
//        if (repository.existsByCustomerIdAndProductId(cartDto.getCustomerId(), cartDto.getProductId())) {
//            throw new AlreadyExistsException("Cart with customer's id = " + cartDto.getCustomerId() +
//                    " and product's id = " + cartDto.getCustomerId() + " already exists");
//        }
//        Cart cart = repository.save(converter.convertToEntity(cartDto));
//        return converter.convertToDto(cart);
        return null;
    }

    @Override
    @Transactional
    public CartDto update(CartDto cartDto) {
//        if (!repository.existsByCustomerIdAndProductId(cartDto.getCustomerId(), cartDto.getProductId())) {
//            throw new NotFoundException("Cart with customer's id = " + cartDto.getCustomerId() +
//                    " and product's id = " + cartDto.getProductId() + " is not found");
//        }
//        Cart cart = repository.save(converter.convertToEntity(cartDto));
//        return converter.convertToDto(cart);
        return null;
    }

    @Override
    @Transactional
    public void deleteById(UUID customerId, UUID productId) {
//        if (!repository.existsByCustomerIdAndProductId(customerId, productId)) {
//            throw new NotFoundException("Cart with customer's id = " + customerId +
//                    " and product's id = " + productId + " is not found");
//        }
//        repository.deleteByCustomerIdAndProductId(customerId, productId);
    }

    @Override
    @Transactional
    public List<CartDto> getByCustomerId(UUID customerId) {
//        return repository.findAllByCustomerId(customerId).stream()
//                .map(converter::convertToDto)
//                .collect(Collectors.toList());
        return null;
    }
}
