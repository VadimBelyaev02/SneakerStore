package com.vadim.sneakerstore.controller;

import com.vadim.sneakerstore.dto.FavoriteDto;
import com.vadim.sneakerstore.service.FavoriteService;
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
@Tag(name = "Favorite Controller", description = "Favorite CRUD operations")
@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {

    private final FavoriteService service;

    public FavoriteController(FavoriteService service) {
        this.service = service;
    }

    @Operation(description = "Get favorite by customer and product ids")
    @ApiResponse(description = "Favorite product by product id and customer id is found", responseCode = "200")
    @GetMapping("/{productId}/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    public FavoriteDto getFavorite(@Parameter(description = "Id of a product that should be found in customer's cart")
                                   @PathVariable("productId") UUID productId,
                                   @Parameter(description = "Id of a customer's cart in whose product should be found")
                                   @PathVariable("customerId") UUID customerId) {
        return service.getById(customerId, productId);
    }

    @Operation(description = "Get all products from everyone's favorites")
    @ApiResponse(description = "All favorites of all customers are found", responseCode = "200")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<FavoriteDto> getAllFavorites() {
        return service.getAll();
    }

    @Operation(description = "Get all products from customer's favorites")
    @ApiResponse(description = "All customer's favorites are found", responseCode = "200")
    @GetMapping("/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    public List<FavoriteDto> getAllCustomerFavorite(@Parameter(description = "Customer whose favorites should be found")
                                                    @PathVariable("customerId") UUID customerId) {
        return service.getByCustomerId(customerId);
    }

    @Operation(description = "Add a new product in favorites")
    @ApiResponse(description = "Favorite product is added to customer's favorites", responseCode = "201")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FavoriteDto postCustomer(@Parameter(description = "Contains info about new someone's favorite product")
                                    @Valid @RequestBody FavoriteDto favoriteDto) {
        return service.save(favoriteDto);
    }

    @Operation(description = "Update existed product in favorites")
    @ApiResponse(description = "Favorite product is successfully updated", responseCode = "200")
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public FavoriteDto putCustomer(@Parameter(description = "Contains info about updated customer's favorite product")
                                   @Valid @RequestBody FavoriteDto favoriteDto) {
        return service.update(favoriteDto);
    }

    @Operation(description = "Delete favorite by id")
    @ApiResponse(description = "Favorite product is successfully deleted from customer's favorites", responseCode = "204")
    @DeleteMapping("/{productId}/{customerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@Parameter(description = "Id of deleted product from customer's favorites")
                               @PathVariable("productId") UUID productId,
                               @Parameter(description = "Id of a customers from whose favorite products should be deleted")
                               @PathVariable("customerId") UUID customerId) {
        service.deleteById(customerId, productId);
    }
}
