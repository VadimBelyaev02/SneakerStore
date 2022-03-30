package com.vadim.sneakerstore.integration;

import com.vadim.sneakerstore.dto.CommentDto;
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

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureMockMvc
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
                .id(UUID.fromString("9b410870-2c8a-4fd4-8377-89514c4bc05d"))
                //.date(Local)
                .build();
    }
}
