package com.vadim.sneakerstore.controller;

import com.vadim.sneakerstore.dto.SizeDto;
import com.vadim.sneakerstore.service.SizeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Size Controller", description = "Size CRUD operations")
@RestController
@RequestMapping("/api")
public class SizeController {

    private final SizeService service;

    public SizeController(SizeService service) {
        this.service = service;
    }

    @Operation(description = "Get size by id")
    @ApiResponse(description = "Size is found", responseCode = "200")
    @GetMapping("/sizes/{id}")
    @ResponseStatus(HttpStatus.OK)
    public SizeDto getSize(@Parameter(description = "Id of needed size")
                           @PathVariable("id") UUID id) {
        return service.getById(id);
    }

    @Operation(description = "Get all sizes")
    @ApiResponse(description = "All sizes are found", responseCode = "200")
    @GetMapping("/sizes")
    @ResponseStatus(HttpStatus.OK)
    public List<SizeDto> getAllSizes() {
        return service.getAll();
    }

    @Operation(description = "Get all product's sizes")
    @ApiResponse(description = "All product's sizes are found", responseCode = "200")
    @GetMapping("/products/{productId}/sizes")
    @ResponseStatus(HttpStatus.OK)
    public List<SizeDto> getAllProductSizes(@Parameter(description = "Id of a product whose sizes should be found")
                                            @PathVariable("productId") UUID productId) {
        return service.getAllByProductId(productId);
    }

    @Operation(description = "Add a new size")
    @ApiResponse(description = "Sizes is added", responseCode = "201")
    @PostMapping("/sizes")
    @ResponseStatus(HttpStatus.CREATED)
    public SizeDto postSize(@Parameter(description = "Contains info about a new size")
                            @Valid @RequestBody SizeDto sizeDto) {
        return service.save(sizeDto);
    }

    @Operation(description = "Update existed size")
    @ApiResponse(description = "Size is updated", responseCode = "200")
    @PutMapping("/sizes")
    @ResponseStatus(HttpStatus.OK)
    public SizeDto putSize(@Parameter(description = "Contains info about new size's fields")
                           @Valid @RequestBody SizeDto sizeDto) {
        return service.update(sizeDto);
    }

    @Operation(description = "Delete size by id")
    @ApiResponse(description = "Size is deleted", responseCode = "204")
    @DeleteMapping("/sizes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSize(@Parameter(description = "Id of deleted size")
                           @PathVariable("id") UUID id) {
        service.deleteById(id);
    }
}
