package com.vadim.sneakerstore.service.impl;

import com.vadim.sneakerstore.dto.CardDto;
import com.vadim.sneakerstore.entity.Card;
import com.vadim.sneakerstore.repository.CardRepository;
import com.vadim.sneakerstore.service.CardService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CardServiceImpl implements CardService {

    private final CardRepository repository;

    public CardServiceImpl(CardRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Card> getCard(UUID id) {
        return Optional.empty();
    }

    @Override
    public List<CardDto> getCards() {
        return null;
    }

    @Override
    public Optional<CardDto> saveCard(CardDto cardDto) {
        return Optional.empty();
    }

    @Override
    public Optional<CardDto> updateCard(CardDto cardDto) {
        return Optional.empty();
    }

    @Override
    public void deleteCard(UUID id) {

    }
}
