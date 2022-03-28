package com.vadim.sneakerstore.unit;

import com.vadim.sneakerstore.dto.converter.CommentConverter;
import com.vadim.sneakerstore.repository.CommentRepository;
import com.vadim.sneakerstore.service.impl.CommentServiceImpl;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.vadim.sneakerstore.exception.AlreadyExistsException;
import com.vadim.sneakerstore.exception.NotFoundException;
import com.vadim.sneakerstore.repository.CustomerRepository;
import com.vadim.sneakerstore.service.impl.CustomerServiceImpl;
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

@ExtendWith(MockitoExtension.class)
public class CommentUnitTest {

    @InjectMocks
    private CommentServiceImpl service;

    @Mock
    private CommentRepository repository;

    @Mock
    private CommentConverter converter;

    @Test
    public void shouldReturnCommentById() {

    }

    @Test
    public void shouldThrowNotFoundException() {

    }

    @Test
    public void shouldReturnAllComments() {

    }

    @Test
    public void shouldReturnEmptyList() {

    }

    @Test
    public void shouldReturnSavedComment() {

    }

    @Test
    public void shouldThrowAlreadyExistsException() {

    }

    @Test
    public void shouldReturnUpdatedComment() {

    }

    @Test
    public void shouldThrowNotFoundExceptionWhildUpdating() {

    }

    @Test
    public void shouldThrowNotFoundExceptingWhileDeleting() {

    }

    /*
    public List<CommentDto> getAll() {
        return repository.findAll().stream()
                .map(converter::convertToDto)
                .collect(Collectors.toList());
    }

   public CommentDto getById(UUID id) {
        Comment comment = repository.findById(id).orElseThrow(() ->
                new NotFoundException("Comment with id=" + id + " is not found")
        );
        return converter.convertToDto(comment);
    }

    public CommentDto save(CommentDto commentDto) {
        if (repository.existsById(commentDto.getId())) {
            throw new AlreadyExistsException("Comment with id = " + commentDto.getId() + " already exists");
        }
        Comment comment = repository.save(converter.convertToEntity(commentDto));
        return converter.convertToDto(comment);
    }

    public CommentDto update(CommentDto commentDto) {
        if (Objects.isNull(commentDto.getId()) || !repository.existsById(commentDto.getId())) {
            throw new NotFoundException("Comment with id = " + commentDto.getId() + " is not found");
        }
        Comment comment = repository.save(converter.convertToEntity(commentDto));
        return converter.convertToDto(comment);
    }

    public void deleteById(UUID id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Comment with id=" + id + " is not found");
        }
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public List<CommentDto> getAllByProductId(UUID productId) {
        return repository.findAllByProductId(productId).stream()
                .map(converter::convertToDto)
                .collect(Collectors.toList());
    }
     */
}
