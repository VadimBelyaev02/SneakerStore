package com.vadim.sneakerstore.unit;

import com.vadim.sneakerstore.dto.PictureDto;
import com.vadim.sneakerstore.dto.converter.PictureConverter;
import com.vadim.sneakerstore.entity.Picture;
import com.vadim.sneakerstore.exception.AlreadyExistsException;
import com.vadim.sneakerstore.exception.NotFoundException;
import com.vadim.sneakerstore.repository.PictureRepository;
import com.vadim.sneakerstore.service.impl.PictureServiceImpl;
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
public class PictureServiceUnitTest {

    @InjectMocks
    private PictureServiceImpl service;

    @Mock
    private PictureRepository repository;

    @Mock
    private PictureConverter converter;

    @Test
    public void shouldReturnPictureById() {
        String id = UUID.randomUUID().toString();
        Picture picture = new Picture();
        PictureDto pictureDto = new PictureDto();
        pictureDto.setLink(id);

        when(repository.findById(id)).thenReturn(Optional.of(picture));
        when(converter.convertToDto(picture)).thenReturn(pictureDto);

        assertEquals(service.getById(id), pictureDto);

        verify(repository, only()).findById(id);
        verify(converter, only()).convertToDto(picture);
    }

    @Test
    public void shouldThrowsNotFoundException() {
        String id = UUID.randomUUID().toString();
        Picture picture = new Picture();

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.getById(id));

        verify(repository, only()).findById(id);
        verify(converter, never()).convertToDto(picture);
    }

    @Test
    public void shouldReturnListOfPicturesDto() {
        Picture picture = new Picture();
        PictureDto pictureDto = new PictureDto();
        List<PictureDto> pictureDtos = Stream.of(pictureDto, pictureDto)
                .collect(Collectors.toList());
        List<Picture> pictures = Stream.of(picture, picture)
                .collect(Collectors.toList());

        when(repository.findAll()).thenReturn(pictures);
        when(converter.convertToDto(picture)).thenReturn(pictureDto);

        assertEquals(service.getAll(), pictureDtos);

        verify(repository, only()).findAll();
        verify(converter, times(2)).convertToDto(picture);
    }

    @Test
    public void shouldReturnEmptyListOfPictureDtos() {
        List<PictureDto> pictureDtos = new ArrayList<>();

        when(repository.findAll()).thenReturn(new ArrayList<>());

        assertEquals(service.getAll(), pictureDtos);

        verify(repository, only()).findAll();
        verify(converter, never()).convertToDto(new Picture());
    }

    @Test
    public void shouldReturnSavedPictureDto() {
        String id = UUID.randomUUID().toString();
        Picture picture = new Picture();
        PictureDto pictureDto = new PictureDto();
        pictureDto.setLink(id);

        when(repository.existsById(id)).thenReturn(false);
        when(repository.save(picture)).thenReturn(picture);
        when(converter.convertToDto(picture)).thenReturn(pictureDto);
        when(converter.convertToEntity(pictureDto)).thenReturn(picture);

        assertEquals(service.save(pictureDto), pictureDto);

        verify(repository, times(1)).existsById(id);
        verify(repository, times(1)).save(picture);
        verify(converter, times(1)).convertToDto(picture);
        verify(converter, times(1)).convertToEntity(pictureDto);
    }

    @Test
    public void shouldThrowsAlreadyExistsExceptionWhileSaving() {
        String id = UUID.randomUUID().toString();
        Picture picture = new Picture();
        PictureDto pictureDto = new PictureDto();
        pictureDto.setLink(id);

        when(repository.existsById(id)).thenReturn(true);

        assertThrows(AlreadyExistsException.class, () -> service.save(pictureDto));

        verify(repository, only()).existsById(id);
        verify(repository, never()).save(picture);
        verify(converter, never()).convertToDto(picture);
        verify(converter, never()).convertToEntity(pictureDto);
    }

    @Test
    public void shouldThrowsNotFoundExceptionWhileUpdating() {

    }

    @Test
    public void shouldReturnUpdatedPictureDto() {

    }

    @Test
    public void shouldThrowsNotFoundExceptionWhileDeleting() {

    }
}
