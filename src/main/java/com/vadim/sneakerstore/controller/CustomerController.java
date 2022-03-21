package com.vadim.sneakerstore.controller;

import com.vadim.sneakerstore.dto.CustomerDto;
import com.vadim.sneakerstore.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "https://sneakerstore.vercel.app", maxAge = 3600)
@Slf4j
@Tag(name = "Customer Controller", description = "Customer CRUD operations")
@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Operation(description = "Get customer by id")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerDto getCustomer(@PathVariable("id") UUID id) {
        log.info("KJKJFDGKFJGKFDJGRTJROINVBM");
        return customerService.getCustomer(id);
    }

    @Operation(description = "Get all customers")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerDto> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @Operation(description = "Add a new customer")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDto postCustomer(@Valid @RequestBody CustomerDto customerDto) {
        return customerService.saveCustomer(customerDto);
    }

    @Operation(description = "Update existed customer")
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public CustomerDto putCustomer(@Valid @RequestBody CustomerDto customerDto) {
        return customerService.updateCustomer(customerDto);
    }

    @Operation(description = "Delete customer by id")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable("id") UUID id) {
        customerService.deleteCustomer(id);
    }
}
