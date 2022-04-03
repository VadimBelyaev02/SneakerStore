package com.vadim.sneakerstore.integration;

import com.vadim.sneakerstore.dto.CardDto;
import com.vadim.sneakerstore.entity.Card;
import liquibase.pro.packaged.B;
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

import java.time.LocalDate;
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
public class CardIntegrationTest {

    private final String ENDPOINT = "/api/cards";

    @Autowired
    private WebApplicationContext applicationContext;

    private MockMvc mockMvc;

    private CardDto cardDto;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.applicationContext).build();
    }

    @BeforeEach
    public void init() {
        cardDto = CardDto.builder()
                .id(UUID.fromString("89039889-99a8-48e1-a570-e578580fb6cb"))
                .owner("owner")
                .number("123456789")
                .validityDate(LocalDate.of(2022, 1, 1))
                .cvv("123")
                .build();
    }

    @Test
    public void shouldReturnCardById() throws Exception {
        String id = "9682140b-9d3e-44bb-a3bc-b398dd20c474";
        mockMvc.perform(get(ENDPOINT + "/{id}", id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.owner").value("owner"))
                .andExpect(jsonPath("$.number").value("123456789"))
                .andExpect(jsonPath("$.cvv").value("123"))
                .andExpect(jsonPath("$.id").value("9682140b-9d3e-44bb-a3bc-b398dd20c474"));
    }

    @Test
    public void shouldReturnMethodNotAllowedWithIncorrectEndpoint() throws Exception {
        mockMvc.perform(get(ENDPOINT + "/{id}", UUID.randomUUID()))
                .andDo(print())
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void shouldReturnMethodNotAllowedWithIncorrectRequest()  throws Exception{
        mockMvc.perform(get(ENDPOINT))
                .andDo(print())
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void shouldReturnSavedCard() throws Exception {
        mockMvc.perform(post(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(cardDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.cvv").value("123"))
                .andExpect(jsonPath("$.number").value("123456789"));
    }

    @Test
    public void shouldReturnInfoThatCardAlreadyExists() throws Exception {
       // cardDto.setId(UUID.fromString("9682140b-9d3e-44bb-a3bc-b398dd20c474"));


        cardDto.setNumber("12345678");
        mockMvc.perform(post(ENDPOINT).contentType(MediaType.APPLICATION_JSON)
                .content(toJson(cardDto)))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    public void shouldReturnUnsupportedMediaTypeInPost() throws Exception {
        mockMvc.perform(post(ENDPOINT).contentType(MediaType.APPLICATION_XML)
                .content(toJson(cardDto)))
                .andDo(print())
                .andExpect(status().isUnsupportedMediaType());
    }

    @Test
    public void shouldReturnUnsupportedMediaTypeInPut() throws Exception {
        mockMvc.perform(put(ENDPOINT).contentType(MediaType.APPLICATION_XML)
                .content(toJson(cardDto)))
                .andDo(print())
                .andExpect(status().isUnsupportedMediaType());
    }

    @Test
    public void shouldReturnBadRequestWithEmptyBodyInPost() throws Exception {
        mockMvc.perform(post(ENDPOINT).contentType(MediaType.APPLICATION_JSON)
                .content(""))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnBadRequestWithEmptyBodyInPut() throws Exception {
        mockMvc.perform(put(ENDPOINT).contentType(MediaType.APPLICATION_JSON)
                .content(""))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnNotFoundWhileDeleting() throws Exception {
        mockMvc.perform(delete(ENDPOINT + "/{id}", UUID.randomUUID()))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnUpdatedCardDto() throws Exception {
        UUID id = UUID.fromString("9682140b-9d3e-44bb-a3bc-b398dd20c474");
        cardDto.setCvv("321");
        cardDto.setNumber("987654321");
        cardDto.setId(id);

        mockMvc.perform(put(ENDPOINT).contentType(MediaType.APPLICATION_JSON)
                .content(toJson(cardDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.cvv").value("321"))
                .andExpect(jsonPath("$.number").value("987654321"))
                .andExpect(jsonPath("$.owner").value("owner"));
    }

    @Test
    public void shouldReturnAllCustomersCards() throws Exception {
        UUID customerId = UUID.fromString("998a0dfe-ac53-11ec-b909-0242ac120002");

        String endpoint = "/api/customers";
        mockMvc.perform(get(endpoint + "/{customerId}/cards", customerId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].owner").value("owner"))
                .andExpect(jsonPath("$[0].cvv").value("123"))
                .andExpect(jsonPath("$[0].number").value("12345678"));
    }

}
