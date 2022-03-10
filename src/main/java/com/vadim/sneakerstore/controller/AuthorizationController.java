package com.vadim.sneakerstore.controller;

import com.vadim.sneakerstore.dto.CustomerDto;
import com.vadim.sneakerstore.model.AuthorizationRequestDto;
import com.vadim.sneakerstore.model.RegistrationRequestDto;

import com.vadim.sneakerstore.service.AuthorizationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class AuthorizationController {

    private final AuthorizationService service;

    public AuthorizationController(AuthorizationService service) {
        this.service = service;
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public CustomerDto login(@Valid @RequestBody AuthorizationRequestDto requestDto) {
        return service.authorize(requestDto);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDto register(@Valid @RequestBody RegistrationRequestDto requestDto) {
        return service.registerCustomer(requestDto);
    }

//    @PostMapping
//    @ResponseStatus(HttpStatus.OK)
//    public CustomerDto resetPassword(@Valid @RequestBody ResetPasswordRequestDto requestDto) {
//        return null;
//    }
}
