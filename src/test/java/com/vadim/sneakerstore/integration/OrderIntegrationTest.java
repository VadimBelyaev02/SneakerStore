package com.vadim.sneakerstore.integration;

import com.vadim.sneakerstore.dto.CommentDto;
import com.vadim.sneakerstore.dto.OrderDto;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application.properties")
@AutoConfigureMockMvc
@Transactional
public class OrderIntegrationTest {

    private final String ENDPOINT = "/api/orders";

    @Autowired
    private WebApplicationContext applicationContext;

    private MockMvc mockMvc;

    private OrderDto orderDto;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.applicationContext).build();
    }

    @BeforeEach
    public void init() {
//        this.orderDto = OrderDto.builder()
//                .id(UUID.fromString("3b410870-2c8a-4fd4-8377-89514c4bc05d"))
//                .productId(UUID.fromString("9b410870-2c8a-4fd4-8377-89514c4bc05d"))

            //    .customerId(UUID.fromString("998a0dfe-ac53-11ec-b909-0242ac120002"))
//                .message()
//                .customer("customer")
//                .build()
    }
}
