package com.vadim.sneakerstore.unit;

import com.vadim.sneakerstore.dto.converter.OrderConverter;
import com.vadim.sneakerstore.repository.OrderRepository;
import com.vadim.sneakerstore.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class OrderUnitTest {

    @InjectMocks
    private OrderServiceImpl service;

    @Mock
    private OrderRepository repository;

    @Mock
    private OrderConverter converter;
}
