package com.vadim.sneakerstore.integration;

import com.vadim.sneakerstore.dto.AddressDto;
import com.vadim.sneakerstore.dto.SizeDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

import static com.vadim.sneakerstore.utils.JsonParser.toJson;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.springframework.http.MediaType;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application.properties")
@AutoConfigureMockMvc
@Transactional
public class AddressIntegrationTest {

    private final String ENDPOINT = "/api/addresses";

    @Autowired
    private WebApplicationContext applicationContext;

    private MockMvc mockMvc;

    private AddressDto addressDto;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.applicationContext).build();
    }

    @BeforeEach
    public void init() {
        addressDto = AddressDto.builder()
                .id(UUID.fromString("998a0dfe-ac53-11ec-b909-0242ac120002"))
                .city("Minsk")
                .country("Belarus")
                .street("Ploshca Yakuba Kolasa 28")
                .build();
    }

    @Test
    public void shouldReturnInfoThatAddressIsNotFound() throws Exception {
        mockMvc.perform(get(ENDPOINT + addressDto.getId()))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnAllAddresses() throws Exception {
        mockMvc.perform(get(ENDPOINT))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void shouldReturnBadRequestWithEmptyBodyInPost() throws Exception {
        mockMvc.perform(post(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnBadRequestWithEmptyBodyInPut() throws Exception {
        mockMvc.perform(put(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnAddressDtoById() throws Exception {
        String id = "89039889-99a8-48e1-a570-e578580fb6cb";
        mockMvc.perform(get(ENDPOINT + "/{id}", id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.city").value("Gomel"))
                .andExpect(jsonPath("$.country").value("Minck"))
                .andExpect(jsonPath("$.street").value("street"));
    }

    @Test
    public void shouldReturnSavedAddressDto() throws Exception {
        mockMvc.perform(post(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(addressDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.country").value("Belarus"))
                .andExpect(jsonPath("$.street").value("Ploshca Yakuba Kolasa 28"))
                .andExpect(jsonPath("$.city").value("Minsk"));
    }

    @Test
    public void shouldReturnInfoThatNotFoundWhileUpdating() throws Exception {
        UUID id = UUID.randomUUID();
        addressDto.setId(id);
        String expectedMessage = "Address with id = " + id + " is not found";

        mockMvc.perform(put(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(addressDto)))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(expectedMessage));
    }

    @Test
    public void shouldReturnUpdatedAddressDto() throws Exception {
        addressDto.setId(UUID.fromString("89039889-99a8-48e1-a570-e578580fb6cb"));
        String city = "newCity";
        addressDto.setCity(city);

        mockMvc.perform(put(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(addressDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.city").value(city));

    }

    @Test
    public void shouldReturnNotFoundInfoWhileDeleting() throws Exception {
        UUID id = UUID.randomUUID();
        String expectedMessage = "Address with id = " + id + " is not found";

        mockMvc.perform(delete(ENDPOINT + "/{id}", id))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(expectedMessage));
    }

    @Test
    public void shouldReturnUnsupportedMediaTypeInPost() throws Exception {
        mockMvc.perform(post(ENDPOINT)
                        .contentType(MediaType.APPLICATION_XML)
                        .content(toJson(addressDto)))
                .andDo(print())
                .andExpect(status().isUnsupportedMediaType());
    }

    @Test
    public void shouldReturnNoContent() throws Exception {
        String id = "89039889-99a8-48e1-a570-e578580fb6cb";
        mockMvc.perform(delete(ENDPOINT + "/{id}", id))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldReturnInfoThatAlreadyExists() throws Exception {
        addressDto.setId(UUID.fromString("89039889-99a8-48e1-a570-e578580fb6cb"));
        addressDto.setCountry("Minck");
        addressDto.setStreet("street");
        addressDto.setCity("Gomel");

        mockMvc.perform(post(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(addressDto)))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void shouldReturnCustomersAddresses() throws Exception {
        UUID customerId = UUID.fromString("998a0dfe-ac53-11ec-b909-0242ac120002");

        String endpoint = "/api/customers";
        mockMvc.perform(get(endpoint + "/{customerId}/addresses", customerId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

}
