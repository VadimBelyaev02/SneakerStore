package com.vadim.sneakerstore.controller;

import com.vadim.sneakerstore.dto.CommentDto;
import com.vadim.sneakerstore.entity.Comment;
import com.vadim.sneakerstore.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
@RequestMapping("/api")
public class CommentController {

    private final CommentService service;

    public CommentController(CommentService commentService) {
        this.service = commentService;
    }

    @Operation(description = "Get all comments")
    @ApiResponse(description = "All comments are found", responseCode = "200")
    @GetMapping("/comments")
    @ResponseStatus(HttpStatus.OK)
    public List<CommentDto> getAllComments() {
        return service.getAll();
    }

    @Operation(description = "Get a comment by its id")
    @ApiResponse(description = "Comment is found", responseCode = "200")
    @GetMapping("/comments/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CommentDto getComment(@Parameter(description = "Id of needed comment")
                                 @PathVariable("id") UUID id) {
        return service.getById(id);
    }

    @Operation(description = "Get all product's comments")
    @ApiResponse(description = "All product's comments are found", responseCode = "200")
    @GetMapping("/products/{productId}/comments")
    @ResponseStatus(HttpStatus.OK)
    public List<CommentDto> getAllProductComments(@Parameter(description = "Id of product whose comments should be found")
                                                  @PathVariable("productId") UUID productId) {
        return service.getAllByProductId(productId);
    }

    @Operation(description = "Add a new comment")
    @ApiResponse(description = "Comment is successfully added", responseCode = "201")
    @PostMapping("/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto postComment(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Object that will be saved")
                                  @Valid @RequestBody CommentDto commentDto) {
        return service.save(commentDto);
    }

    @Operation(description = "Update existed comment")
    @ApiResponse(description = "Comment is successfully updated", responseCode = "200")
    @PutMapping("/comments")
    @ResponseStatus(HttpStatus.OK)
    public CommentDto putComment(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Contains updated fields of existed comment")
                                 @Valid @RequestBody CommentDto commentDto) {
        return service.update(commentDto);
    }

    @Operation(description = "Delete a comment by its id")
    @ApiResponse(description = "Comment is successfully deleted", responseCode = "204")
    @DeleteMapping("/comments/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@Parameter(description = "Id of object that will be deleted")
                              @PathVariable("id") UUID id) {
        service.deleteById(id);
    }
}
