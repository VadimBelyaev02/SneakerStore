package com.vadim.sneakerstore.service;

import com.vadim.sneakerstore.dto.CardDto;
import com.vadim.sneakerstore.entity.Card;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CardService {

    CardDto getById(UUID id);

    List<CardDto> getAll();

    CardDto save(CardDto cardDto);

    CardDto update(CardDto cardDto);

    void deleteById(UUID id);

    List<CardDto> getByCustomerId(UUID userId);
}
