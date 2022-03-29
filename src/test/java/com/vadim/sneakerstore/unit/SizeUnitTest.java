package com.vadim.sneakerstore.unit;

import com.vadim.sneakerstore.dto.SizeDto;
import com.vadim.sneakerstore.dto.converter.SizeConverter;
import com.vadim.sneakerstore.entity.Size;
import com.vadim.sneakerstore.repository.SizeRepository;
import com.vadim.sneakerstore.service.impl.SizeServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

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
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        UUID id = UUID.randomUUID();
        Size size = new Size();
        SizeDto sizeDto = new SizeDto();

        when(repository.findById(id)).thenReturn(Optional.of(size));
        when(converter.convertToDto(size)).thenReturn(sizeDto);

        assertEquals(service.getById(id), sizeDto);

        verify(repository, only()).findById(id);
        verify(converter, only()).convertToDto(size);
    }

    @Test
    public void shouldThrowsNotFoundException() {
        UUID id = UUID.randomUUID();

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.getById(id));

        verify(repository, only()).findById(id);
        verify(converter, never()).convertToDto(new Size());
    }

    @Test
    public void shouldReturnListOfSizeDtos() {
        Size size = new Size();
        SizeDto sizeDto = new SizeDto();
        List<Size> sizes = Stream.of(size, size, size).collect(Collectors.toList());
        List<SizeDto> sizeDtos = Stream.of(sizeDto, sizeDto, sizeDto).collect(Collectors.toList());

        when(repository.findAll()).thenReturn(sizes);
        when(converter.convertToDto(size)).thenReturn(sizeDto);

        assertEquals(service.getAll(), sizeDtos);

        verify(repository, only()).findAll();
        verify(converter, times(3)).convertToDto(size);
    }

    @Test
    public void shouldReturnEmptyListOfSizeDtos() {
        when(repository.findAll()).thenReturn(new ArrayList<>());

        assertEquals(service.getAll(), new ArrayList<>());

        verify(repository, only()).findAll();
        verify(converter, never()).convertToDto(new Size());
    }

    @Test
    public void shouldReturnSavedSizeDto() {
        UUID id = UUID.randomUUID();
        Size size = new Size();
        SizeDto sizeDto = new SizeDto();

        when(repository.existsById(id)).thenReturn(false);
        when(repository.save(size)).thenReturn(size);
        when(converter.convertToDto(size)).thenReturn(sizeDto);
        when(converter.convertToEntity(sizeDto)).thenReturn(size);

        assertEquals(service.save(sizeDto), sizeDto);

        verify(repository, times(1)).save(size);
        verify(repository, times(1)).existsById(id);
        verify(converter, times(1)).convertToDto(size);
        verify(converter, times(1)).convertToEntity(sizeDto);
    }

    /*
    public SizeDto save(SizeDto sizeDto) {
        if (repository.existsById(sizeDto.getId())) {
            throw new AlreadyExistsException("Size with id = " + sizeDto.getId() + " already exists");
        }
        Size size = repository.save(converter.convertToEntity(sizeDto));
        return converter.convertToDto(size);
    }

    public SizeDto update(SizeDto sizeDto) {
        if (!repository.existsById(sizeDto.getId())) {
            throw new NotFoundException("Size with id = " + sizeDto.getId() + " is not found");
        }
        Size size = repository.save(converter.convertToEntity(sizeDto));
        return converter.convertToDto(size);
    }

   public void deleteById(UUID id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Size with id = " + id + " is not found");
        }
        repository.deleteById(id);
    }

   public List<SizeDto> getAllByProductId(UUID productId) {
        return repository.findAllByProductId(productId).stream()
                .map(converter::convertToDto)
                .collect(Collectors.toList());
    }
     */
}
