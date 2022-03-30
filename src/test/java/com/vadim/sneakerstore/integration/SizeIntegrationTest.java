package com.vadim.sneakerstore.integration;


import com.vadim.sneakerstore.dto.SizeDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.web.SpringBootMockServletContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import java.util.UUID;

import static com.vadim.sneakerstore.utils.JsonParser.toJson;
import static com.vadim.sneakerstore.utils.JsonParser.toObject;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

//    @Test
//    public void givenServletContext_whenInitialize_thenContextExists() {
//        ServletContext servletContext = applicationContext.getServletContext();
//
//        assertNotNull(servletContext);
//        assertTrue(servletContext instanceof SpringBootMockServletContext);
//        assertNotNull(applicationContext.getBean("sizeController"));
//    }

    @BeforeEach
    public void init() {
        sizeDto = SizeDto.builder()
                .id(UUID.fromString("9b410870-2c8a-4fd4-8377-89514c4bc05d"))
                .amount(5)
                .size(3)
                .productId(UUID.fromString("9b410870-2c8a-4fd4-8377-89514c4bc05d"))
                .build();
    }

    @Test
    public void shouldReturnNotFoundInfo() throws Exception {
        mockMvc.perform(get(ENDPOINT + sizeDto.getId()))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnSavedSizeDto() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post(ENDPOINT).contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(sizeDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.amount").value(5))
                .andExpect(jsonPath("$.size").value(3))
                .andReturn();

        String returned = mvcResult.getResponse().getContentAsString();
        SizeDto returnedSizeDto = toObject(returned, SizeDto.class);
        returnedSizeDto.setId(sizeDto.getId());
    }

    @Test
    public void shouldReturnMessageThatSizeAlreadyExists() throws Exception {
        mockMvc.perform(post(ENDPOINT).contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(sizeDto)))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        // may also the message itself
    }

    @Test
    public void shouldReturnSizeById() throws Exception {
        mockMvc.perform(get(ENDPOINT + sizeDto.getId().toString()))
                      //  .contentType(MediaType.APPLICATION_JSON))
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
        SizeDto newSizeDto = SizeDto.builder()
                .size(sizeDto.getSize())
                .productId(sizeDto.getProductId())
                .amount(sizeDto.getAmount())
                .build();
        newSizeDto.setId(UUID.randomUUID());
        mockMvc.perform(put(ENDPOINT).contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(newSizeDto)))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnInfoThatSizeIsNotFoundWhileDeleting() throws Exception {
        SizeDto newSizeDto = SizeDto.builder()
                .size(sizeDto.getSize())
                .productId(sizeDto.getProductId())
                .amount(sizeDto.getAmount())
                .build();
        newSizeDto.setId(UUID.randomUUID());
        mockMvc.perform(delete(ENDPOINT + newSizeDto.getId()))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void shouldDeleteSizeById() throws Exception {
        mockMvc.perform(delete(ENDPOINT + sizeDto.getId()))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void shouldReturnInfoThatSizeNotFoundWhileDeleting() throws Exception {
        mockMvc.perform(delete(ENDPOINT + sizeDto.getId()))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }


}
