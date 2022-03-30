package com.vadim.sneakerstore.unit;

import com.vadim.sneakerstore.dto.AddressDto;
import com.vadim.sneakerstore.dto.converter.AddressConverter;
import com.vadim.sneakerstore.entity.Address;
import com.vadim.sneakerstore.entity.Card;
import com.vadim.sneakerstore.exception.AlreadyExistsException;
import com.vadim.sneakerstore.exception.NotFoundException;
import com.vadim.sneakerstore.repository.AddressRepository;
import com.vadim.sneakerstore.service.impl.AddressServiceImpl;
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
        Address address = new Address();
        AddressDto addressDto = new AddressDto();
        UUID id = UUID.randomUUID();
        List<Address> addresses = Stream.of(address, address, address)
                .collect(Collectors.toList());
        List<AddressDto> addressDtos = Stream.of(addressDto, addressDto, addressDto)
                .collect(Collectors.toList());

        address.setId(id);
        addressDto.setId(id);

        when(repository.findAll()).thenReturn(addresses);
        when(converter.convertToDto(address)).thenReturn(addressDto);

        assertEquals(service.getAll(), addressDtos);

        verify(repository, only()).findAll();
        verify(converter, times(3)).convertToDto(address);
    }

    @Test
    public void shouldReturnEmptyListOfAddressDtos() {
        when(repository.findAll()).thenReturn(new ArrayList<>());

        assertEquals(service.getAll(), new ArrayList<>());

        verify(repository, only()).findAll();
        verify(converter, never()).convertToDto(new Address());
    }

    @Test
    public void shouldReturnSavedAddress() {
        UUID id = UUID.randomUUID();
        String everyField = "city";
        Address address = new Address();
        AddressDto addressDto = new AddressDto();
        addressDto.setId(id);
        addressDto.setCity(everyField);
        addressDto.setCountry(everyField);
        addressDto.setStreet(everyField);

        when(repository.existsByCityAndCountryAndStreet(everyField, everyField, everyField))
                .thenReturn(false);
        when(repository.save(address)).thenReturn(address);
        when(converter.convertToDto(address)).thenReturn(addressDto);
        when(converter.convertToEntity(addressDto)).thenReturn(address);

        assertEquals(service.save(addressDto), addressDto);

        verify(repository, times(1)).save(address);
        verify(repository, times(1)).existsByCityAndCountryAndStreet(everyField, everyField, everyField);
        verify(converter, times(1)).convertToDto(address);
        verify(converter, times(1)).convertToEntity(addressDto);
    }

    @Test
    public void shouldThrowAlreadyExistsExceptionWhileSaving() {
        UUID id = UUID.randomUUID();
        String everyField = "city";
        Address address = new Address();
        AddressDto addressDto = new AddressDto();
        addressDto.setId(id);
        addressDto.setCity(everyField);
        addressDto.setCountry(everyField);
        addressDto.setStreet(everyField);

        when(repository.existsByCityAndCountryAndStreet(everyField, everyField, everyField))
                .thenReturn(true);

        assertThrows(AlreadyExistsException.class, () -> service.save(addressDto));

        verify(repository, only()).existsByCityAndCountryAndStreet(everyField, everyField, everyField);
        verify(converter, never()).convertToDto(address);
        verify(converter, never()).convertToEntity(addressDto);
        verify(repository, never()).save(address);
    }

    @Test
    public void shouldThrowNotFoundExceptionWhileUpdating() {
        UUID id = UUID.randomUUID();
        Address address = new Address();
        AddressDto addressDto = new AddressDto();
        addressDto.setId(id);

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

    @Test
    public void shouldReturnAllAddressesDtosByCustomerId() {
        UUID customerId = UUID.randomUUID();
        Address address = new Address();
        AddressDto addressDto = new AddressDto();
        List<Address> addresses = Stream.of(address, address, address, address)
                .collect(Collectors.toList());
        List<AddressDto> addressDtos = Stream.of(addressDto, addressDto, addressDto, addressDto)
                .collect(Collectors.toList());

        when(repository.findAllByCustomerId(customerId)).thenReturn(addresses);
        when(converter.convertToDto(address)).thenReturn(addressDto);

        assertEquals(service.getAllByCustomerId(customerId), addressDtos);

        verify(repository, only()).findAllByCustomerId(customerId);
        verify(converter, times(4)).convertToDto(address);
    }

}
