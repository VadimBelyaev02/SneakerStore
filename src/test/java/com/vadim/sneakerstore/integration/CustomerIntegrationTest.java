package com.vadim.sneakerstore.integration;

import com.vadim.sneakerstore.dto.CustomerDto;
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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.vadim.sneakerstore.utils.JsonParser.toJson;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application.properties")
@AutoConfigureMockMvc
@Transactional
public class CustomerIntegrationTest {

    private final String ENDPOINT = "/api/customers";

    @Autowired
    private WebApplicationContext applicationContext;

    private MockMvc mockMvc;

    private CustomerDto customerDto;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.applicationContext).build();
    }

    @BeforeEach
    public void init() {
        customerDto = CustomerDto.builder()
                .id(UUID.fromString("898a0dfe-ac53-11ec-b909-0242ac120002"))
                .avatar("avatar")
                .email("email@gmail.com")
                .firstName("firstName")
                .lastName("lastName")
                .phone("phone")
                .role("USER")
                .build();
    }

    @Test
    public void shouldReturnCustomerDtoById() throws Exception {
        UUID id = UUID.fromString("998a0dfe-ac53-11ec-b909-0242ac120002");
        mockMvc.perform(get(ENDPOINT + "/{id}", id))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("dfg"))
                .andExpect(jsonPath("$.lastName").value("dfg"))
                .andExpect(jsonPath("$.phone").value("324"))
                .andExpect(jsonPath("$.email").value("vadim@gmail.com"))
                .andExpect(jsonPath("$.avatar").value("ert"));
    }

