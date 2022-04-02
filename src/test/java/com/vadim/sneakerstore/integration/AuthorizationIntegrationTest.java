package com.vadim.sneakerstore.integration;

import com.vadim.sneakerstore.dto.CommentDto;
import com.vadim.sneakerstore.model.AuthorizationRequestDto;
import com.vadim.sneakerstore.model.RegistrationRequestDto;
import com.vadim.sneakerstore.model.ResetPasswordRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application.properties")
@AutoConfigureMockMvc
@Transactional
public class AuthorizationIntegrationTest {

    @Autowired
    private WebApplicationContext applicationContext;

    private MockMvc mockMvc;

    private AuthorizationRequestDto authorizationRequestDto;
    private RegistrationRequestDto registrationRequestDto;
    private ResetPasswordRequestDto resetPasswordRequestDto;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.applicationContext).build();
    }

    @BeforeEach
    public void shouldAuthorizeCustomer() {
        authorizationRequestDto = new AuthorizationRequestDto();
        registrationRequestDto = new RegistrationRequestDto();
        resetPasswordRequestDto = new ResetPasswordRequestDto();

        authorizationRequestDto.setPassword("password");
        authorizationRequestDto.setEmail("vadim@gmail.com");

        registrationRequestDto.setPassword("password");
        registrationRequestDto.setEmail("email@gmail.com");
        registrationRequestDto.setFirstName("firstName");
        registrationRequestDto.setLastName("lastName");
        registrationRequestDto.setPhone("phone");

        resetPasswordRequestDto.setNewPassword("newPassword");
        resetPasswordRequestDto.setEmail("vadim@gmail.com");
     //   resetPasswordRequestDto.setCode();
    }
    /*
    public CustomerDto authorize(AuthorizationRequestDto requestDto) {
        Customer customer = customerRepository.findByEmail(requestDto.getEmail()).orElseThrow(() ->
                new NotFoundException("Customer with email = " + requestDto.getEmail() + " is not found")
        );
        if (!encoder.matches(requestDto.getPassword(), customer.getPassword())) {
            throw new AccessDeniedException("Wrong password");
        }
        return converter.convertToDto(customer);
    }

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
    public Integer forgotPassword(String email) {
        Customer customer = customerRepository.findByEmail(email).orElseThrow(() ->
                new NotFoundException("Customer with email " + email + " is not found")
        );
        Confirmation confirmation = new Confirmation();
        Integer code = (int) (Math.random() * 4000);
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
     */
}
