package be.kdg.client.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("classpath:application-test.properties")
public class OrderCrudRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * Test to check if the client is authenticated
     * post-condition: the client get an error message
     */
    @Test
    public void whenICreateAnOrderAndIAmNotAuthenticatedIWantAnError() throws Exception {
        String requestJson = """
                {
                  "specialInstructions": "Delivery to the door",
                  "productDtoList": [
                    {
                      "productNumber": "5a2e7f53-7e9d-4ea3-8921-08cfc2f8e3d2",
                      "quantity": 1000
                    },
                    {
                      "productNumber": "9fcb0e7e-6a38-432c-9e9a-864e8e4c15a1",
                      "quantity": 5000
                    }
                  ]
                }""";

        mockMvc.perform(
                        post("/api/orders/create")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestJson))
                .andExpect(status().isForbidden());
    }


    /**
     * Test to check if the client don't give any products in the order
     * post-condition: the client get an error message "no products in order"
     */
    @Test
    public void whenICreateAnOrderWithoutProductsIWantAnError() throws Exception {
        String requestJson = """
                {
                  "specialInstructions": "Delivery to the door",
                  "productDtoList": []
                }""";

        mockMvc.perform(
                post("/api/orders/create").with(jwt().authorities(List.of(new SimpleGrantedAuthority("user")))
                                .jwt(jwt -> jwt
                                        .claim("sub", "3dc99fa5-90aa-48e4-b76d-b45bc65c6d80")
                                        .claim(StandardClaimNames.GIVEN_NAME, "Maxim")
                                        .claim(StandardClaimNames.FAMILY_NAME , "Hansen")
                                        .claim(StandardClaimNames.EMAIL, "user@client.be")))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isNotFound())
                .andExpect(content().string("No products in order"));
    }

    /**
     * Test to check if the client give a product that is not present in the database
     * post-condition: the client get an error message "Product with id 5b2e7f53-7e9d-4ea3-8921-08cfc2f8e3d2 not found!"
     */
    @Test
    public void whenICreateAnOrderWithUnknownProductIdIWantAnError() throws Exception {
        String requestJson = """
                {
                  "specialInstructions": "Delivery to the door",
                  "productDtoList": [
                    {
                      "productNumber": "5b2e7f53-7e9d-4ea3-8921-08cfc2f8e3d2",
                      "quantity": 1000
                    }
                  ]
                }""";

        mockMvc.perform(
                post("/api/orders/create")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("user")))
                                .jwt(jwt -> jwt
                                        .claim("sub", "3dc99fa5-90aa-48e4-b76d-b45bc65c6d80")
                                        .claim(StandardClaimNames.GIVEN_NAME, "Maxim")
                                        .claim(StandardClaimNames.FAMILY_NAME , "Hansen")
                                        .claim(StandardClaimNames.EMAIL, "user@client.be")))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Product with id 5b2e7f53-7e9d-4ea3-8921-08cfc2f8e3d2 not found!"));
    }

    /**
     * Test if the order is created correctly
     * post-condition: the client get a message "Order created"
     */
    @Test
    public void whenICreateAnOrderIWantACorrectRegistrationByTheSystem() throws Exception {
        String requestJson = """
                {
                  "specialInstructions": "Delivery to the door",
                  "productDtoList": [
                    {
                      "productNumber": "6f8d5a2b-aa4e-4b71-a33d-182ccda1d3d0",
                      "quantity": 1000
                    },
                    {
                      "productNumber": "f28e9a06-0d2c-4a3b-b12d-7a1c1f3ec1e2",
                      "quantity": 5000
                    }
                  ]
                }""";

        mockMvc.perform(
                post("/api/orders/create")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("user")))
                        .jwt(jwt -> jwt
                                .claim("sub", "3dc99fa5-90aa-48e4-b76d-b45bc65c6d80")
                                .claim(StandardClaimNames.GIVEN_NAME, "Maxim")
                                .claim(StandardClaimNames.FAMILY_NAME , "Hansen")
                                .claim(StandardClaimNames.EMAIL, "user@client.be")))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().string("Order created"));
    }

    /**
     * Test if the order is never placed or not found
     * post-condition: the client get a message "Order not found exception!"
     */
    @Test
    public void whenIConfirmAnOrderThatIsNotPlacedIWantAnError() throws Exception {
        mockMvc.perform(
                post("/api/orders/confirm")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("user")))
                                .jwt(jwt -> jwt
                                        .claim("sub", "3dc99fa5-90aa-48e4-b76d-b45bc65c6d80")
                                        .claim(StandardClaimNames.GIVEN_NAME, "Maxim")
                                        .claim(StandardClaimNames.FAMILY_NAME, "Hansen")
                                        .claim(StandardClaimNames.EMAIL, "user@client.be")))
                        .param("orderId", "5a2e7f53-7e9d-4ea3-8921-08cfc2f8e3d2"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Order not found exception!"));
    }

    /**
     * Test if the order is already confirmed
     * post-condition: the client get a message "Order is already confirmed"
     */
    @Test
    public void whenIConfirmAnOrderThatIsAlreadyConfirmedIWantAnError() throws Exception {
        mockMvc.perform(
                post("/api/orders/confirm")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("user")))
                                .jwt(jwt -> jwt
                                        .claim("sub", "3dc99fa5-90aa-48e4-b76d-b45bc65c6d80")
                                        .claim(StandardClaimNames.GIVEN_NAME, "Maxim")
                                        .claim(StandardClaimNames.FAMILY_NAME, "Hansen")
                                        .claim(StandardClaimNames.EMAIL, "user@client.be")))
                        .param("orderId", "550e8400-e29b-41d4-a716-446655440000"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Order is already confirmed"));
    }

    /**
     * Test if the order is confirmed correctly
     * post-condition: the client get a message "Order confirmed"
     */
    @Test
    @Transactional
    public void whenIConfirmMyOrderIWantToDoItSuccessfully() throws Exception {
        mockMvc.perform(
                post("/api/orders/confirm")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("user")))
                                .jwt(jwt -> jwt
                                        .claim("sub", "3dc99fa5-90aa-48e4-b76d-b45bc65c6d80")
                                        .claim(StandardClaimNames.GIVEN_NAME, "Maxim")
                                        .claim(StandardClaimNames.FAMILY_NAME, "Hansen")
                                        .claim(StandardClaimNames.EMAIL, "user@client.be")))
                        .param("orderId", "d1f6a9d2-7df5-4a78-8e1d-d20e8125e8a2"))
                .andExpect(status().isOk())
                .andExpect(content().string("Order confirmed"));
    }

    /**
     * Test if the order is already canceled
     * post-condition: the client get a message "Order can't be cancelled! The current state is: ORDER_CANCELLED"
     */
    @Test
    public void whenICancelMyOrderAndItIsAlreadyCanceledIWantToGetAnError() throws Exception {
        mockMvc.perform(
                delete("/api/orders/cancel")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("user")))
                                .jwt(jwt -> jwt
                                        .claim("sub", "3dc99fa5-90aa-48e4-b76d-b45bc65c6d80")
                                        .claim(StandardClaimNames.GIVEN_NAME, "Maxim")
                                        .claim(StandardClaimNames.FAMILY_NAME, "Hansen")
                                        .claim(StandardClaimNames.EMAIL, "user@client.be")))
                        .param("orderId", "90e8d6c6-3d03-46b3-b15f-6aaad2f9478a"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Order can't be cancelled! The current state is: ORDER_CANCELLED"));
    }

    /**
     * Test if the order can be canceled when it is already confirmed
     * post-condition: the client get a message "Order can't be cancelled anymore! The current state is: ORDER_CONFIRMED"
     */
    @Test
    public void whenICancelMyOrderAndMyOrderIsAlreadyConfirmedIWantToGetAnError() throws Exception {
        mockMvc.perform(
                delete("/api/orders/cancel")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("user")))
                                .jwt(jwt -> jwt
                                        .claim("sub", "3dc99fa5-90aa-48e4-b76d-b45bc65c6d80")
                                        .claim(StandardClaimNames.GIVEN_NAME, "Maxim")
                                        .claim(StandardClaimNames.FAMILY_NAME, "Hansen")
                                        .claim(StandardClaimNames.EMAIL, "user@client.be")))
                                .param("orderId", "550e8400-e29b-41d4-a716-446655440000"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Order can't be cancelled! The current state is: ORDER_CONFIRMED"));
    }

    /**
     * Test if the order is correctly cancelled
     * post-condition: the client get a message "Order cancelled"
     */
    @Test
    public void whenICancelMyOrderIWantToDoItSuccessfully() throws Exception {
        mockMvc.perform(
                delete("/api/orders/cancel")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("user")))
                                .jwt(jwt -> jwt
                                        .claim("sub", "3dc99fa5-90aa-48e4-b76d-b45bc65c6d80")
                                        .claim(StandardClaimNames.GIVEN_NAME, "Maxim")
                                        .claim(StandardClaimNames.FAMILY_NAME, "Hansen")
                                        .claim(StandardClaimNames.EMAIL, "user@client.be")))
                                .param("orderId", "8b8691e7-0287-4f1d-bbaf-1e10be917e96"))
                .andExpect(status().isOk())
                .andExpect(content().string("Order cancelled"));
    }
}