//    @Test
//    public void shouldReturnBadRequestWithIncorrectId() throws Exception {
//        mockMvc.perform(get(ENDPOINT + "/{id}", "wrong"))
//                .andDo(print())
//                .andExpect(status().isBadRequest());
//    }

    @Test
    public void shouldReturnNotFoundInfo() throws Exception {
        mockMvc.perform(get(ENDPOINT + "/{id}", UUID.randomUUID()))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

//    @Test
//    public void shouldReturnBadRequestWithEmptyId() throws Exception {
//        mockMvc.perform(get(ENDPOINT + "/{id}", ""))
//                .andDo(print())
//                .andExpect(status().isBadRequest());
//    }

    @Test
    public void shouldReturnAllCustomers() throws Exception {
        mockMvc.perform(get(ENDPOINT))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

//    @Test
//    public void shouldReturnSavedCustomer() throws Exception {
//        mockMvc.perform(post(ENDPOINT)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(toJson(customerDto)))
//                .andDo(print())
//                .andExpect(status().isCreated())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.email").value("email@gmail.com"))
//                .andExpect(jsonPath("$.firstName").value("firstName"))
//                .andExpect(jsonPath("$.lastName").value("lastName"))
//                .andExpect(jsonPath("$.phone").value("phone"))
//                .andExpect(jsonPath("$.role").value("USER"));
//    }

//    @Test
//    public void shouldReturnAlreadyExistsWithWrongPhone() throws Exception {
//        customerDto.setPhone("324");
//        String expectedMessage = "Customer with phone = 324 already exists";
//
//        mockMvc.perform(post(ENDPOINT)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(toJson(customerDto)))
//                .andDo(print())
//                .andExpect(status().isConflict())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.message").value(expectedMessage));
//    }

//    @Test
//    public void shouldReturnAlreadyExistsWithWrongEmail() throws Exception {
//        customerDto.setEmail("vadim@gmail.com");
//        String expectedMessage = "Customer with email = vadim@gmail.com already exists";
//
//        mockMvc.perform(post(ENDPOINT)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(toJson(customerDto)))
//                .andDo(print())
//                .andExpect(status().isConflict())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.message").value(expectedMessage));
//    }
//
//    @Test
//    public void shouldReturnAlreadyExistsWithWrongEmailAndPhone() throws Exception {
//        customerDto.setPhone("324");
//        customerDto.setEmail("vadim@gmail.com");
//        String expectedMessage = "Customer with phone = 324 and email = vadim@gmail.com already exists";
//
//        mockMvc.perform(post(ENDPOINT)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(toJson(customerDto)))
//                .andDo(print())
//                .andExpect(status().isConflict())
//                .andExpect(jsonPath("$.message").value(expectedMessage));
//    }

    @Test
    public void shouldReturnUnsupportedMediaTypeInPost() throws Exception {
        mockMvc.perform(post(ENDPOINT)
                        .contentType(MediaType.APPLICATION_XML)
                        .content(toJson(customerDto)))
                .andDo(print())
                .andExpect(status().isUnsupportedMediaType());
    }

    @Test
    public void shouldReturnBadRequestWithEmptyBodyInPost() throws Exception {
        mockMvc.perform(post(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .contentType(""))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnUpdatedCustomer() throws Exception {
        customerDto.setId(UUID.fromString("998a0dfe-ac53-11ec-b909-0242ac120002"));
        customerDto.setAvatar("newava");
        customerDto.setFirstName("newFirstName");
        customerDto.setLastName("newLastName");

        mockMvc.perform(put(ENDPOINT).contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(customerDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.avatar").value("newava"))
                .andExpect(jsonPath("$.firstName").value("newFirstName"))
                .andExpect(jsonPath("$.lastName").value("newLastName"));
    }

    @Test
    public void shouldReturnUnsupportedMediaTypeInPut() throws Exception {
        mockMvc.perform(put(ENDPOINT)
                        .contentType(MediaType.APPLICATION_XML)
                        .contentType(toJson(customerDto)))
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
    public void shouldReturnNotFoundInfoWhileUpdating() throws Exception {
        customerDto.setId(UUID.randomUUID());

        mockMvc.perform(put(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(customerDto)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnBadRequestWithEmptyIdInDelete() throws Exception {
        mockMvc.perform(delete(ENDPOINT + "/{id}", ""))
                .andDo(print())
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void shouldReturnNotFoundWhileDeleting() throws Exception {
        mockMvc.perform(delete(ENDPOINT + "/{id}", UUID.randomUUID()))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnNoContent() throws Exception {
        UUID id = UUID.fromString("998a0dfe-ac53-11ec-b909-0242ac120002");

        mockMvc.perform(delete(ENDPOINT + "/{id}", id))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldReturnBadRequestWithEmptyField() throws Exception {
        customerDto.setPhone(null);

        mockMvc.perform(post(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(customerDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void shouldUpdateFavoritesByCustomerIdAndProductId() throws Exception {
        UUID customerId = UUID.fromString("998a0dfe-ac53-11ec-b909-0242ac120002");
        List<UUID> productIds = Stream.of(UUID.fromString("9b410870-2c8a-4fd4-8377-89514c4bc05d"))
                .collect(Collectors.toList());

        List<String> fuckingProductsIds = Stream.of("9b410870-2c8a-4fd4-8377-89514c4bc05d")
                        .collect(Collectors.toList());

        customerDto.setId(customerId);
        customerDto.setFavoritesIds(productIds);

        mockMvc.perform(put(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(customerDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.favoritesIds").value(fuckingProductsIds));
    }

    @Test
    public void shouldUpdateCustomersAddresses() throws Exception {
        UUID customerId = UUID.fromString("998a0dfe-ac53-11ec-b909-0242ac120002");

        List<String> addressesIds = Stream.of("89039889-99a8-48e1-a570-e578580fb6cb")
                .collect(Collectors.toList());

        customerDto.setId(customerId);
        customerDto.setAddressesIds(addressesIds.stream()
                .map(UUID::fromString)
                .collect(Collectors.toList()));

        mockMvc.perform(put(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(customerDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.addressesIds").value(addressesIds));
    }

    @Test
    public void shouldUpdateCustomersCards() throws Exception {
        UUID customerId = UUID.fromString("998a0dfe-ac53-11ec-b909-0242ac120002");

        List<String> cardsIds = Stream.of("9682140b-9d3e-44bb-a3bc-b398dd20c474")
                .collect(Collectors.toList());

        customerDto.setId(customerId);
        customerDto.setCardsIds(cardsIds.stream()
                .map(UUID::fromString)
                .collect(Collectors.toList()));

        mockMvc.perform(put(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(customerDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.cardsIds").value(cardsIds));
    }

    @Test
    public void shouldUpdateCustomersInCart() throws Exception {
        UUID customerId = UUID.fromString("998a0dfe-ac53-11ec-b909-0242ac120002");
        List<String> inCartIds = Stream.of("9b410870-2c8a-4fd4-8377-89514c4bc05d")
                .collect(Collectors.toList());

        customerDto.setId(customerId);
        customerDto.setInCartIds(inCartIds.stream()
                .map(UUID::fromString)
                .collect(Collectors.toList()));

        mockMvc.perform(put(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(customerDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.inCartIds").value(inCartIds));
    }
}
