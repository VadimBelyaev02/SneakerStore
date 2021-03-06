package com.vadim.sneakerstore.controller;

import com.vadim.sneakerstore.dto.PictureDto;
import com.vadim.sneakerstore.service.PictureService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Picture Controller", description = "Picture CRUD operations")
@RestController
@RequestMapping("/api/pictures")
public class PictureController {

    private final PictureService service;

    public PictureController(PictureService service) {
        this.service = service;
    }

    @Operation(description = "Get picture by id")
    @ApiResponse(description = "Picture is found", responseCode = "200")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PictureDto getById(@Parameter(description = "Id of needed picture")
                              @PathVariable("id") String id) {
        return service.getById(id);
    }

    @Operation(description = "Get all pictures")
    @ApiResponse(description = "All pictures are found", responseCode = "200")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PictureDto> getAllPhotos() {
        return service.getAll();
    }

    @Operation(description = "Add a new picture")
    @ApiResponse(description = "Picture is added", responseCode = "201")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PictureDto postPhotos(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Contains info about a new picture")
                                 @Valid @RequestBody PictureDto pictureDto) {
        return service.save(pictureDto);
    }

    @Operation(description = "Update existed picture")
    @ApiResponse(description = "Picture is successfully updated", responseCode = "200")
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public PictureDto putPhotos(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Contains info about new picture's info")
                                @Valid @RequestBody PictureDto pictureDto) {
        return service.update(pictureDto);
    }

    @Operation(description = "Delete picture by id")
    @ApiResponse(description = "Picture is successfully deleted", responseCode = "204")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePhotos(@Parameter(description = "Id of deleted picture")
                             @PathVariable("id") String id) {
        service.deleteById(id);
    }
}
