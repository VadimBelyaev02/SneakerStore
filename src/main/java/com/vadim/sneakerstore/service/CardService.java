package com.vadim.sneakerstore.service;

import com.vadim.sneakerstore.dto.CardDto;
import com.vadim.sneakerstore.entity.Card;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CardService {

    CardDto getCard(UUID id);

    List<CardDto> getCards();

    CardDto saveCard(CardDto cardDto);
    CardDto updateCard(CardDto cardDto);

    void deleteCard(UUID id);
}
