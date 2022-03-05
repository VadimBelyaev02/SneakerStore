package com.vadim.sneakerstore.service.impl;

import com.vadim.sneakerstore.dto.CardDto;
import com.vadim.sneakerstore.entity.Card;
import com.vadim.sneakerstore.repository.CardRepository;
import com.vadim.sneakerstore.service.CardService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CardServiceImpl implements CardService {

    private final CardRepository repository;

    public CardServiceImpl(CardRepository repository) {
        this.repository = repository;
    }

    @Override
    public Card getCard(UUID id) {
        return null;
    }

    @Override
    public CardDto getCards() {
        return null;
    }

    @Override
    public CardDto saveCard(CardDto cardDto) {
        return null;
    }

    @Override
    public CardDto updateCard(CardDto cardDto) {
        return null;
    }

    @Override
    public void deleteCard(UUID id) {

    }
}
