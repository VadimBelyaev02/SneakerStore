package com.vadim.sneakerstore.service.impl;

import com.vadim.sneakerstore.dto.CardDto;
import com.vadim.sneakerstore.dto.converter.CardConverter;
import com.vadim.sneakerstore.entity.Card;
import com.vadim.sneakerstore.exception.AlreadyExistsException;
import com.vadim.sneakerstore.exception.NotFoundException;
import com.vadim.sneakerstore.repository.CardRepository;
import com.vadim.sneakerstore.service.CardService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CardServiceImpl implements CardService {

    private final CardRepository repository;
    private final CardConverter converter;

    public CardServiceImpl(CardRepository repository, CardConverter converter) {
        this.repository = repository;
        this.converter = converter;
    }

    @Override
    @Transactional
    public CardDto getById(UUID id) {
        Card card = repository.findById(id).orElseThrow(() ->
                new NotFoundException("Card with id = " + id + " is not found")
        );
        return converter.convertToDto(card);
    }

    @Override
    @Transactional
    public List<CardDto> getAll() {
        return repository.findAll().stream()
                .map(converter::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CardDto save(CardDto cardDto) {
        if (repository.existsByNumber(cardDto.getNumber())) {
            throw new AlreadyExistsException("Card with number = " + cardDto.getNumber() + " already exists");
        }
        Card card = repository.save(converter.convertToEntity(cardDto));
        return converter.convertToDto(card);
    }

    @Override
    @Transactional
    public CardDto update(CardDto cardDto) {
        if (!repository.existsById(cardDto.getId())) {
            throw new NotFoundException("Card with id = " + cardDto.getId() + " is not found");
        }
        Card card = repository.save(converter.convertToEntity(cardDto));
        return converter.convertToDto(card);
    }

    @Override
    @Transactional
    public void deleteById(UUID id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Card with id = " + id + " is not found");
        }
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public List<CardDto> getByCustomerId(UUID customerId) {
//        return repository.findAll().stream()
//                .filter(card -> card.getCustomers().)
//                .map(converter::convertToDto)
//                .collect(Collectors.toList());
        return null;
    }
}
