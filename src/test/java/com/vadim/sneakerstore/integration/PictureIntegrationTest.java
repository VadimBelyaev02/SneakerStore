package com.vadim.sneakerstore.integration;

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
                .link("link2")
                .productId(UUID.fromString("836b1a57-7e14-401e-b618-0024c694e8b2"))
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
        UUID id = UUID.randomUUID();
        String expectedMessage = "Picture with id = " + id + " is not found";

        mockMvc.perform(get(ENDPOINT + "/{id}", id))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(expectedMessage));
    }

    @Test
    public void shouldReturnBadRequestWithIncorrectId() throws Exception {
        mockMvc.perform(get(ENDPOINT + "/{id}", toJson(pictureDto)))
                .andDo(print())
                .andExpect(status().isBadRequest());
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
        mockMvc.perform(post(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(pictureDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.link").value("link2"))
                .andExpect(jsonPath("$.productId").value("836b1a57-7e14-401e-b618-0024c694e8b2"));
    }

    @Test
    public void shouldReturnConflictWithExistedLinkInPost() throws Exception {
        pictureDto.setLink("link");
        String expectedMessage = "Picture with link = link already exists";

        mockMvc.perform(post(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(pictureDto)))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(expectedMessage));
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
    public void shouldReturnUnsupportedMediaTypeInPut() throws Exception {
        mockMvc.perform(post(ENDPOINT)
                        .contentType(MediaType.APPLICATION_XML)
                        .content(toJson(pictureDto)))
                .andDo(print())
                .andExpect(status().isUnsupportedMediaType());
    }

    @Test
    public void shouldReturnNotFoundInPut() throws Exception {
        //id
        String expectedMessage = "Picture with link = " + pictureDto.getLink() + " is not found";

        mockMvc.perform(put(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(pictureDto)))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(expectedMessage));
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
    public void shouldReturnUpdatedPictureDto() throws Exception {
        UUID productId = UUID.fromString("9b410870-2c8a-4fd4-8377-89514c4bc05d");
        pictureDto.setLink("link");
        pictureDto.setProductId(productId);

        mockMvc.perform(put(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(pictureDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.link").value("link"))
                .andExpect(jsonPath("$.productId").value(productId));
    }

    @Test
    public void shouldReturnNoContent() throws Exception {
        mockMvc.perform(delete(ENDPOINT + "/{id}", "link"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldReturnBadRequestWithIncorrectIdInDelete() throws Exception {
        mockMvc.perform(delete(ENDPOINT + "/{id}", toJson(pictureDto)))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnUnsupportedMediaTypeInPost() throws Exception {
        mockMvc.perform(post(ENDPOINT).contentType(MediaType.APPLICATION_XML)
                        .content(toJson(pictureDto)))
                .andDo(print())
                .andExpect(status().isUnsupportedMediaType());
    }


}

