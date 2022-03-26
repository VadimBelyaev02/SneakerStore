package com.vadim.sneakerstore.unit;

import com.vadim.sneakerstore.dto.CustomerDto;
import com.vadim.sneakerstore.dto.converter.CustomerConverter;
import com.vadim.sneakerstore.entity.Customer;
import com.vadim.sneakerstore.exception.NotFoundException;
import com.vadim.sneakerstore.repository.CustomerRepository;
import com.vadim.sneakerstore.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class CustomerServiceUnitTest {

    @Autowired
    private CustomerServiceImpl service;

    @Autowired
    private CustomerRepository repository;

    @Autowired
    private CustomerConverter converter;

    @Test
    public void shouldReturnCustomerById() {
        UUID id = UUID.randomUUID();
        Customer customer = new Customer();
        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(id);
        customer.setId(id);

        when(repository.findById(id)).thenReturn(Optional.of(customer));
        when(converter.convertToDto(customer)).thenReturn(customerDto);

        assertEquals(service.getCustomer(id), customerDto);

        verify(repository, only()).findById(id);
        verify(converter, only()).convertToDto(customer);
    }

    @Test
    public void shouldThrowsNotFoundException() {
        UUID id = UUID.randomUUID();
        Customer customer = new Customer();

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.getCustomer(id));
    }
}
