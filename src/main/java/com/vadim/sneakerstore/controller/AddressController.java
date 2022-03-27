package com.vadim.sneakerstore.controller;

import com.vadim.sneakerstore.dto.AddressDto;
import com.vadim.sneakerstore.service.AddressService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    private final AddressService service;

    public AddressController(AddressService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AddressDto getAddress(@PathVariable("id") UUID id) {
        return service.getById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AddressDto> getAllAddresses() {
        return service.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AddressDto postAddresses(@Valid @RequestBody AddressDto addressDto) {
        return service.save(addressDto);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public AddressDto putAddresses(@Valid @RequestBody AddressDto addressDto) {
        return service.update(addressDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAddress(@PathVariable("id") UUID id) {
        service.deleteById(id);
    }
}
