package com.vadim.sneakerstore.controller;

import com.vadim.sneakerstore.dto.FavoriteDto;
import com.vadim.sneakerstore.service.FavoriteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Favorite Controller", description = "Favorite CRUD operations")
@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {

    private final FavoriteService service;

    public FavoriteController(FavoriteService service) {
        this.service = service;
    }

    @Operation(description = "Get favorite by customer and product ids")
    @GetMapping("/{productId}/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    public FavoriteDto getFavorite(@PathVariable("productId") UUID productId,
                                   @PathVariable("customerId") UUID customerId) {
        return service.getById(customerId, productId);
    }

    @Operation(description = "Get all products from everyone's favorites")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<FavoriteDto> getAllFavorites() {
        return service.getAll();
    }

    @Operation(description = "Get all products from customer's favorites")
    @GetMapping("/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    public List<FavoriteDto> getAllCustomerFavorite(@PathVariable("customerId") UUID customerId) {
        return service.getByCustomerId(customerId);
    }

    @Operation(description = "Add a new product in favorites")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FavoriteDto postCustomer(@Valid @RequestBody FavoriteDto favoriteDto) {
        return service.save(favoriteDto);
    }

    @Operation(description = "Update existed product in favorites")
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public FavoriteDto putCustomer(@Valid @RequestBody FavoriteDto favoriteDto) {
        return service.update(favoriteDto);
    }

    @Operation(description = "Delete favorite by id")
    @DeleteMapping("/{productId}/{customerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable("productId") UUID productId,
                               @PathVariable("customerId") UUID customerId) {
        service.deleteById(customerId, productId);
    }
}
