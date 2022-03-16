package com.vadim.sneakerstore.controller;

import com.vadim.sneakerstore.dto.PhotoDto;
import com.vadim.sneakerstore.service.PhotoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@Tag(name = "Photo Controller", description = "Photo CRUD operations")
@RestController
@RequestMapping("/api")
public class PhotoController {

    private final PhotoService service;

    public PhotoController(PhotoService service) {
        this.service = service;
    }

    @Operation(description = "Get photo by id")
    @GetMapping("/photos/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PhotoDto getById(@PathVariable("id") UUID id) {
        return service.getById(id);
    }

    @Operation(description = "Get all photos")
    @GetMapping("/photos")
    @ResponseStatus(HttpStatus.OK)
    public List<PhotoDto> getAllPhotos() {
        return service.getAll();
    }

    @Operation(description = "Add a new photo")
    @PostMapping("/photos")
    @ResponseStatus(HttpStatus.CREATED)
    public PhotoDto postPhotos(@Valid @RequestBody PhotoDto photoDto) {
        return service.save(photoDto);
    }

    @Operation(description = "Update existed photo")
    @PutMapping("/photos")
    @ResponseStatus(HttpStatus.OK)
    public PhotoDto putPhotos(@Valid @RequestBody PhotoDto photoDto) {
        return service.update(photoDto);
    }

    @Operation(description = "Delete photo by id")
    @DeleteMapping("/photos/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePhotos(@PathVariable("id") UUID id) {
        service.deleteById(id);
    }
}
