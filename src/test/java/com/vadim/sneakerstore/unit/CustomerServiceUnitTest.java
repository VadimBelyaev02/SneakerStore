package com.vadim.sneakerstore.unit;

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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceUnitTest {

    @InjectMocks
    private CustomerServiceImpl service;

    @Mock
    private CustomerRepository repository;

    @Mock
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

        assertEquals(service.getById(id), customerDto);

        verify(repository, only()).findById(id);
        verify(converter, only()).convertToDto(customer);
    }

    @Test
    public void shouldThrowsNotFoundException() {
        UUID id = UUID.randomUUID();

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.getById(id));

        verify(repository, only()).findById(id);
        verify(converter, never()).convertToDto(new Customer());
    }

    @Test
    public void shouldReturnAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        List<CustomerDto> customerDtos = new ArrayList<>();
        Customer customer = new Customer();
        CustomerDto customerDto = new CustomerDto();
        customers.add(new Customer());
        customers.add(new Customer());
        customers.add(new Customer());

        customerDtos.add(new CustomerDto());
        customerDtos.add(new CustomerDto());
        customerDtos.add(new CustomerDto());

        when(repository.findAll()).thenReturn(customers);
        when(converter.convertToDto(new Customer())).thenReturn(new CustomerDto());

        assertEquals(service.getAll(), customerDtos);

        verify(repository, only()).findAll();
        verify(converter, times(3)).convertToDto(new Customer());
    }

    @Test
    public void shouldSaveNewCustomer() {
        String email = "vadimbelaev002@gmail.com";
        Customer customer = new Customer();
        CustomerDto customerDto = new CustomerDto();

        when(repository.existsByEmail(email)).thenReturn(false);
        when(converter.convertToDto(customer)).thenReturn(customerDto);
        when(converter.convertToEntity(customerDto)).thenReturn(customer);
        when(repository.save(customer)).thenReturn(customer);

        assertEquals(service.save(customerDto), customerDto);
    }

    @Test
    public void shouldThrowsAlreadyExistsException() {
        String email = "vadimbelaev002@gmail.com";
        Customer customer = new Customer();
        CustomerDto customerDto = new CustomerDto();

        when(repository.existsByEmail(email)).thenReturn(true);

        assertThrows(AlreadyExistsException.class, () -> service.save(customerDto));

        verify(repository, only()).existsByEmail(email);
        verify(repository, never()).save(customer);
        verify(converter, never()).convertToDto(customer);
        verify(converter, never()).convertToEntity(customerDto);
    }

    @Test
    public void shouldUpdateExistedCustomer() {
        String email = "vadimbelaev002@gmail.com";
        Customer customer = new Customer();
        CustomerDto customerDto = new CustomerDto();

        when(repository.existsByEmail(email)).thenReturn(true);
       // when(repository.save())
    }

    @Test
    public void shouldThrowNotFoundExceptionWhileUpdating() {
        String email = "vadimbelaev002@gmail.com";
        CustomerDto customerDto = new CustomerDto();
        Customer customer = new Customer();

        when(repository.existsByEmail(email)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> service.update(customerDto));

        verify(repository, only()).existsByEmail(email);
        verify(repository, never()).save(customer);
        verify(converter, never()).convertToEntity(customerDto);
        verify(converter, never()).convertToDto(customer);
    }

    @Test
    public void shouldThrowNotFoundExceptionWhileDeleting() {
        UUID id = UUID.randomUUID();

        when(repository.existsById(id)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> service.deleteById(id));

        verify(repository, only()).existsById(id);
        verify(repository, never()).deleteById(id);
    }
}
