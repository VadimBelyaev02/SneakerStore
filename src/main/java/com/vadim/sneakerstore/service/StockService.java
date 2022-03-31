package com.vadim.sneakerstore.service;

import com.vadim.sneakerstore.dto.StockDto;
import com.vadim.sneakerstore.entity.Stock;

import java.util.List;
import java.util.UUID;

public interface StockService {

    StockDto getById(UUID id);

    List<StockDto> getAll();

    StockDto save(StockDto stockDto);

    StockDto update(StockDto stockDto);

    void deleteById(UUID id);
}
