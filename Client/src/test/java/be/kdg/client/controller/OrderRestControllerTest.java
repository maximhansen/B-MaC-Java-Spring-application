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

import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("classpath:application-test.properties")
public class OrderRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * If I ask all my orders when I'm authenticated, I want all my orders.
     * post-condition: I get a list of all my orders.
     */
    @Test
    public void whenIAskAllMyOrdersIWantAllMyOrders() throws Exception {
        String resultJson = """
                [
                  {
                    "orderId":"d7f5c51d-ff67-4b88-a4a5-cc1627aa7469",
                    "totalPrice":5000.0,
                    "orderDate":"2023-01-01T00:00:00",
                    "specialInstructions":null,
                    "lastOrderState":"ORDER_CONFIRMED",
                    "orderLines":
                    [
                     {
                       "id":5,
                       "quantity":1,
                       "productNumber":"6f8d5a2b-aa4e-4b71-a33d-182ccda1d3d0"
                     }
                    ]
                  }
                ]
                        """;

        mockMvc.perform(
                        get("/api/orders/all").with(jwt().authorities(List.of(new SimpleGrantedAuthority("user")))
                                        .jwt(jwt -> jwt
                                                .claim("sub", "b03a604f-112d-4b67-a0a0-5aa228d8877f")
                                                .claim(StandardClaimNames.GIVEN_NAME, "Maxim")
                                                .claim(StandardClaimNames.FAMILY_NAME, "Hansen")
                                                .claim(StandardClaimNames.EMAIL, "user@client.be")))
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(resultJson));
    }

    /**
     * If I ask all my confirmed orders when I'm authenticated, I want all my confirmed orders.
     * post-condition: I get a list of all my confirmed orders.
     */
    @Test
    public void whenIAskAllConfirmedOrdersIWantAllMyConfirmedOrders() throws Exception {
        String resultJson = """
                [
                   {
                      "orderId":"550e8400-e29b-41d4-a716-446655440000",
                      "totalPrice":5000.0,
                      "orderDate":"2023-01-01T00:00:00",
                      "specialInstructions":null,
                      "lastOrderState":"ORDER_CONFIRMED",
                      "orderLines":[
                         {
                            "id":3,
                            "quantity":1,
                            "specialInstructions":null,
                            "productNumber":"6f8d5a2b-aa4e-4b71-a33d-182ccda1d3d0"
                         }
                      ]
                   },
                   {
                      "orderId":"d7f5c51d-ff67-4b88-a4a5-cc1627aa7469",
                      "totalPrice":5000.0,
                      "orderDate":"2023-01-01T00:00:00",
                      "specialInstructions":null,
                      "lastOrderState":"ORDER_CONFIRMED",
                      "orderLines":[
                         {
                            "id":5,
                            "quantity":1,
                            "specialInstructions":null,
                            "productNumber":"6f8d5a2b-aa4e-4b71-a33d-182ccda1d3d0"
                         }
                      ]
                   }
                ]
                        """;

        mockMvc.perform(
                        get("/api/orders/all/confirmed").with(jwt().authorities(List.of(new SimpleGrantedAuthority("user")))
                                        .jwt(jwt -> jwt
                                                .claim("sub", "b03a604f-112d-4b67-a0a0-5aa228d8877f")
                                                .claim(StandardClaimNames.GIVEN_NAME, "Maxim")
                                                .claim(StandardClaimNames.FAMILY_NAME, "Hansen")
                                                .claim(StandardClaimNames.EMAIL, "user@client.be")))
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(resultJson));
    }

    /**
     * If I ask all my orders sorted when I'm authenticated, I want all my orders sorted.
     * pre-condition: I have a start date, end date and order state.
     * post-condition: I get a list of all my orders sorted.
     */
    @Test
    public void whenIAskAllOrdersSortedIWantAllMyOrdersSorted() throws Exception {
        String requestJson = """           
                {
                    "startDate": "2023-01-01",
                    "endDate": "2023-01-01",
                    "orderState": "ORDER_CONFIRMED"
                }
                """;

        String resultJson = """
                        [
                  {
                    "orderId":"d7f5c51d-ff67-4b88-a4a5-cc1627aa7469",
                    "totalPrice":5000.0,
                    "orderDate":"2023-01-01T00:00:00",
                    "specialInstructions":null,
                    "lastOrderState":"ORDER_CONFIRMED",
                    "orderLines":
                    [
                     {
                       "id":5,
                       "quantity":1,
                       "productNumber":"6f8d5a2b-aa4e-4b71-a33d-182ccda1d3d0"
                     }
                    ]
                  }
                ]
                        """;

        mockMvc.perform(
                get("/api/orders/all/sorted").with(jwt().authorities(List.of(new SimpleGrantedAuthority("user")))
                                .jwt(jwt -> jwt
                                        .claim("sub", "b03a604f-112d-4b67-a0a0-5aa228d8877f")
                                        .claim(StandardClaimNames.GIVEN_NAME, "Maxim")
                                        .claim(StandardClaimNames.FAMILY_NAME, "Hansen")
                                        .claim(StandardClaimNames.EMAIL, "user@client.be")))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().json(resultJson));
    }

    /**
     * If I ask the state of an order when I'm authenticated, I want the state of that order.
     * pre-condition: I have an order id.
     * post-condition: I get the state of that order.
     */
    @Test
    public void whenIAskTheStateOfAnOrderIWantTheStateOfThatOrder() throws Exception {
        String result = "ORDER_CONFIRMED";
        String orderId = "d7f5c51d-ff67-4b88-a4a5-cc1627aa7469";

        mockMvc.perform(
                        get("/api/orders/state").with(jwt().authorities(List.of(new SimpleGrantedAuthority("user")))
                                        .jwt(jwt -> jwt
                                                .claim("sub", "b03a604f-112d-4b67-a0a0-5aa228d8877f")
                                                .claim(StandardClaimNames.GIVEN_NAME, "Maxim")
                                                .claim(StandardClaimNames.FAMILY_NAME, "Hansen")
                                                .claim(StandardClaimNames.EMAIL, "user@client.be")))
                                .param("orderId", orderId))
                .andExpect(status().isOk())
                .andExpect(content().string(result));
    }
}
