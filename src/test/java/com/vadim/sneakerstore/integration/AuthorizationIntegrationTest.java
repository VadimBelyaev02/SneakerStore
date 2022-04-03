package com.vadim.sneakerstore.integration;

import com.vadim.sneakerstore.dto.CommentDto;
import com.vadim.sneakerstore.model.AuthorizationRequestDto;
import com.vadim.sneakerstore.model.ChangePasswordRequestDto;
import com.vadim.sneakerstore.model.RegistrationRequestDto;
import com.vadim.sneakerstore.model.ResetPasswordRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.vadim.sneakerstore.utils.JsonParser.toJson;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application.properties")
@AutoConfigureMockMvc
@Transactional
public class AuthorizationIntegrationTest {

    private final String LOGIN_ENDPOINT = "/api/login";
    private final String REGISTER_ENDPOINT = "/api/register";
    private final String RESET_ENDPOINT = "/api/reset_password";
    private final String FORGOT_ENDPOINT = "/api/forgot_password";
    private final String CHANGE_ENDPOINT = "/api/change_password";


    @Autowired
    private WebApplicationContext applicationContext;

    private MockMvc mockMvc;

    private AuthorizationRequestDto authorizationRequestDto;
    private RegistrationRequestDto registrationRequestDto;
    private ResetPasswordRequestDto resetPasswordRequestDto;
    private ChangePasswordRequestDto changePasswordRequestDto;

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
        resetPasswordRequestDto.setCode("0123");

