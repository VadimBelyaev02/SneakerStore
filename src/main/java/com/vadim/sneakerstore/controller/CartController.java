package com.vadim.sneakerstore.controller;

import com.vadim.sneakerstore.dto.CartDto;
import com.vadim.sneakerstore.service.CartService;
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
@Tag(name = "Cart Controller", description = "Cart CRUD operations")
@RestController
@RequestMapping("/api/carts")
public class CartController {

    private final CartService service;

    public CartController(CartService service) {
        this.service = service;
    }

    @Operation(description = "Get product from customer's cart by composite id")
    @ApiResponse(description = "Product by id from customer's cart are found", responseCode = "200")
    @GetMapping("/{customerId}/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public CartDto getCart(@Parameter(description = "Id of customer in whose cart this product")
                           @PathVariable("customerId") UUID customerId,
                           @Parameter(description = "Id of a product from customer's cart")
                           @PathVariable("productId") UUID productId) {
        return service.getById(customerId, productId);
    }

    @Operation(description = "Get all products from customer's cart by his id")
    @ApiResponse(description = "All products from customer's cart are found", responseCode = "200")
    @GetMapping("/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    public List<CartDto> getByCustomerId(@Parameter(description = "Id of needed customer's cart")
                                         @PathVariable("customerId") UUID customerId) {
        return service.getByCustomerId(customerId);
    }

    @Operation(description = "Get all products from all carts")
    @ApiResponse(description = "All carts are found", responseCode = "200")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CartDto> getAllCarts() {
        return service.getAll();
    }

    @Operation(description = "Save a new product in the cart")
    @ApiResponse(description = "A new product is added in cart", responseCode = "201")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CartDto postCarts(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Contains info about new cart")
                             @Valid @RequestBody CartDto cartDto) {
        return service.save(cartDto);
    }

    @Operation(description = "Updated existed product in the cart")
    @ApiResponse(description = "Product in cart is updated", responseCode = "200")
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public CartDto putCarts(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Contains info about new field of customer's cart")
                            @Valid @RequestBody CartDto cartDto) {
        return service.update(cartDto);
    }

    @Operation(description = "Delete existed product from the cart")
    @ApiResponse(description = "Product is deleted from cart", responseCode = "204")
    @DeleteMapping("/{customerId}/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCarts(@Parameter(description = "Id of a customers from whose cart the product will be deleted")
                            @PathVariable("customerId") UUID customerId,
                            @Parameter(description = "Id of a product that will be deleted from customer's cart")
                            @PathVariable("productId") UUID productId) {
        service.deleteById(customerId, productId);
    }
}
