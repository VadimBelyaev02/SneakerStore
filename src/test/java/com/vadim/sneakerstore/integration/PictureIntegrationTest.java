package com.vadim.sneakerstore.integration;

import com.vadim.sneakerstore.dto.PictureDto;
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

@SpringBootTest
@AutoConfigureMockMvc
public class PictureIntegrationTest {

    private final String ENDPOINT = "/api/pictures";

    @Autowired
    private WebApplicationContext applicationContext;

    private MockMvc mockMvc;

    private PictureDto pictureDto;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.applicationContext).build();
    }

    @BeforeEach
    public void init() {
        this.pictureDto = PictureDto.builder()
                .link("link")
                .productId(UUID.fromString("9b410870-2c8a-4fd4-8377-89514c4bc05d"))
                .build();
    }

    @Test
    public void shouldReturnPictureDtoByLink() throws Exception {
        mockMvc.perform(get(ENDPOINT + "/{id}", "link"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.link").value("link"))
                .andExpect(jsonPath("$.productId").value("9b410870-2c8a-4fd4-8377-89514c4bc05d"));
    }

    @Test
    public void shouldReturnInfoThatPictureIsNotFound() throws Exception {
        mockMvc.perform(get(ENDPOINT + "/{id}", "1b410870-2c8a-4fd4-8377-89514c4bc05d"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void shouldReturnInfoThatMethodIsNotAllowed() throws Exception {
        mockMvc.perform(get(ENDPOINT + "/{id}", "justString"))
                .andDo(print())
                .andExpect(status().isMethodNotAllowed())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void shouldReturnAllPictures() throws Exception {
        mockMvc.perform(get(ENDPOINT))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void shouldReturnSavedPictureDto() throws Exception {
        mockMvc.perform(post(ENDPOINT).contentType(MediaType.APPLICATION_JSON)
                .content(toJson(pictureDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.link").value("link"));
    }

    @Test
    public void shouldReturnUpdatedPictureDto() throws Exception {
        mockMvc.perform(put(ENDPOINT).contentType(MediaType.APPLICATION_JSON)
                .content(toJson(pictureDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.link").value("link"));
    }

    @Test
    public void shouldReturnMethodNotAllowedWithIncorrectIdWhileDeleting() throws Exception {
        mockMvc.perform(delete(ENDPOINT + "/{id}", "badValue"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void shouldReturnUnsupportedMediaType() throws Exception {
        mockMvc.perform(post(ENDPOINT).contentType(MediaType.APPLICATION_XML)
                .content(toJson(pictureDto)))
                .andDo(print())
                .andExpect(status().isUnsupportedMediaType());
    }




}

