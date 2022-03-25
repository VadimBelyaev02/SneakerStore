package com.vadim.sneakerstore.service.impl;

import com.vadim.sneakerstore.dto.OrderDto;
import com.vadim.sneakerstore.dto.converter.OrderConverter;
import com.vadim.sneakerstore.entity.Order;
import com.vadim.sneakerstore.exception.AlreadyExistsException;
import com.vadim.sneakerstore.exception.NotFoundException;
import com.vadim.sneakerstore.repository.OrderRepository;
import com.vadim.sneakerstore.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repository;
    private final OrderConverter converter;

    public OrderServiceImpl(OrderRepository repository, OrderConverter converter) {
        this.repository = repository;
        this.converter = converter;
    }

    @Override
    @Transactional
    public OrderDto getById(UUID id) {
        Order order = repository.findById(id).orElseThrow(() ->
                new NotFoundException("Order with id = " + id + " is not found")
        );
        return converter.convertToDto(order);
    }

    @Override
    @Transactional
    public List<OrderDto> getAll() {
        return repository.findAll().stream()
                .map(converter::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public OrderDto save(OrderDto orderDto) {
        if (repository.existsById(orderDto.getId())) {
            throw new AlreadyExistsException("Order with id = " + orderDto.getId() + " already exists");
        };
        Order order = repository.save(converter.convertToEntity(orderDto));
        return converter.convertToDto(order);
    }

    @Override
    @Transactional
    public OrderDto update(OrderDto orderDto) {
        if (!repository.existsById(orderDto.getId())) {
            throw new NotFoundException("Order with id = " + orderDto.getId() + " is not found");
        };
        Order order = repository.save(converter.convertToEntity(orderDto));
        return converter.convertToDto(order);
    }

    @Override
    @Transactional
    public void deleteById(UUID id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Order with id = " + id + " is not found");
        }
        repository.deleteById(id);
    }
}
