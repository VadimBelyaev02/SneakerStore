package com.vadim.sneakerstore.controller;

import com.vadim.sneakerstore.dto.AddressDto;
import com.vadim.sneakerstore.service.AddressService;
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
@Tag(name = "Address Controller", description = "Address CRUD operations")
@RestController
@RequestMapping("/api")
public class AddressController {

    private final AddressService service;

    public AddressController(AddressService service) {
        this.service = service;
    }

    @Operation(description = "Get address by its id")
    @ApiResponse(description = "Address is found", responseCode = "200")
    @GetMapping("/addresses/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AddressDto getAddress(@Parameter(description = "Id of needed address")
                                 @PathVariable("id") UUID id) {
        return service.getById(id);
    }

    @Operation(description = "Get all addresses")
    @ApiResponse(description = "Addresses are found", responseCode = "200")
    @GetMapping("/addresses")
    @ResponseStatus(HttpStatus.OK)
    public List<AddressDto> getAllAddresses() {
        return service.getAll();
    }

    @Operation(description = "Get all customer's addresses")
    @ApiResponse(description = "All customer's addresses are found", responseCode = "200")
    @GetMapping("/customers/{customerId}/addresses")
    @ResponseStatus(HttpStatus.OK)
    public List<AddressDto> getAllByCustomerId(@Parameter(description = "Customer's id whose addresses need to find")
                                               @PathVariable("customerId") UUID customerId) {
        return service.getAllByCustomerId(customerId);
    }

    @Operation(description = "Save a new address")
    @ApiResponse(description = "Address is successfully saved", responseCode = "201")
    @PostMapping("/addresses")
    @ResponseStatus(HttpStatus.CREATED)
    public AddressDto postAddresses(@Parameter(description = "Contains info about new address")
                                    @Valid @RequestBody AddressDto addressDto) {
        return service.save(addressDto);
    }

    @Operation(description = "Update existed address")
    @ApiResponse(description = "Address is successfully updated", responseCode = "200")
    @PutMapping("/addresses")
    @ResponseStatus(HttpStatus.OK)
    public AddressDto putAddresses(@Parameter(description = "Contains new fields")
                                   @Valid @RequestBody AddressDto addressDto) {
        return service.update(addressDto);
    }

    @Operation(description = "Delete existed address by its id")
    @ApiResponse(description = "Address is successfully deleted", responseCode = "204")
    @DeleteMapping("/addresses/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAddress(@Parameter(description = "Id of deleted address")
                              @PathVariable("id") UUID id) {
        service.deleteById(id);
    }
}
