package com.vadim.sneakerstore.unit;

import com.vadim.sneakerstore.dto.converter.SizeConverter;
import com.vadim.sneakerstore.repository.SizeRepository;
import com.vadim.sneakerstore.service.impl.SizeServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

@ExtendWith(MockitoExtension.class)
public class SizeUnitTest {

    @InjectMocks
    private SizeServiceImpl service;

    @Mock
    private SizeRepository repository;

    @Mock
    private SizeConverter converter;

    @Test
    public void shouldReturnSizeDtoById() {

    }

    @Test
    public void shouldThrowsNotFoundException() {

    }
}
