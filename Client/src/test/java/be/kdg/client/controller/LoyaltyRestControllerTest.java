package be.kdg.client.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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
public class LoyaltyRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * Test if the loyalty level is returned when a user is authenticated.
     * pre-condition: the user is authenticated
     * post-condition: the loyalty level is returned
     */
    @Test
    public void WhenIAmAuthenticatedIWishToGetMyLoyaltyLevel() throws Exception {
        mockMvc.perform(
                        get("/api/loyalty").with(jwt().authorities(List.of(new SimpleGrantedAuthority("user")))
                                        .jwt(jwt -> jwt
                                                .claim("sub", "3dc99fa5-90aa-48e4-b76d-b45bc65c6d80")
                                                .claim(StandardClaimNames.GIVEN_NAME, "Maxim")
                                                .claim(StandardClaimNames.FAMILY_NAME , "Hansen")
                                                .claim(StandardClaimNames.EMAIL, "user@client.be"))))
                .andExpect(status().isOk())
                .andExpect(content().string("Bronze"));
    }

    /**
     * Test if the loyalty points are returned when a user is authenticated.
     * pre-condition: the user is authenticated
     * post-condition: the loyalty points are returned
     */
    @Test
    public void WhenIAmAuthenticatedIWishToGetMyLoyaltyPoints() throws Exception {
        mockMvc.perform(
                        get("/api/loyalty/points").with(jwt().authorities(List.of(new SimpleGrantedAuthority("user")))
                                .jwt(jwt -> jwt
                                        .claim("sub", "3dc99fa5-90aa-48e4-b76d-b45bc65c6d80")
                                        .claim(StandardClaimNames.GIVEN_NAME, "Maxim")
                                        .claim(StandardClaimNames.FAMILY_NAME, "Hansen")
                                        .claim(StandardClaimNames.EMAIL, "user@client.be"))))
                .andExpect(status().isOk())
                .andExpect(content().string("500"));
    }

    /**
     * Test if the loyalty limits are returned when an admin is authenticated.
     * pre-condition: the admin is authenticated
     * post-condition: the loyalty limits are returned
     */
    @Test
    public void WhenIAmAuthenticatedAsAdminIWishToGetTheLoyaltyLimits() throws Exception {
        String expected = """
                [
                  {
                    "id": 0,
                    "name": "Bronze",
                    "discountPercentage": 0.0,
                    "threshold": 1000
                  },
                  {
                    "id": 1,
                    "name": "Silver",
                    "discountPercentage": 0.05,
                    "threshold": 5000
                  },
                  {
                    "id": 2,
                    "name": "Gold",
                    "discountPercentage": 0.1,
                    "threshold": 10000
                  },
                  {
                    "id": 3,
                    "name": "Platinum",
                    "discountPercentage": 0.2,
                    "threshold": 0
                  }
                ]
                """;
        mockMvc.perform(
                        get("/api/loyalty/limits").with(jwt().authorities(List.of(new SimpleGrantedAuthority("admin")))
                                .jwt(jwt -> jwt
                                        .claim("sub", "3dc99fa5-90aa-48e4-b76d-b45bc65c6d80")
                                        .claim(StandardClaimNames.GIVEN_NAME, "Maxim")
                                        .claim(StandardClaimNames.FAMILY_NAME, "Hansen")
                                        .claim(StandardClaimNames.EMAIL, "user@client.be"))))
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
    }
}
