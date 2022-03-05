package com.vadim.sneakerstore.service;

import com.vadim.sneakerstore.dto.CardDto;
import com.vadim.sneakerstore.entity.Card;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CardService {

    Optional<Card> getCard(UUID id);

    List<CardDto> getCards();

    Optional<CardDto> saveCard(CardDto cardDto);

    Optional<CardDto> updateCard(CardDto cardDto);

    void deleteCard(UUID id);
}
