package com.vadim.sneakerstore.integration;


import com.vadim.sneakerstore.dto.SizeDto;
import com.vadim.sneakerstore.entity.Size;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class SizeIntegrationTest {

    private final String ENDPOINT = "/api/products";

    @Autowired
    private WebApplicationContext applicationContext;

    private SizeDto sizeDto;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.applicationContext).build();
    }

    @Test
    public void givenServletContext_whenInitialize_thenContextExists() {
        ServletContext servletContext = applicationContext.getServletContext();

        assertNotNull(servletContext);
        assertTrue(servletContext instanceof MockServletContext);
        assertNotNull(applicationContext.getBean("parallelogramController"));
    }

    @BeforeEach
    public void init() {
        SizeDto sizeDto = SizeDto.builder()
                .id(UUID.fromString("09f0ee11-e4d6-47d3-a0af-28dff09b08bc"))
                .amount(5)
                .size(3)
                .build();
    }

    @Test
    public void shouldReturnNotFoundInfo() throws Exception {
        mockMvc.perform(get(ENDPOINT + sizeDto.getId()))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnSavedSizeDto() throws Exception {
        mockMvc.perform(post(ENDPOINT))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.amount").value(5))
                .andExpect(jsonPath("$.size").value(3));
    }

    @Test
    public void shouldReturnMessageThatSizeAlreadyExists() throws Exception {
        mockMvc.perform(post(ENDPOINT))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
                // may also the message itself
    }

    @Test
    public void shouldReturnSizeById() throws Exception {
        mockMvc.perform(get(ENDPOINT + sizeDto.getId()))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value(5))
                .andExpect(jsonPath("$.size").value(3));
    }


}
