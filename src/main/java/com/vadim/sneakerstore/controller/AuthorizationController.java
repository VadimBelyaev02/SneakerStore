package com.vadim.sneakerstore.controller;

import com.vadim.sneakerstore.dto.CustomerDto;
import com.vadim.sneakerstore.model.AuthorizationRequestDto;
import com.vadim.sneakerstore.model.ChangePasswordRequestDto;
import com.vadim.sneakerstore.model.RegistrationRequestDto;

import com.vadim.sneakerstore.model.ResetPasswordRequestDto;
import com.vadim.sneakerstore.service.AuthorizationService;
import com.vadim.sneakerstore.service.MailSenderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600, allowedHeaders = {"Authorization", "Access-Control-Allow-Origin"})
@RestController
@RequestMapping("/api")
public class AuthorizationController {

    private final AuthorizationService authorizationService;
    private final MailSenderService senderService;

    public AuthorizationController(AuthorizationService authorizationService, MailSenderService senderService) {
        this.authorizationService = authorizationService;
        this.senderService = senderService;
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public CustomerDto login(@Valid @RequestBody AuthorizationRequestDto requestDto) {
        return authorizationService.authorize(requestDto);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDto register(@Valid @RequestBody RegistrationRequestDto requestDto) {
        return authorizationService.registerCustomer(requestDto);
    }

    @PostMapping("/forgot_password")
    @ResponseStatus(HttpStatus.OK)
    public void forgotPassword(@RequestParam String email) {
        Integer code = authorizationService.forgotPassword(email);
        senderService.sendMessage(email, String.valueOf(code), null);
    }

    @PostMapping("/reset_password")
    @ResponseStatus(HttpStatus.OK)
    public CustomerDto resetPassword(@Valid @RequestBody ResetPasswordRequestDto requestDto) {
        return authorizationService.resetPassword(requestDto);
    }

    @PutMapping("/change_password")
    @ResponseStatus(HttpStatus.OK)
    public CustomerDto changePassword(@Valid @RequestBody ChangePasswordRequestDto requestDto) {
        return authorizationService.changePassword(requestDto);
    }
}
