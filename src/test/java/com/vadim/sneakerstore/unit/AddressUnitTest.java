package com.vadim.sneakerstore.unit;

import com.vadim.sneakerstore.dto.AddressDto;
import com.vadim.sneakerstore.dto.converter.AddressConverter;
import com.vadim.sneakerstore.entity.Address;
import com.vadim.sneakerstore.exception.AlreadyExistsException;
import com.vadim.sneakerstore.exception.NotFoundException;
import com.vadim.sneakerstore.repository.AddressRepository;
import com.vadim.sneakerstore.service.impl.AddressServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

import java.util.UUID;

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
        UUID id = UUID.randomUUID();
        Address address = new Address();
        AddressDto addressDto = new AddressDto();

        when(repository.findById(id)).thenReturn(Optional.of(address));
        when(converter.convertToDto(address))
                .thenReturn(addressDto);

        assertEquals(service.getById(id), addressDto);

        verify(repository, only()).findById(id);
        verify(converter, only()).convertToDto(address);
    }

    @Test
    public void shouldThrowNotFoundException() {
        UUID id = UUID.randomUUID();

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.getById(id));

        verify(repository, only()).findById(id);
        verify(converter, never()).convertToDto(new Address());
    }

    @Test
    public void shouldReturnAllAddressDtos() {
        UUID id = UUID.randomUUID();
        List<Address> addresses = new ArrayList<>();
        List<AddressDto> addressDtos = new ArrayList<>();
        Address address = new Address();
        AddressDto addressDto = new AddressDto();
        address.setId(id);
        addressDto.setId(id);

        addresses.add(address);
        addresses.add(address);
        addresses.add(address);

        addressDtos.add(addressDto);
        addressDtos.add(addressDto);
        addressDtos.add(addressDto);

        when(repository.findAll()).thenReturn(addresses);
        when(converter.convertToDto(address)).thenReturn(addressDto);

        assertEquals(service.getAll(), addressDtos);

        verify(repository, only()).findAll();
        verify(converter, times(3)).convertToDto(address);
    }

    @Test
    public void shouldReturnEmptyList() {

    }

    @Test
    public void shouldReturnSavedAddress() {

    }

    @Test
    public void shouldThrowAlreadyExistsExceptionWhileSaving() {
        UUID id = UUID.randomUUID();
        Address address = new Address();
        AddressDto addressDto = new AddressDto();

        when(repository.existsById(id)).thenReturn(true);

        assertThrows(AlreadyExistsException.class, () -> service.save(addressDto));

        verify(repository, only()).existsById(id);
        verify(converter, never()).convertToDto(address);
        verify(converter, never()).convertToEntity(addressDto);
        verify(repository, never()).save(address);
    }

    @Test
    public void shouldThrowNotFoundExceptionWhileUpdating() {
        UUID id = UUID.randomUUID();
        Address address = new Address();
        AddressDto addressDto = new AddressDto();

        when(repository.existsById(id)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> service.update(addressDto));

        verify(repository, never()).save(address);
        verify(repository, only()).existsById(id);
        verify(converter, never()).convertToDto(address);
        verify(converter, never()).convertToEntity(addressDto);
    }

    @Test
    public void shouldReturnUpdatedAddress() {

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
