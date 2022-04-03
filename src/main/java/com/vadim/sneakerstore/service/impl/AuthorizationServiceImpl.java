package com.vadim.sneakerstore.service.impl;

import com.vadim.sneakerstore.dto.CustomerDto;
import com.vadim.sneakerstore.dto.converter.CustomerConverter;
import com.vadim.sneakerstore.entity.Confirmation;
import com.vadim.sneakerstore.entity.Customer;
import com.vadim.sneakerstore.exception.AccessDeniedException;
import com.vadim.sneakerstore.exception.AlreadyExistsException;
import com.vadim.sneakerstore.exception.NotFoundException;
import com.vadim.sneakerstore.model.AuthorizationRequestDto;
import com.vadim.sneakerstore.model.ChangePasswordRequestDto;
import com.vadim.sneakerstore.model.RegistrationRequestDto;
import com.vadim.sneakerstore.model.ResetPasswordRequestDto;
import com.vadim.sneakerstore.repository.ConfirmationRepository;
import com.vadim.sneakerstore.repository.CustomerRepository;
import com.vadim.sneakerstore.service.AuthorizationService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

@Service
public class AuthorizationServiceImpl implements AuthorizationService {

    private final CustomerRepository customerRepository;
    private final CustomerConverter converter;
    private final PasswordEncoder encoder;
    private final ConfirmationRepository confirmationRepository;

    public AuthorizationServiceImpl(CustomerRepository customerRepository, CustomerConverter converter, PasswordEncoder encoder, ConfirmationRepository confirmationRepository) {
        this.customerRepository = customerRepository;
        this.converter = converter;
        this.encoder = encoder;
        this.confirmationRepository = confirmationRepository;
    }


    @Override
    @Transactional
    public CustomerDto authorize(AuthorizationRequestDto requestDto) {
        Customer customer = customerRepository.findByEmail(requestDto.getEmail()).orElseThrow(() ->
                new NotFoundException("Customer with email = " + requestDto.getEmail() + " is not found")
        );
//        if (!encoder.matches(requestDto.getPassword(), customer.getPassword())) {
//            throw new AccessDeniedException("Wrong password");
//        }
        return converter.convertToDto(customer);
    }

    @Override
    @Transactional
    public CustomerDto registerCustomer(RegistrationRequestDto requestDto) {
        if (customerRepository.existsByPhoneAndEmail(requestDto.getPhone(), requestDto.getEmail())) {
            throw new AlreadyExistsException("Customer with phone " + requestDto.getPhone()
                    + " and email " + requestDto.getEmail() + " already exists");
        }
        if (customerRepository.existsByPhone(requestDto.getPhone())) {
            throw new AlreadyExistsException("Customer with phone " + requestDto.getPhone() + " already exists");
        }
        if (customerRepository.existsByEmail(requestDto.getEmail())) {
            throw new AlreadyExistsException("Customer with email " + requestDto.getEmail() + " already exists");
        }
        requestDto.setPassword(encoder.encode(requestDto.getPassword()));
        Customer customer = customerRepository.save(converter.convertToEntity(requestDto));
        return converter.convertToDto(customer);
    }

    @Override
    @Transactional
    public CustomerDto resetPassword(ResetPasswordRequestDto requestDto) {
        Customer customer = customerRepository.findByEmail(requestDto.getEmail()).orElseThrow(() ->
                new NotFoundException("Customer with email " + requestDto.getEmail() + " is not found")
        );
        Confirmation confirmation = customer.getConfirmation();
        if (Objects.isNull(confirmation) || !confirmation.getCode().equals(requestDto.getCode())) {
            throw new AccessDeniedException("Incorrect code!");
        }
        customer.setPassword(encoder.encode(requestDto.getNewPassword()));
        customer.setConfirmation(null);
        confirmation.setCustomer(null);
        confirmationRepository.delete(confirmation);
        return converter.convertToDto(customer);
    }

    @Override
    @Transactional
    public String forgotPassword(String email) {
        Customer customer = customerRepository.findByEmail(email).orElseThrow(() ->
                new NotFoundException("Customer with email " + email + " is not found")
        );
        Confirmation confirmation = new Confirmation();
        String code = String.valueOf((int) (Math.random() * 4000));
        confirmation.setCode(code);
        confirmation.setCustomer(customer);
        confirmationRepository.save(confirmation);
        return code;
    }

    @Override
    @Transactional
    public CustomerDto changePassword(ChangePasswordRequestDto requestDto) {
        Customer customer = customerRepository.findByEmail(requestDto.getEmail()).orElseThrow(() ->
                new NotFoundException("Customer with email " + requestDto.getEmail() + " is not found")
        );
        if (!encoder.matches(requestDto.getOldPassword(), customer.getPassword())) {
            throw new AccessDeniedException("Incorrect old password!");
        }
        customer.setPassword(encoder.encode(requestDto.getNewPassword()));
        return converter.convertToDto(customer);
    }
}
