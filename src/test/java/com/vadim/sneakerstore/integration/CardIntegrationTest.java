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
                .id(UUID.fromString("9682140b-9d3e-44bb-a3bc-b398dd20c474"))
                .owner("owner")
                .number("123456789")
                .validityDate(LocalDate.of(2022, 1, 1))
                .cvv("123")
                .build();
    }

    @Test
    public void shouldReturnCardById() throws Exception {
        mockMvc.perform(get(ENDPOINT + "/{id}", cardDto.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.owner").value("owner"))
                .andExpect(jsonPath("$.number").value("123456789"))
                .andExpect(jsonPath("$.cvv").value("123"))
                .andExpect(jsonPath("$.id").value("9682140b-9d3e-44bb-a3bc-b398dd20c474"));
    }

    @Test
    public void shouldReturnNotFoundInfo() throws Exception {
        mockMvc.perform(get(ENDPOINT + "/{id}", UUID.randomUUID()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnAllCard()  throws Exception{
        mockMvc.perform(get(ENDPOINT))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnSavedCard() throws Exception {
        String oldNumber = cardDto.getNumber();
        UUID oldId = cardDto.getId();
        String oldCvv = cardDto.getCvv();
        cardDto.setCvv("321");
        cardDto.setNumber("987654321");

        mockMvc.perform(post(ENDPOINT).contentType(MediaType.APPLICATION_JSON)
                        .contentType(toJson(cardDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.cvv").value("321"))
                .andExpect(jsonPath("$.number").value("987654321"));

        cardDto.setNumber(oldNumber);
        cardDto.setCvv(oldCvv);
        cardDto.setId(oldId);
    }

    @Test
    public void shouldReturnInfoThatCardAlreadyExists() throws Exception {
        mockMvc.perform(post(ENDPOINT).contentType(MediaType.APPLICATION_JSON)
                .content(toJson(cardDto)))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    public void shouldReturnUnsupportedMediaType() throws Exception {
        mockMvc.perform(post(ENDPOINT).contentType(MediaType.APPLICATION_XML)
                .content(toJson(cardDto)))
                .andExpect(status().isUnsupportedMediaType());
    }

    @Test
    public void shouldReturnBadRequestWithIncorrectId() throws Exception {

    }

}
