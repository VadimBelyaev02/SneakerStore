package com.vadim.sneakerstore.controller;

import com.vadim.sneakerstore.dto.CommentDto;
import com.vadim.sneakerstore.entity.Comment;
import com.vadim.sneakerstore.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Comment Controller", description = "Comment CRUD operations")
@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @Operation(description = "Get all comments")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CommentDto> getAllComments() {
        return commentService.getAll();
    }

    @Operation(description = "Get a comment by its id")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CommentDto getComment(@Parameter(description = "Id of needed comment")
                                 @PathVariable("id") UUID id) {
        return commentService.getById(id);
    }

    @Operation(description = "Add a new comment")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto postComment(@Parameter(description = "Object that will be saved")
                                  @Valid @RequestBody CommentDto commentDto) {
        return commentService.save(commentDto);
    }

    @Operation(description = "Update existed comment")
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public CommentDto putComment(@Parameter(description = "Contains updated fields of existed comment")
                                 @Valid @RequestBody CommentDto commentDto) {
        return commentService.update(commentDto);
    }

    @Operation(description = "Delete a comment by its id")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@Parameter(description = "Id of object that will be deleted")
                              @PathVariable("id") UUID id) {
        commentService.deleteById(id);
    }
}
