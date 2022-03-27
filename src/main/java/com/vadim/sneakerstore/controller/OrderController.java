package com.vadim.sneakerstore.controller;

import com.vadim.sneakerstore.dto.OrderDto;
import com.vadim.sneakerstore.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Order Controller", description = "Order CRUD operations")
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @Operation(description = "Get order by its id")
    @ApiResponse(description = "Order is found")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDto getOrder(@PathVariable("id") UUID id) {
        return service.getById(id);
    }

    @Operation(description = "Get all orders")
    @ApiResponse(description = "All orders are found")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<OrderDto> getAllOrders() {
        return service.getAll();
    }

    @Operation(description = "Save a new order")
    @ApiResponse(description = "Order is successfully created")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto postOrder(@Valid @RequestBody OrderDto orderDto) {
        return service.save(orderDto);
    }

    @Operation(description = "Update existed order")
    @ApiResponse(description = "Order is successfully updated")
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public OrderDto putOrder(@Valid @RequestBody OrderDto orderDto) {
        return service.update(orderDto);
    }

    @Operation(description = "Delete existed order")
    @ApiResponse(description = "Order is successfully deleted")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable("id") UUID id) {
        service.deleteById(id);
    }
}
