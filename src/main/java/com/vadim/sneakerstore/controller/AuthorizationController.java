package com.vadim.sneakerstore.controller;

import com.vadim.sneakerstore.dto.CustomerDto;
import com.vadim.sneakerstore.model.AuthorizationRequestDto;
import com.vadim.sneakerstore.model.ChangePasswordRequestDto;
import com.vadim.sneakerstore.model.RegistrationRequestDto;

import com.vadim.sneakerstore.model.ResetPasswordRequestDto;
import com.vadim.sneakerstore.service.AuthorizationService;
import com.vadim.sneakerstore.service.MailSenderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "Authorization Controller", description = "Authorization operations")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
@Slf4j
public class AuthorizationController {

    private final AuthorizationService authorizationService;
    private final MailSenderService senderService;

    public AuthorizationController(AuthorizationService authorizationService, MailSenderService senderService) {
        this.authorizationService = authorizationService;
        this.senderService = senderService;
    }

    @Operation(description = "Login into the system")
    @ApiResponse(description = "Account is logged in")
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public CustomerDto login(@Valid @RequestBody AuthorizationRequestDto requestDto) {
        return authorizationService.authorize(requestDto);
    }

    @Operation(description = "Register in the system")
    @ApiResponse(description = "Account is registered")
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDto register(@Valid @RequestBody RegistrationRequestDto requestDto) {
        return authorizationService.registerCustomer(requestDto);
    }

    @Operation(description = "It's used if somebody has forgotten his password")
    @ApiResponse(description = "Account found and email is sended")
    @PostMapping("/forgot_password")
    @ResponseStatus(HttpStatus.OK)
    public void forgotPassword(@RequestParam String email) {
        Integer code = authorizationService.forgotPassword(email);
        senderService.sendMessage(email, String.valueOf(code), null);
    }

    @Operation(description = "Recover password after forgetting")
    @ApiResponse(description = "Password has been recovered")
    @PostMapping("/reset_password")
    @ResponseStatus(HttpStatus.OK)
    public CustomerDto resetPassword(@Valid @RequestBody ResetPasswordRequestDto requestDto) {
        return authorizationService.resetPassword(requestDto);
    }

    @Operation(description = "Change password by knowing the current password")
    @ApiResponse(description = "Password has been changed")
    @PutMapping("/change_password")
    @ResponseStatus(HttpStatus.OK)
    public CustomerDto changePassword(@Valid @RequestBody ChangePasswordRequestDto requestDto) {
        return authorizationService.changePassword(requestDto);
    }

}
