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
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        Customer customer = new Customer();
        CustomerDto customerDto = new CustomerDto();
        List<Customer> customers = Stream.of(customer, customer)
                .collect(Collectors.toList());
        List<CustomerDto> customerDtos = Stream.of(customerDto, customerDto)
                .collect(Collectors.toList());

        when(repository.findAll()).thenReturn(customers);
        when(converter.convertToDto(customer)).thenReturn(customerDto);

        assertEquals(service.getAll(), customerDtos);

        verify(repository, only()).findAll();
        verify(converter, times(2)).convertToDto(customer);
    }

    @Test
    public void shouldSaveNewCustomer() {
        String email = "vadimbelaev002@gmail.com";
        String phone = "123456789";
        Customer customer = new Customer();
        CustomerDto customerDto = new CustomerDto();
        customerDto.setPhone(phone);
        customerDto.setEmail(email);

        when(repository.existsByPhoneAndEmail(phone, email)).thenReturn(false);
        when(repository.existsByPhone(phone)).thenReturn(false);
        when(repository.existsByEmail(email)).thenReturn(false);
        when(converter.convertToDto(customer)).thenReturn(customerDto);
        when(converter.convertToEntity(customerDto)).thenReturn(customer);
        when(repository.save(customer)).thenReturn(customer);

        assertEquals(service.save(customerDto), customerDto);

        verify(repository, times(1)).existsByEmail(email);
        verify(repository, times(1)).existsByPhone(phone);
        verify(repository, times(1)).existsByPhoneAndEmail(phone, email);
        verify(repository, times(1)).save(customer);
        verify(converter, times(1)).convertToDto(customer);
        verify(converter, times(1)).convertToEntity(customerDto);
    }
    /*
            if (repository.existsByPhoneAndEmail(customerDto.getPhone(), customerDto.getEmail())) {
            throw new AlreadyExistsException("Customer with phone  = " + customerDto.getPhone()
                    + " and email = " + customerDto.getEmail() + " already exists");
        }
        if (repository.existsByPhone(customerDto.getPhone())) {
            throw new AlreadyExistsException("Customer with phone = " + customerDto.getPhone() + " already exists");
        }
        if (repository.existsByEmail(customerDto.getEmail())) {
            throw new AlreadyExistsException("Customer with email = " + customerDto.getEmail() + " already exists");
        }
        Customer customer = repository.save(converter.convertToEntity(customerDto));
        return converter.convertToDto(customer);
     */

    @Test
    public void shouldThrowsAlreadyExistsException() {
        String email = "vadimbelaev002@gmail.com";
        String phone = "123456789";
        Customer customer = new Customer();
        CustomerDto customerDto = new CustomerDto();
        customerDto.setPhone(phone);
        customerDto.setEmail(email);

        when(repository.existsByPhoneAndEmail(phone, email)).thenReturn(true);

        assertThrows(AlreadyExistsException.class, () -> service.save(customerDto));

        verify(repository, only()).existsByPhoneAndEmail(phone, email);
        verify(repository, never()).existsByEmail(email);
        verify(repository, never()).existsByPhone(phone);
        verify(repository, never()).save(customer);
        verify(converter, never()).convertToDto(customer);
        verify(converter, never()).convertToEntity(customerDto);
    }

    @Test
    public void shouldUpdateExistedCustomer() {
        String email = "vadimbelaev002@gmail.com";
        Customer customer = new Customer();
        CustomerDto customerDto = new CustomerDto();

        // when(repository.existsByEmail(email)).thenReturn(true);
        // when(repository.save())
    }

    @Test
    public void shouldThrowNotFoundExceptionWhileUpdating() {
        UUID id = UUID.randomUUID();
        CustomerDto customerDto = new CustomerDto();
        Customer customer = new Customer();
        customerDto.setId(id);

        when(repository.existsById(id)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> service.update(customerDto));

        verify(repository, only()).existsById(id);
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
