package com.vadim.sneakerstore.service.impl;

import com.vadim.sneakerstore.dto.CustomerDto;
import com.vadim.sneakerstore.dto.converter.CustomerConverter;
import com.vadim.sneakerstore.entity.Customer;
import com.vadim.sneakerstore.exception.AccessDeniedException;
import com.vadim.sneakerstore.exception.AlreadyExistsException;
import com.vadim.sneakerstore.exception.NotFoundException;
import com.vadim.sneakerstore.model.AuthorizationRequestDto;
import com.vadim.sneakerstore.model.RegistrationRequestDto;
import com.vadim.sneakerstore.repository.CustomerRepository;
import com.vadim.sneakerstore.service.AuthorizationService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthorizationServiceImpl implements AuthorizationService {

    private final CustomerRepository repository;
    private final CustomerConverter converter;
   // private final PasswordEncoder encoder;

    public AuthorizationServiceImpl(CustomerRepository repository, CustomerConverter converter) {
        this.repository = repository;
        this.converter = converter;
   //     this.encoder = encoder;
    }

    @Override
    @Transactional
    public void authorize(AuthorizationRequestDto requestDto) {
        Customer customer = repository.findByEmail(requestDto.getEmail()).orElseThrow(() ->
                new NotFoundException("Customer with email = " + requestDto.getEmail() + " is not found")
        );
//        if (!customer.getPassword().equals(encoder.encode(requestDto.getPassword()))) {
//            throw new AccessDeniedException("Wrong password");
//        }

    }

    @Override
    @Transactional
    public CustomerDto registerCustomer(RegistrationRequestDto requestDto) {
        if (repository.existsByPhoneAndEmail(requestDto.getPhone(), requestDto.getEmail())) {
            throw new AlreadyExistsException("Customer with phone  = " + requestDto.getPhone()
                    + " and email = " + requestDto.getEmail() + " already exists");
        }
        if (repository.existsByPhone(requestDto.getPhone())) {
            throw new AlreadyExistsException("Customer with phone = " + requestDto.getPhone() + " already exists");
        }
        if (repository.existsByEmail(requestDto.getEmail())) {
            throw new AlreadyExistsException("Customer with email = " + requestDto.getEmail() + " already exists");
        }
        Customer customer = repository.save(converter.convertToEntity(requestDto));
        return converter.convertToDto(customer);
    }
}
