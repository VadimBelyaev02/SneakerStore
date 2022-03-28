package com.vadim.sneakerstore.unit;

import com.vadim.sneakerstore.dto.converter.ProductConverter;
import com.vadim.sneakerstore.repository.ProductRepository;
import com.vadim.sneakerstore.service.impl.ProductServiceImpl;
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

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class ProductServiceUnitTest {

    @InjectMocks
    private ProductServiceImpl service;

    @Mock
    private ProductRepository repository;

    @Mock
    private ProductConverter converter;

    @Test
    public void shouldReturnProductById() {

    }

    @Test
    public void shouldThrowsNotFoundException() {

    }

    public void shouldReturnSavedProduct() {}

}
