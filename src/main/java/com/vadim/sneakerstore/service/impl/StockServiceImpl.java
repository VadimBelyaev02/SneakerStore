package com.vadim.sneakerstore.service.impl;

import com.vadim.sneakerstore.dto.StockDto;
import com.vadim.sneakerstore.dto.converter.StockConverter;
import com.vadim.sneakerstore.entity.Stock;
import com.vadim.sneakerstore.exception.AlreadyExistsException;
import com.vadim.sneakerstore.exception.NotFoundException;
import com.vadim.sneakerstore.repository.StockRepository;
import com.vadim.sneakerstore.service.StockService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class StockServiceImpl implements StockService {

    private final StockRepository repository;
    private final StockConverter converter;

    public StockServiceImpl(StockRepository repository, StockConverter converter) {
        this.repository = repository;
        this.converter = converter;
    }

    @Override
    @Transactional
    public StockDto getById(UUID id) {
        Stock stock = repository.findById(id).orElseThrow(() ->
                new NotFoundException("Stock with is = " + id + " is not found")
        );
        return converter.convertToDto(stock);
    }

    @Override
    @Transactional
    public List<StockDto> getAll() {
        return repository.findAll().stream()
                .map(converter::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public StockDto save(StockDto stockDto) {
        if (repository.existsById(stockDto.getId())) {
            throw new AlreadyExistsException("Stock with id = " + stockDto.getId() + " already exists");
        }
        Stock stock = repository.save(converter.convertToEntity(stockDto));
        return converter.convertToDto(stock);
    }

    @Override
    @Transactional
    public StockDto update(StockDto stockDto) {
        UUID.randomUUID()
        if (!repository.existsById(stockDto.getId())) {
            throw new NotFoundException("Stock with id = " + stockDto.getId() + " is not found");
        }
        Stock stock = repository.save(converter.convertToEntity(stockDto));
        return converter.convertToDto(stock);


    }

    @Override
    @Transactional
    public void deleteById(UUID id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Stock with id = " + id + " is not found");
        }
        repository.deleteById(id);
    }
}
