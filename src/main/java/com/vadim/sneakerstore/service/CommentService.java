package com.vadim.sneakerstore.service;

import com.vadim.sneakerstore.dto.CommentDto;

import java.util.List;
import java.util.UUID;

public interface CommentService {

    List<CommentDto> getAll();

    CommentDto getById(UUID id);

    CommentDto save(CommentDto commentDto);

    CommentDto update(CommentDto commentDto);

    void deleteById(UUID id);

    List<CommentDto> getAllByProductId(UUID productId);
}
