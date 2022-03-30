package com.vadim.sneakerstore.unit;

import com.vadim.sneakerstore.dto.converter.ProductConverter;
import com.vadim.sneakerstore.repository.ProductRepository;
import com.vadim.sneakerstore.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
