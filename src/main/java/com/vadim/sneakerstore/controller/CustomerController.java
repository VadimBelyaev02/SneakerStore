package com.vadim.sneakerstore.controller;

import com.vadim.sneakerstore.dto.CustomerDto;
import com.vadim.sneakerstore.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Customer Controller", description = "Customer CRUD operations")
@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Operation(description = "Get customer by id")
    @ApiResponse(description = "Customer is found", responseCode = "200")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerDto getCustomer(@Parameter(description = "Id of needed customer")
                                   @PathVariable("id") UUID id) {
        return customerService.getById(id);

    }

    @Operation(description = "Get all customers")
    @ApiResponse(description = "All customers are found", responseCode = "200")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerDto> getAllCustomers() {
        return customerService.getAll();
    }

    @Operation(description = "Add a new customer")
    @ApiResponse(description = "Customer is created", responseCode = "201")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDto postCustomer(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Contains info about fields of a new customer")
                                    @Valid @RequestBody CustomerDto customerDto) {
        return customerService.save(customerDto);
    }

    @Operation(description = "Update existed customer")
    @ApiResponse(description = "Customer is updated", responseCode = "200")
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public CustomerDto putCustomer(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Contains info about new customer's field")
                                   @Valid @RequestBody CustomerDto customerDto) {
        return customerService.update(customerDto);
    }

    @Operation(description = "Delete customer by id")
    @ApiResponse(description = "Customer is deleted", responseCode = "204")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@Parameter(description = "Id of deleted customer")
                               @PathVariable("id") UUID id) {
        customerService.deleteById(id);
    }
}
