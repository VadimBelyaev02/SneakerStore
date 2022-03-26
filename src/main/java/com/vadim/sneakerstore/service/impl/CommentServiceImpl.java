package com.vadim.sneakerstore.service.impl;

import com.vadim.sneakerstore.dto.CommentDto;
import com.vadim.sneakerstore.dto.converter.CommentConverter;
import com.vadim.sneakerstore.entity.Comment;
import com.vadim.sneakerstore.exception.AlreadyExistsException;
import com.vadim.sneakerstore.exception.NotFoundException;
import com.vadim.sneakerstore.repository.CommentRepository;
import com.vadim.sneakerstore.service.CommentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository repository;
    private final CommentConverter converter;

    public CommentServiceImpl(CommentRepository repository, CommentConverter converter) {
        this.repository = repository;
        this.converter = converter;
    }

    @Override
    @Transactional
    public List<CommentDto> getAll() {
        return repository.findAll().stream()
                .map(converter::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CommentDto getById(UUID id) {
        Comment comment = repository.findById(id).orElseThrow(() ->
                new NotFoundException("Comment with id=" + id + " is not found")
        );
        return converter.convertToDto(comment);
    }

    @Override
    @Transactional
    public CommentDto save(CommentDto commentDto) {
        if (repository.existsById(commentDto.getId())) {
            throw new AlreadyExistsException("Comment with id = " + commentDto.getId() + " already exists");
        }
        Comment comment = repository.save(converter.convertToEntity(commentDto));
        return converter.convertToDto(comment);
    }

    @Override
    @Transactional
    public CommentDto update(CommentDto commentDto) {
        if (Objects.isNull(commentDto.getId()) || !repository.existsById(commentDto.getId())) {
            throw new NotFoundException("Comment with id = " + commentDto.getId() + " is not found");
        }
        Comment comment = repository.save(converter.convertToEntity(commentDto));
        return converter.convertToDto(comment);
    }

    @Override
    @Transactional
    public void deleteById(UUID id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Comment with id=" + id + " is not found");
        }
        repository.deleteById(id);
    }
}
