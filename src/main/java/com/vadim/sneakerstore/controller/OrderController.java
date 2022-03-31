package com.vadim.sneakerstore.controller;

import com.vadim.sneakerstore.dto.OrderDto;
import com.vadim.sneakerstore.service.OrderService;
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
@Tag(name = "Order Controller", description = "Order CRUD operations")
@RestController
@RequestMapping("/api")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @Operation(description = "Get order by its id")
    @ApiResponse(description = "Order is found", responseCode = "200")
    @GetMapping("/orders/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDto getOrder(@Parameter(description = "Id of needed order")
                             @PathVariable("id") UUID id) {
        return service.getById(id);
    }

    @Operation(description = "Get all orders")
    @ApiResponse(description = "All orders are found", responseCode = "200")
    @GetMapping("/orders")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderDto> getAllOrders() {
        return service.getAll();
    }

    @Operation(description = "Get all customer's order")
    @ApiResponse(description = "All customer's orders are found", responseCode = "200")
    @GetMapping("/customers/{customerId}/orders")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderDto> getAllOrdersByCustomerId(@Parameter(description = "Id of a customer whose order should be found")
                                                   @PathVariable("customerId") UUID customerId) {
        return service.getAllByCustomerId(customerId);
    }

    @Operation(description = "Save a new order")
    @ApiResponse(description = "Order is successfully created", responseCode = "201")
    @PostMapping("/orders")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto postOrder(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Contains info about a new order")
                              @Valid @RequestBody OrderDto orderDto) {
        return service.save(orderDto);
    }

    @Operation(description = "Update existed order")
    @ApiResponse(description = "Order is successfully updated", responseCode = "200")
    @PutMapping("/orders")
    @ResponseStatus(HttpStatus.OK)
    public OrderDto putOrder(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Contains info about new order's fields")
                             @Valid @RequestBody OrderDto orderDto) {
        return service.update(orderDto);
    }

    @Operation(description = "Delete existed order")
    @ApiResponse(description = "Order is successfully deleted", responseCode = "204")
    @DeleteMapping("/orders/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@Parameter(description = "Id of a deleted order")
                            @PathVariable("id") UUID id) {
        service.deleteById(id);
    }
}
