package com.vadim.sneakerstore.service;

import com.vadim.sneakerstore.dto.OrderDto;

import java.util.List;
import java.util.UUID;

public interface OrderService {

    OrderDto getById(UUID id);

    List<OrderDto> getAll();

    OrderDto save(OrderDto orderDto);

    OrderDto update(OrderDto orderDto);

    void deleteById(UUID id);
}
