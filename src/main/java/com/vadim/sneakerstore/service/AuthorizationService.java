package com.vadim.sneakerstore.service;

import com.vadim.sneakerstore.dto.CustomerDto;
import com.vadim.sneakerstore.model.AuthorizationRequestDto;
import com.vadim.sneakerstore.model.RegistrationRequestDto;

public interface AuthorizationService {

    CustomerDto authorize(AuthorizationRequestDto requestDto);

    CustomerDto registerCustomer(RegistrationRequestDto requestDto);
}
