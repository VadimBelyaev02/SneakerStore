package com.vadim.sneakerstore.unit;

import com.vadim.sneakerstore.dto.CardDto;
import com.vadim.sneakerstore.dto.converter.CardConverter;
import com.vadim.sneakerstore.entity.Card;
import com.vadim.sneakerstore.entity.Comment;
import com.vadim.sneakerstore.repository.CardRepository;
import com.vadim.sneakerstore.service.CardService;
import com.vadim.sneakerstore.service.impl.CardServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.vadim.sneakerstore.dto.CustomerDto;
import com.vadim.sneakerstore.dto.converter.CustomerConverter;
import com.vadim.sneakerstore.entity.Customer;
import com.vadim.sneakerstore.exception.AlreadyExistsException;
import com.vadim.sneakerstore.exception.NotFoundException;
import com.vadim.sneakerstore.repository.CustomerRepository;
import com.vadim.sneakerstore.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class CardUnitTest {

    @InjectMocks
    private CardServiceImpl service;

    @Mock
    private CardRepository repository;

    @Mock
    private CardConverter converter;

    @Test
    public void shouldReturnCardById() {
        UUID id = UUID.randomUUID();
        Card card = new Card();
        CardDto cardDto = new CardDto();

        when(repository.findById(id)).thenReturn(Optional.of(card));
        when(converter.convertToDto(card)).thenReturn(cardDto);

        assertEquals(service.getById(id), cardDto);

        verify(repository, only()).findById(id);
        verify(converter, only()).convertToDto(card);
    }

    @Test
    public void shouldThrowsNotFoundException() {
        UUID id = UUID.randomUUID();

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.getById(id));

        verify(repository, only()).findById(id);
        verify(converter, never()).convertToDto(new Card());
    }

    @Test
    public void shouldReturnAllCardDtos() {
        Card card = new Card();
        CardDto cardDto = new CardDto();
        List<Card> cards = Stream.of(card, card, card)
                .collect(Collectors.toList());
        List<CardDto> cardDtos = Stream.of(cardDto, cardDto, cardDto)
                .collect(Collectors.toList());

        when(repository.findAll()).thenReturn(cards);
        when(converter.convertToDto(card)).thenReturn(cardDto);

        assertEquals(service.getAll(), cardDtos);

        verify(repository, only()).findAll();
        verify(converter, times(3))
                .convertToDto(card);
    }

    @Test
    public void shouldReturnEmptyListOfCardDtos() {
        when(repository.findAll()).thenReturn(new ArrayList<>());

        assertEquals(service.getAll(), new ArrayList<>());

        verify(repository, only()).findAll();
        verify(converter, never()).convertToDto(new Card());
    }

    @Test
    public void shouldReturnSavedCardDto() {
        String number = "123456789";
        Card card = new Card();
        CardDto cardDto = new CardDto();
        cardDto.setNumber(number);

        when(repository.existsByNumber(number)).thenReturn(false);
        when(repository.save(card)).thenReturn(card);
        when(converter.convertToDto(card)).thenReturn(cardDto);
        when(converter.convertToEntity(cardDto)).thenReturn(card);

        assertEquals(service.save(cardDto), cardDto);

        verify(repository, times(1)).existsByNumber(number);
        verify(repository, times(1)).save(card);
        verify(converter, times(1)).convertToDto(card);
        verify(converter, times(1)).convertToEntity(cardDto);
    }

    @Test
    public void shouldThrowsAlreadyExistsException() {
        String number = "123456789";
        Card card = new Card();
        CardDto cardDto = new CardDto();
        cardDto.setNumber(number);

        when(repository.existsByNumber(number)).thenReturn(true);

        assertThrows(AlreadyExistsException.class, () -> service.save(cardDto));

        verify(repository, only()).existsByNumber(number);
        verify(repository, never()).save(card);
        verify(converter, never()).convertToDto(card);
        verify(converter, never()).convertToEntity(cardDto);
    }
}
