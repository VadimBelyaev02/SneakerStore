package com.vadim.sneakerstore.integration;

import com.vadim.sneakerstore.dto.CommentDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.vadim.sneakerstore.dto.PictureDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

import static com.vadim.sneakerstore.utils.JsonParser.toJson;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application.properties")
@AutoConfigureMockMvc
@Transactional
public class CommentIntegrationTest {

    private final String ENDPOINT = "/api/comments";

    @Autowired
    private WebApplicationContext applicationContext;

    private MockMvc mockMvc;

    private CommentDto commentDto;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.applicationContext).build();
    }

    @BeforeEach
    public void init() {
        commentDto = CommentDto.builder()
                .id(UUID.fromString("998a0dfe-ac53-11ec-b909-0242ac120002"))
                .date(LocalDate.of(2002, 1, 1))
                .customerId(UUID.fromString("998a0dfe-ac53-11ec-b909-0242ac120002"))
                .message("Norm")
                .rate(5)
                .customer("username")
                .productId(UUID.fromString("9b410870-2c8a-4fd4-8377-89514c4bc05d"))
                .build();
    }

    @Test
    public void shouldReturnCommentById() throws Exception {
        String id = "9682140b-9d3e-44bb-a3bc-b398dd20c474";
        mockMvc.perform(get(ENDPOINT + "/{id}", id))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Norm"))
                .andExpect(jsonPath("$.rate").value(5))
                .andExpect(jsonPath("$.customer").value("username"));
    }

    @Test
    public void shouldReturnInfoThatCommentIsNotFound() throws Exception {
        mockMvc.perform(get(ENDPOINT + "/{id}", UUID.randomUUID()))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void shouldReturnBadRequestWithIncorrectId() throws Exception {
        mockMvc.perform(get(ENDPOINT + "/{id}", toJson(commentDto)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnAllComments() throws Exception {
        mockMvc.perform(get(ENDPOINT))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void shouldReturnInfoThatCommentAlreadyExists() throws Exception {
        commentDto.setId(UUID.fromString("9682140b-9d3e-44bb-a3bc-b398dd20c474"));
        mockMvc.perform(post(ENDPOINT).contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(commentDto)))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    public void shouldReturnSavedComment() throws Exception {
        mockMvc.perform(post(ENDPOINT).contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(commentDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.rate").value(5))
                .andExpect(jsonPath("$.message").value("Norm"));
    }

    @Test
    public void shouldReturnUpdatedComment() throws Exception {
        commentDto.setRate(4);
        commentDto.setMessage("Ne norm");
        commentDto.setId(UUID.fromString("9682140b-9d3e-44bb-a3bc-b398dd20c474"));

        mockMvc.perform(put(ENDPOINT).contentType(MediaType.APPLICATION_JSON)
                .content(toJson(commentDto)))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rate").value(4))
                .andExpect(jsonPath("$.message").value("Ne norm"));
    }

    @Test
    public void shouldReturnUnsupportedMediaTypeWhileUpdating() throws Exception {
        mockMvc.perform(put(ENDPOINT)
                        .contentType(MediaType.APPLICATION_XML)
                        .content(toJson(commentDto)))
                .andDo(print())
                .andExpect(status().isUnsupportedMediaType());
    }

    @Test
    public void shouldReturnMethodNotAllowedWithEmptyIdInDelete() throws Exception {
        mockMvc.perform(delete(ENDPOINT + "/{id}", ""))
                .andDo(print())
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void shouldReturnBadRequestWithIncorrectIdInDelete() throws Exception {
        mockMvc.perform(delete(ENDPOINT + "/{id}", toJson(commentDto)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnInfoThatCommentNotFoundWhileDeleting() throws Exception {
        mockMvc.perform(delete(ENDPOINT + "/{id}", "notId"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldDeleteCommentByIdAndReturnNoContent() throws Exception {
        UUID id = UUID.fromString("9682140b-9d3e-44bb-a3bc-b398dd20c474");
        mockMvc.perform(delete(ENDPOINT + "/{id}", id))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldReturnAllCommentsByProductId() throws Exception {
        UUID productId = UUID.fromString("9b410870-2c8a-4fd4-8377-89514c4bc05d");

        String endpoint = "/api/products";
        mockMvc.perform(get(endpoint + "/{productId}/comments", productId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].customer").value("username"))
                .andExpect(jsonPath("$[0].message").value("Norm"))
                .andExpect(jsonPath("$[0].customerId").value("998a0dfe-ac53-11ec-b909-0242ac120002"));
    }
}

