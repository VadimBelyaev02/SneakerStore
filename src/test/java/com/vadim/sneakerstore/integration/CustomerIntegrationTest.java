package com.vadim.sneakerstore.integration;

import com.vadim.sneakerstore.controller.CustomerController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerIntegrationTest {

    private final String ENDPOINT = "/api/customers";

    @Autowired
    private CustomerController customerController;

    @Autowired
    private MockMvc mvc;

    @Test
    public void contextLoads() throws Exception {
        assertThat(customerController).isNotNull();
    }

    @Test
    public void shouldReturnCustomerById() throws Exception {
        //  mvc.perform(get(ENDPOINT + "/"))
    }
}
