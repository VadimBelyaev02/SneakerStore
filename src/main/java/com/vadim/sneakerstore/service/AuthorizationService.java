package com.vadim.sneakerstore.service;

import com.vadim.sneakerstore.model.AuthorizationRequestDto;

public interface AuthorizationService {

    void authorize(AuthorizationRequestDto requestDto);
}
