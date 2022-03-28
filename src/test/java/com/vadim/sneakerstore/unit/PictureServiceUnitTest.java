package com.vadim.sneakerstore.unit;

import com.vadim.sneakerstore.dto.CustomerDto;
import com.vadim.sneakerstore.dto.PictureDto;
import com.vadim.sneakerstore.dto.converter.CustomerConverter;
import com.vadim.sneakerstore.dto.converter.PictureConverter;
import com.vadim.sneakerstore.entity.Customer;
import com.vadim.sneakerstore.entity.Picture;
import com.vadim.sneakerstore.exception.AlreadyExistsException;
import com.vadim.sneakerstore.exception.NotFoundException;
import com.vadim.sneakerstore.repository.CustomerRepository;
import com.vadim.sneakerstore.repository.PictureRepository;
import com.vadim.sneakerstore.service.impl.CustomerServiceImpl;
import com.vadim.sneakerstore.service.impl.PictureServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        UUID id = UUID.randomUUID();
        Picture picture = new Picture();
        PictureDto pictureDto = new PictureDto();

        when(repository.findById(id)).thenReturn(Optional.of(picture));
    }

    @Test
    public void shouldThrowsNotFoundException() {

    }

    @Test
    public void shouldReturnListOfPicturesDto() {

    }

    @Test
    public void shouldReturnEmptyList() {

    }

    @Test
    public void shouldReturnSavedPictureDto() {

    }

    @Test
    public void shouldThrowsAlreadyExistsExceptionWhileSaving() {

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
