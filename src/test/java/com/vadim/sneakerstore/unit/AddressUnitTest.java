package com.vadim.sneakerstore.unit;

import com.vadim.sneakerstore.dto.converter.AddressConverter;
import com.vadim.sneakerstore.repository.AddressRepository;
import com.vadim.sneakerstore.service.impl.AddressServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class AddressUnitTest {

    @InjectMocks
    private AddressServiceImpl service;

    @Mock
    private AddressRepository repository;

    @Mock
    private AddressConverter converter;

    @Test
    public void shouldReturnAddressById() {

    }

    @Test
    public void shouldThrowNotFoundException() {

    }

    @Test
    public void shouldReturnAllAddresses() {

    }

    @Test
    public void shouldReturnEmptyList() {

    }

    @Test
    public void shouldReturnSavedAddress() {

    }

    @Test
    public void shouldThrowAlreadyExistsException() {

    }

    @Test
    public void shouldThrowNotFoundExceptionWhileUpdating() {

    }

    @Test
    public void shouldReturnUpdatedAddress() {

    }

    @Test
    public void shouldThrowNotFoundExceptionWhileDeleting() {

    }

}
