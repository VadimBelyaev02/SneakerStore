package com.vadim.sneakerstore.unit;

import com.vadim.sneakerstore.dto.CommentDto;
import com.vadim.sneakerstore.dto.SizeDto;
import com.vadim.sneakerstore.dto.converter.CommentConverter;
import com.vadim.sneakerstore.entity.Comment;
import com.vadim.sneakerstore.entity.Size;
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
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        UUID id = UUID.randomUUID();
        Comment comment = new Comment();
        CommentDto commentDto = new CommentDto();

        when(repository.findById(id)).thenReturn(Optional.of(comment));
        when(converter.convertToDto(comment)).thenReturn(commentDto);

        assertEquals(service.getById(id), commentDto);

        verify(repository, only()).findById(id);
        verify(converter, only()).convertToDto(comment);
    }

    @Test
    public void shouldThrowNotFoundException() {
        UUID id = UUID.randomUUID();

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.getById(id));

        verify(repository, only()).findById(id);
        verify(converter, never()).convertToDto(new Comment());
    }

    @Test
    public void shouldReturnListOfCommentDtos() {
        Comment comment = new Comment();
        CommentDto commentDto = new CommentDto();
        List<Comment> comments = Stream.of(comment, comment, comment)
                .collect(Collectors.toList());
        List<CommentDto> commentDtos = Stream.of(commentDto, commentDto, commentDto)
                .collect(Collectors.toList());

        when(repository.findAll()).thenReturn(comments);
        when(converter.convertToDto(comment)).thenReturn(commentDto);

        assertEquals(service.getAll(), commentDtos);

        verify(repository, only()).findAll();
        verify(converter, times(3)).convertToDto(comment);
    }

    @Test
    public void shouldReturnEmptyList() {
        when(repository.findAll()).thenReturn(new ArrayList<>());

        assertEquals(service.getAll(), new ArrayList<>());

        verify(repository, only()).findAll();
        verify(converter, never()).convertToDto(new Comment());
    }

    @Test
    public void shouldReturnSavedComment() {
        UUID id = UUID.randomUUID();
        Comment comment = new Comment();
        CommentDto commentDto = new CommentDto();
        commentDto.setId(id);

        when(repository.existsById(id)).thenReturn(false);
        when(repository.save(comment)).thenReturn(comment);
        when(converter.convertToDto(comment)).thenReturn(commentDto);
        when(converter.convertToEntity(commentDto)).thenReturn(comment);

        assertEquals(service.save(commentDto), commentDto);

        verify(repository, times(1)).save(comment);
        verify(repository, times(1)).existsById(id);
        verify(converter, times(1)).convertToDto(comment);
        verify(converter, times(1)).convertToEntity(commentDto);
    }

    @Test
    public void shouldThrowAlreadyExistsExceptionWhileSaving() {
        UUID id = UUID.randomUUID();
        CommentDto commentDto = new CommentDto();
        commentDto.setId(id);

        when(repository.existsById(id)).thenReturn(true);

        assertThrows(AlreadyExistsException.class, () -> service.save(commentDto));

        verify(repository, only()).existsById(id);
        verify(converter, never()).convertToEntity(commentDto);
        verify(converter, never()).convertToDto(new Comment());
    }

    @Test
    public void shouldReturnUpdatedComment() {

    }

    @Test
    public void shouldThrowNotFoundExceptionWhileUpdating() {

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
