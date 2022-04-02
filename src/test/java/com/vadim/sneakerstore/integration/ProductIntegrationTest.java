package com.vadim.sneakerstore.integration;

import com.vadim.sneakerstore.dto.ProductDto;
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

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductIntegrationTest {

    private final String ENDPOINT = "/api/products";

    @Autowired
    private WebApplicationContext applicationContext;

    private MockMvc mockMvc;

    private ProductDto productDto;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.applicationContext).build();
    }

    @BeforeEach
    public void init() {
        productDto = ProductDto.builder()
                .id(UUID.fromString("8b410870-2c8a-4fd4-8377-89514c4bc05d"))
                .brand("brand")
                .color("color")
                .destiny("destiny")
                .material("material")
                .name("name2")
                .season("season")
                .sex("sex")
                .description("description")
                .originCountry("originCountry")
                .price(BigDecimal.ONE)
                .averageRate(5D)
                .build();
    }

    @Test
    public void shouldReturnProductById() throws Exception {
        UUID id = UUID.fromString("836b1a57-7e14-401e-b618-0024c694e8b2");
        mockMvc.perform(get(ENDPOINT + "/{id}", id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("name"))
                .andExpect(jsonPath("$.price").value(BigDecimal.ONE))
                .andExpect(jsonPath("$.destiny").value("destiny"))
                .andExpect(jsonPath("$.color").value("color"))
                .andExpect(jsonPath("$.brand").value("brand"))
                .andExpect(jsonPath("$.description").value("description"));
    }

    @Test
    public void shouldReturnNotFoundInfo() throws Exception {
        UUID id = UUID.randomUUID();
        String expectedMessage = "Product with id = " + id + " is not found";

        mockMvc.perform(get(ENDPOINT + "/{id}", id))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(expectedMessage));
    }

    @Test
    public void shouldReturnUnsupportedMediaTypeInPost() throws Exception{
        mockMvc.perform(post(ENDPOINT)
                        .contentType(MediaType.APPLICATION_XML)
                .content(toJson(productDto)))
                .andDo(print())
                .andExpect(status().isUnsupportedMediaType());
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
    public void shouldReturnConflictWithExistedProductInPost() throws Exception {
        productDto.setName("name");
        String expectedMessage = "Product with name = name already exists";

        mockMvc.perform(post(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(productDto)))
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(expectedMessage));
    }

    @Test
    public void shouldReturnAllProductDtos() throws Exception {
        mockMvc.perform(get(ENDPOINT))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void shouldReturnBadRequestWithNullField() throws Exception {
        productDto.setName(null);

        mockMvc.perform(put(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(productDto)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnSavedProductDto() throws Exception {
        productDto.setName("newName");

        mockMvc.perform(post(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(productDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("newName"));
    }

    @Test
    public void shouldReturnNotFoundWhileUpdating() throws Exception {
        String expectedMessage = "Product with id = " + productDto.getId() + " is not found";

        mockMvc.perform(put(ENDPOINT).contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(productDto)))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(expectedMessage));
    }

    @Test
    public void shouldReturnUnsupportedMediaTypeInPut() throws Exception {
        mockMvc.perform(put(ENDPOINT)
                .contentType(MediaType.APPLICATION_XML)
                .content(toJson(productDto)))
                .andDo(print())
                .andExpect(status().isUnsupportedMediaType());
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
    public void shouldReturnBadRequestWithIncorrectIdInDelete() throws Exception {
        mockMvc.perform(delete(ENDPOINT + "/{id}", toJson(productDto)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnNotFoundWhileDeleting() throws Exception {
        UUID id = UUID.randomUUID();
        String expectedMessage = "Product with id = " + id + " is not found";

        mockMvc.perform(delete(ENDPOINT + "/{id}", id))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(expectedMessage));
    }

    @Test
    public void shouldReturnNoContent() throws Exception {
        String id = "836b1a57-7e14-401e-b618-0024c694e8b2";
        mockMvc.perform(delete(ENDPOINT + "/{id}", id))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}
