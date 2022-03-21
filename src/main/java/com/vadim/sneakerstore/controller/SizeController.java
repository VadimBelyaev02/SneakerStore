package com.vadim.sneakerstore.controller;

import com.vadim.sneakerstore.dto.SizeDto;
import com.vadim.sneakerstore.service.SizeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "https://sneakerstore.vercel.app", maxAge = 3600)
@Tag(name = "Size Controller", description = "Size CRUD operations")
@RestController
@RequestMapping("/api")
public class SizeController {

    private final SizeService service;

    public SizeController(SizeService service) {
        this.service = service;
    }

    @Operation(description = "Get size by id")
    @GetMapping("/sizes/{id}")
    @ResponseStatus(HttpStatus.OK)
    public SizeDto getSize(@PathVariable("id") UUID id) {
        return service.getById(id);
    }

    @Operation(description = "Get all sizes")
    @GetMapping("/sizes")
    @ResponseStatus(HttpStatus.OK)
    public List<SizeDto> getAllSizes() {
        return service.getAll();
    }

    @Operation(description = "Add a new size")
    @PostMapping("/sizes")
    @ResponseStatus(HttpStatus.CREATED)
    public SizeDto postSize(@Valid @RequestBody SizeDto sizeDto) {
        return service.save(sizeDto);
    }

    @Operation(description = "Update existed size")
    @PutMapping("/sizes")
    @ResponseStatus(HttpStatus.OK)
    public SizeDto putSize(@Valid @RequestBody SizeDto sizeDto) {
        return service.update(sizeDto);
    }

    @Operation(description = "Delete size by id")
    @DeleteMapping("/sizes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSize(@PathVariable("id") UUID id) {
        service.deleteById(id);
    }
}
