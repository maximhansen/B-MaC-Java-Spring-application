package be.kdg.client.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("classpath:application-test.properties")
public class SalesRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * Test to check if the sales report is generated for a specific date.
     * precondition: the date is not null
     * post condition: the sales report is generated for a specific date
     */
    @Test
    public void AsAAdminIWantToGenerateASalesReportForASpecificDate() throws Exception {
        LocalDate date = LocalDate.of(2023, 1, 1);
        mockMvc.perform(
                        get("/api/sales/date").with(jwt().authorities(List.of(new SimpleGrantedAuthority("admin")))
                                        .jwt(jwt -> jwt
                                                .claim("sub", "f9090ea6-b05b-449f-afd1-1b01fdddef6a")
                                                .claim(StandardClaimNames.GIVEN_NAME, "Maxim")
                                                .claim(StandardClaimNames.FAMILY_NAME , "Hansen")
                                                .claim(StandardClaimNames.EMAIL, "admin@client.be")))
                                .param("date", String.valueOf(date)))
                .andExpect(status().isOk())
                .andExpect(content().string("Sales for date: " + date + " generated!"));
    }

    /**
     * Test to check if the sales report is generated for a specific product.
     * precondition: the product id is not null
     * post condition: the sales report is generated for a specific product
     */
    @Test
    public void AsAAdminIWantToGenerateASalesReportForASpecificProduct() throws Exception {
        UUID productId = UUID.fromString("6f8d5a2b-aa4e-4b71-a33d-182ccda1d3d0");
        String productName = "Croissant";
        mockMvc.perform(
                        get("/api/sales/product").with(jwt().authorities(List.of(new SimpleGrantedAuthority("admin")))
                                        .jwt(jwt -> jwt
                                                .claim("sub", "f9090ea6-b05b-449f-afd1-1b01fdddef6a")
                                                .claim(StandardClaimNames.GIVEN_NAME, "Maxim")
                                                .claim(StandardClaimNames.FAMILY_NAME, "Hansen")
                                                .claim(StandardClaimNames.EMAIL, "admin@client.be")))
                                .param("productId", String.valueOf(productId)))
                .andExpect(status().isOk())
                .andExpect(content().string("Sales for product: " + productName + " generated!"));
    }

    /**
     * Test to check if the sales report is generated for a specific client.
     * precondition: the client id is not null
     * post condition: the sales report is generated for a specific client
     */
    @Test
    public void AsAAdminIWantToGenerateASalesReportForASpecificClient() throws Exception {
        int clientId = 1;
        String clientName = "Maxim Hansen";
        mockMvc.perform(
                        get("/api/sales/client").with(jwt().authorities(List.of(new SimpleGrantedAuthority("admin")))
                                        .jwt(jwt -> jwt
                                                .claim("sub", "f9090ea6-b05b-449f-afd1-1b01fdddef6a")
                                                .claim(StandardClaimNames.GIVEN_NAME, "Maxim")
                                                .claim(StandardClaimNames.FAMILY_NAME, "Hansen")
                                                .claim(StandardClaimNames.EMAIL, "admin@client.be")))
                                .param("clientId", String.valueOf(clientId)))
                .andExpect(status().isOk())
                .andExpect(content().string("Sales for client: " + clientName + " generated!"));
    }
}
