package com.vadim.sneakerstore.controller;

import com.vadim.sneakerstore.dto.CartDto;
import com.vadim.sneakerstore.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(description = "Get customer's cart ") // INCORRECT
    @GetMapping("/{customerId}/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public CartDto getCart(@PathVariable("customerId") UUID customerId,
                           @PathVariable("productId") UUID productId) {
        return service.getById(customerId, productId);
    }

    @Operation(description = "Get all products from customer's cart by his id")
    @GetMapping("/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    public List<CartDto> getByCustomerId(@PathVariable("customerId") UUID customerId) {
        return service.getByCustomerId(customerId);
    }

    @Operation(description = "Get all products from all carts")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CartDto> getAllCards() {
        return service.getAll();
    }

    @Operation(description = "Save a new product in the cart")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CartDto postCarts(@Valid @RequestBody CartDto cartDto) {
        return service.save(cartDto);
    }

    @Operation(description = "Updated existed product in the cart")
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public CartDto putCarts(@Valid @RequestBody CartDto cartDto) {
        return service.update(cartDto);
    }

    @Operation(description = "Delete existed product from the cart")
    @DeleteMapping("/{customerId}/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCarts(@PathVariable("customerId") UUID customerId,
                            @PathVariable("productId") UUID productId) {
        service.deleteById(customerId, productId);
    }
}