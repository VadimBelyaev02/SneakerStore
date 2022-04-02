package com.vadim.sneakerstore.integration;


import com.vadim.sneakerstore.dto.SizeDto;
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

import java.util.UUID;

import static com.vadim.sneakerstore.utils.JsonParser.toJson;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application.properties")
@AutoConfigureMockMvc
@Transactional
public class SizeIntegrationTest {

    private final String ENDPOINT = "/api/sizes";

    @Autowired
    private WebApplicationContext applicationContext;

    private SizeDto sizeDto;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.applicationContext).build();
    }

    @BeforeEach
    public void init() {
        sizeDto = SizeDto.builder()
                .id(UUID.fromString("7b410870-2c8a-4fd4-8377-89514c4bc05d"))
                .amount(6)
                .size(3)
                .productId(UUID.fromString("9b410870-2c8a-4fd4-8377-89514c4bc05d"))
                .build();
    }

    @Test
    public void shouldReturnNotFoundInfo() throws Exception {
        UUID id = UUID.randomUUID();
        String expectedMessage = "Size with id = " + id + " is not found";

        mockMvc.perform(get(ENDPOINT + "/{id}", id))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(expectedMessage));
    }


    @Test
    public void shouldReturnUnsupportedMediaTypeInPut() throws Exception {
        mockMvc.perform(put(ENDPOINT)
                        .contentType(MediaType.APPLICATION_XML)
                        .contentType(toJson(sizeDto)))
                .andDo(print())
                .andExpect(status().isUnsupportedMediaType());
    }

    @Test
    public void shouldReturnUnsupportedMediaTypeInPost() throws Exception {
        mockMvc.perform(post(ENDPOINT)
                .contentType(MediaType.APPLICATION_XML)
                .content(toJson(sizeDto)))
                .andDo(print())
                .andExpect(status().isUnsupportedMediaType());
    }

    @Test
    public void shouldReturnNotFoundInPut() throws Exception {
        String expectedMessage = "Size with id = " + sizeDto.getId() + " is not found";

        mockMvc.perform(put(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(sizeDto)))
                .andDo(print())
                .andExpect(status().isNotFound())
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
    public void shouldReturnBadRequestWithNullField() throws Exception {
        sizeDto.setSize(null);

        mockMvc.perform(post(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(sizeDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnConflictWithExistedSizeInPost() throws Exception {
        UUID id = UUID.fromString("9b410870-2c8a-4fd4-8377-89514c4bc05d");
        sizeDto.setId(id);
        String expectedMessage = "Size with id = " + id + " already exists";

        mockMvc.perform(post(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(sizeDto)))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(expectedMessage));
    }

    @Test
    public void shouldReturnSizeById() throws Exception {
        mockMvc.perform(get(ENDPOINT + "/{id}", "9b410870-2c8a-4fd4-8377-89514c4bc05d"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value(5))
                .andExpect(jsonPath("$.size").value(3));
    }

    @Test
    public void shouldReturnAllSizeDtos() throws Exception {
        mockMvc.perform(get(ENDPOINT))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnUpdatedSizeDto() throws Exception {
        sizeDto.setAmount(6);
        sizeDto.setId(UUID.fromString("9b410870-2c8a-4fd4-8377-89514c4bc05d"));

        mockMvc.perform(put(ENDPOINT).contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(sizeDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size").value(3))
                .andExpect(jsonPath("$.amount").value(6));
    }

    @Test
    public void shouldReturnInfoThatSizeIsNotFoundWhileUpdating() throws Exception {
        UUID id = UUID.randomUUID();
        String expectedMessage = "Size with id = " + id + " is not found";
        sizeDto.setId(id);

        mockMvc.perform(put(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(sizeDto)))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(expectedMessage));
    }

    @Test
    public void shouldReturnInfoThatSizeIsNotFoundWhileDeleting() throws Exception {
        UUID id = UUID.randomUUID();
        String expectedMessage = "Size with id = " + id + " is not found";

        mockMvc.perform(delete(ENDPOINT + "/{id}", id))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(expectedMessage));
    }

    @Test
    public void shouldDeleteSizeById() throws Exception {
        String id = "9b410870-2c8a-4fd4-8377-89514c4bc05d";
        mockMvc.perform(delete(ENDPOINT + "/{id}", id))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}
