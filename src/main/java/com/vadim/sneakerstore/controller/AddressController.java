package com.vadim.sneakerstore.controller;

import com.vadim.sneakerstore.dto.AddressDto;
import com.vadim.sneakerstore.service.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Address Controller", description = "Address CRUD operations")
@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    private final AddressService service;

    public AddressController(AddressService service) {
        this.service = service;
    }

    @Operation(description = "Get address by its id")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AddressDto getAddress(@PathVariable("id") UUID id) {
        return service.getById(id);
    }

    @Operation(description = "Get all addresses")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AddressDto> getAllAddresses() {
        return service.getAll();
    }

    @Operation(description = "Save a new address")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AddressDto postAddresses(@Valid @RequestBody AddressDto addressDto) {
        return service.save(addressDto);
    }

    @Operation(description = "Update existed address")
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public AddressDto putAddresses(@Valid @RequestBody AddressDto addressDto) {
        return service.update(addressDto);
    }

    @Operation(description = "Delete existed address by its id")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAddress(@PathVariable("id") UUID id) {
        service.deleteById(id);
    }
}