        changePasswordRequestDto.setNewPassword("newPassword");
        changePasswordRequestDto.setOldPassword("password");
        changePasswordRequestDto.setEmail("vadim@gmail.com");
    }

    @Test
    public void shouldAuthorizeRequestAndReturnCustomerDto() throws Exception {
        mockMvc.perform(post(LOGIN_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(authorizationRequestDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName").value("dfg"));
    }

    @Test
    public void shouldReturnUnauthorizedWithWrongPassword() throws Exception {
        authorizationRequestDto.setPassword("wrong");

        String expectedMessage = "Wrong password";
        mockMvc.perform(post(LOGIN_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(authorizationRequestDto)))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(expectedMessage));
    }

    @Test
    public void shouldReturnInfoThatEmailNotFound() throws Exception {
        authorizationRequestDto.setEmail("wrong");
        String expectedMessage = "Customer with email = " + authorizationRequestDto.getEmail() + " is not found";
        mockMvc.perform(post(LOGIN_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(authorizationRequestDto)))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(expectedMessage));
    }

    @Test
    public void shouldReturnUnsupportedMediaTypeInLogin() throws Exception {
        mockMvc.perform(post(LOGIN_ENDPOINT)
                        .contentType(MediaType.APPLICATION_XML)
                        .content(toJson(authorizationRequestDto)))
                .andDo(print())
                .andExpect(status().isUnsupportedMediaType());
    }

    @Test
    public void shouldReturnBadRequestWithEmptyBodyInLogin() throws Exception {
        mockMvc.perform(post(LOGIN_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnRegisteredCustomerDto() throws Exception {
        mockMvc.perform(post(REGISTER_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(registrationRequestDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName").value("firstName"))
                .andExpect(jsonPath("$.phone").value("phone"))
                .andExpect(jsonPath("$.email").value("email@gmail.com"));
    }

    @Test
    public void shouldReturnAlreadyExistsWithWrongPhone() throws Exception {
        registrationRequestDto.setPhone("324");
        String expectedMessage = "Customer with phone = 324 already exists";

        mockMvc.perform(post(REGISTER_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(registrationRequestDto)))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(expectedMessage));
    }

    @Test
    public void shouldReturnAlreadyExistsWithWrongEmail() throws Exception {
        registrationRequestDto.setEmail("vadim@gmail.com");
        String expectedMessage = "Customer with email = vadim@gmail.com already exists";

        mockMvc.perform(post(REGISTER_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(registrationRequestDto)))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(expectedMessage));
    }

    @Test
    public void shouldReturnAlreadyExistsWithWrongEmailAndPhone() throws Exception {
        registrationRequestDto.setPhone("324");
        registrationRequestDto.setEmail("vadim@gmail.com");
        String expectedMessage = "Customer with phone = 324 and email = vadim@gmail.com already exists";

        mockMvc.perform(post(REGISTER_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(registrationRequestDto)))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value(expectedMessage));
    }

    @Test
    public void shouldReturnBadRequestWithNullFieldInRegister() throws Exception {
        registrationRequestDto.setPhone(null);

        mockMvc.perform(post(REGISTER_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(registrationRequestDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void shouldResetPasswordAndReturnOk() throws Exception {
        mockMvc.perform(post(RESET_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(resetPasswordRequestDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName").value("dfg"))
                .andExpect(jsonPath("$.phone").value("324"))
                .andExpect(jsonPath("$.lastName").value("dfg"))
                .andExpect(jsonPath("$.email").value("vadim@gmail.com"));
    }

    @Test
    public void shouldReturnThatCustomerIsNotFound() throws Exception {
        resetPasswordRequestDto.setEmail("wrong");
        String expectedMessage = "Customer with email = " + resetPasswordRequestDto.getEmail() + " is not found";

        mockMvc.perform(post(RESET_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(resetPasswordRequestDto)))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(expectedMessage));
    }

    @Test
    public void shouldReturnUnauthorizedWithIncorrectCode() throws Exception {
        resetPasswordRequestDto.setCode("3210");
        String expectedMessage = "Incorrect code!";

        mockMvc.perform(post(RESET_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(resetPasswordRequestDto)))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(expectedMessage));
    }

    @Test
    public void shouldReturnUnsupportedMediaTypeInRegister() throws Exception {
        mockMvc.perform(post(REGISTER_ENDPOINT)
                        .contentType(MediaType.APPLICATION_XML)
                        .content(toJson(registrationRequestDto)))
                .andDo(print())
                .andExpect(status().isUnsupportedMediaType());
    }

    @Test
    public void shouldReturnBadRequestWithEmptyBodyInRegister() throws Exception {
        mockMvc.perform(post(REGISTER_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnUnsupportedMediaTypeInReset() throws Exception {
        mockMvc.perform(post(RESET_ENDPOINT)
                        .contentType(MediaType.APPLICATION_XML)
                        .content(toJson(resetPasswordRequestDto)))
                .andDo(print())
                .andExpect(status().isUnsupportedMediaType());
    }

    @Test
    public void shouldReturnBadRequestWithEmptyBodyInReset() throws Exception {
        mockMvc.perform(post(RESET_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(""))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnBadRequestWithNullFieldInReset() throws Exception {
        registrationRequestDto.setEmail(null);

        mockMvc.perform(post(RESET_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(resetPasswordRequestDto)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnNotFoundWithWrongEmailInForgot() throws Exception {
        String email = "wrong@gmail.com";
        String expectedMessage = "Customer with email = " + email + " is not found";

        mockMvc.perform(post(FORGOT_ENDPOINT)
                        .param("email", email))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(expectedMessage));
    }

    @Test
    public void shouldReturnBadRequestWithEmptyParamsInForgot() throws Exception {
        mockMvc.perform(post(FORGOT_ENDPOINT))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldSendAnEmailInForgot() throws Exception {
        String email = "vadim@gmail.com";
        mockMvc.perform(post(FORGOT_ENDPOINT)
                        .param("email", email))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnNotFoundInChange() throws Exception {
        changePasswordRequestDto.setEmail("wrong@gmail.com");
        String expectedMessage = "Customer with email = wrong@gmail.com is not found";

        mockMvc.perform(put(CHANGE_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(changePasswordRequestDto)))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(expectedMessage));
    }

    @Test
    public void shouldReturnBadRequestWithEmptyBodyInChange() throws Exception {
        mockMvc.perform(put(CHANGE_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .contentType(""))
                .andDo(print())
                .andExpect(status().isBadRequest())
    }

    @Test
    public void shouldReturnUnsupportedMediaTypeInChange() throws Exception {
        mockMvc.perform(put(CHANGE_ENDPOINT)
                .contentType(MediaType.APPLICATION_XML)
                .content(toJson(changePasswordRequestDto)))
                .andDo(print())
                .andExpect(status().isUnsupportedMediaType());
    }

    @Test
    public void shouldReturnForbiddenWithIncorrectPassword() throws Exception {
        changePasswordRequestDto.setOldPassword("wrong");
        String expectedMessage = "Incorrect old password!";

        mockMvc.perform(put(CHANGE_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(changePasswordRequestDto)))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(expectedMessage));
    }

    @Test
    public void shouldReturnBadRequestWithIncorrectEmailPattern() throws Exception {
        changePasswordRequestDto.setEmail("notemail");

        mockMvc.perform(put(CHANGE_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(changePasswordRequestDto)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}
