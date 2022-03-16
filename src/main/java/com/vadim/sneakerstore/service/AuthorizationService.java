package com.vadim.sneakerstore.service;

import com.vadim.sneakerstore.dto.CustomerDto;
import com.vadim.sneakerstore.model.AuthorizationRequestDto;
import com.vadim.sneakerstore.model.ChangePasswordRequestDto;
import com.vadim.sneakerstore.model.RegistrationRequestDto;
import com.vadim.sneakerstore.model.ResetPasswordRequestDto;

public interface AuthorizationService {

    CustomerDto authorize(AuthorizationRequestDto requestDto);

    CustomerDto registerCustomer(RegistrationRequestDto requestDto);

    CustomerDto resetPassword(ResetPasswordRequestDto requestDto);

    Integer forgotPassword(String email);

    CustomerDto changePassword(ChangePasswordRequestDto requestDto);
}
