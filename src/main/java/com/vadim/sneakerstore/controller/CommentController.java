package com.vadim.sneakerstore.controller;

import com.vadim.sneakerstore.dto.CommentDto;
import com.vadim.sneakerstore.entity.Comment;
import com.vadim.sneakerstore.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/comment")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CommentDto> getAllComments() {
        return commentService.getAllComments();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CommentDto getComment(@PathVariable("id")UUID i) {
        return null;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto postComment(@Valid @RequestBody CommentDto commentDto) {
        return commentService.saveComment(commentDto);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public CommentDto putComment(@Valid @RequestBody CommentDto commentDto) {
        return commentService.updateComment(commentDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable("id") UUID id) {
        commentService.deleteComment(id);
    }
}
