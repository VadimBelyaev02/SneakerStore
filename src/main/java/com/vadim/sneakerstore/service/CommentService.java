package com.vadim.sneakerstore.service;

import com.vadim.sneakerstore.dto.CommentDto;

import java.util.List;
import java.util.UUID;

public interface CommentService {

    List<CommentDto> getAllComments();

    CommentDto getComment(UUID id);

    CommentDto saveComment(CommentDto commentDto);

    CommentDto updateComment(CommentDto commentDto);

    void deleteComment(UUID id);
}
