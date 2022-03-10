package com.vadim.sneakerstore.service.impl;

import com.vadim.sneakerstore.entity.Customer;
import com.vadim.sneakerstore.exception.AccessDeniedException;
import com.vadim.sneakerstore.exception.NotFoundException;
import com.vadim.sneakerstore.model.AuthorizationRequestDto;
import com.vadim.sneakerstore.repository.CustomerRepository;
import com.vadim.sneakerstore.service.AuthorizationService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationServiceImpl implements AuthorizationService {

    private final CustomerRepository repository;
    private final PasswordEncoder encoder;

    public AuthorizationServiceImpl(CustomerRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    @Override
    public void authorize(AuthorizationRequestDto requestDto) {
        Customer customer = repository.findByEmail(requestDto.getEmail()).orElseThrow(() ->
                new NotFoundException("Customer with email = " + requestDto.getEmail() + " is not found")
        );
        if (!customer.getPassword().equals(encoder.encode(requestDto.getPassword()))) {
            throw new AccessDeniedException("Wrong password");
        }
    }
}
