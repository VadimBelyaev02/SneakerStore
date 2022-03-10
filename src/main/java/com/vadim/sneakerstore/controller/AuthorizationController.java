package com.vadim.sneakerstore.controller;

import com.vadim.sneakerstore.model.AuthorizationRequestDto;
import com.vadim.sneakerstore.service.AuthorizationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class AuthorizationController {

    private final AuthorizationService service;

    public AuthorizationController(AuthorizationService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void login(@Valid @RequestBody AuthorizationRequestDto requestDto) {
        service.authorize(requestDto);
    }
}
