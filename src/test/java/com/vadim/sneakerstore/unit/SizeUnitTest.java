package com.vadim.sneakerstore.unit;

import com.vadim.sneakerstore.dto.SizeDto;
import com.vadim.sneakerstore.dto.converter.SizeConverter;
import com.vadim.sneakerstore.entity.Size;
import com.vadim.sneakerstore.exception.AlreadyExistsException;
import com.vadim.sneakerstore.exception.NotFoundException;
import com.vadim.sneakerstore.repository.SizeRepository;
import com.vadim.sneakerstore.service.impl.SizeServiceImpl;
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
        sizeDto.setId(id);

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

    @Test
    public void shouldThrowsAlreadyExistsExceptionWhileSaving() {
        UUID id = UUID.randomUUID();
        SizeDto sizeDto = new SizeDto();
        sizeDto.setId(id);

        when(repository.existsById(id)).thenReturn(true);

        assertThrows(AlreadyExistsException.class, () -> service.save(sizeDto));

        verify(repository, only()).existsById(id);
        verify(converter, never()).convertToEntity(sizeDto);
        verify(converter, never()).convertToDto(new Size());
    }

    @Test
    public void shouldReturnUpdatedSizeDto() {
//        UUID id = UUID.randomUUID();
//        Size size = new Size();
//        SizeDto sizeDto = new SizeDto();
//
//        when(repository.existsById(id)).thenReturn(true);
//        when(converter.convertToEntity(sizeDto)).thenReturn(size);
//        when(converter.convertToDto(size)).thenReturn(sizeDto);
//
//
//        assertEquals(service.update(sizeDto), sizeDto);
//
//        verify(repository, )
    }

    @Test
    public void shouldThrowsNotFoundExceptionWhileUpdating() {
        UUID id = UUID.randomUUID();
        Size size = new Size();
        SizeDto sizeDto = new SizeDto();
        sizeDto.setId(id);

        when(repository.existsById(id)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> service.update(sizeDto));

        verify(repository, only()).existsById(id);
        verify(converter, never()).convertToDto(size);
        verify(converter, never()).convertToEntity(sizeDto);
    }

    @Test
    public void shouldThrowsNotFoundExceptionWhileDeleting() {
        UUID id = UUID.randomUUID();

        when(repository.existsById(id)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> service.deleteById(id));

        verify(repository, only()).existsById(id);
        verify(repository, never()).deleteById(id);
    }

    @Test
    public void shouldReturnListOfSizeDtosByProductId() {
        UUID productId = UUID.randomUUID();
        Size size = new Size();
        SizeDto sizeDto = new SizeDto();
        List<Size> sizes = Stream.of(size, size, size, size).collect(Collectors.toList());
        List<SizeDto> sizeDtos = Stream.of(sizeDto, sizeDto, sizeDto, sizeDto).collect(Collectors.toList());

        when(repository.findAllByProductId(productId)).thenReturn(sizes);
        when(converter.convertToDto(size)).thenReturn(sizeDto);

        assertEquals(service.getAllByProductId(productId), sizeDtos);

        verify(repository, only()).findAllByProductId(productId);
        verify(converter, times(4)).convertToDto(size);
    }
}
