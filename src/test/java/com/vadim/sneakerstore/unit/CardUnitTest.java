package com.vadim.sneakerstore.unit;

import com.vadim.sneakerstore.dto.converter.CardConverter;
import com.vadim.sneakerstore.repository.CardRepository;
import com.vadim.sneakerstore.service.CardService;
import com.vadim.sneakerstore.service.impl.CardServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
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
    }

    @Test
    public void shouldThrowsNotFoundException() {
        UUID id = UUID.randomUUID();

    }

    @Test
    public void shouldReturnAllCards() {

    }
}
