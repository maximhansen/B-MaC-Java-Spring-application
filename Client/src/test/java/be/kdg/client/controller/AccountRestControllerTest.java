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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("classpath:application-test.properties")
public class AccountRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenIMakeANewAccountIWantSuccess() throws Exception {
        String accountJson = """
                {
                    "firstName": "Sam",
                    "lastName": "Verdonck",
                    "phoneNumber": "0473389421"
                }
                """;
        mockMvc.perform(
                        post("/api/account/create").with(jwt().authorities(List.of(new SimpleGrantedAuthority("user")))
                                        .jwt(jwt -> jwt
                                                .claim("sub", "49559c8f-afb6-43ff-807e-fe294b460775")
                                                .claim(StandardClaimNames.GIVEN_NAME, "Sam")
                                                .claim(StandardClaimNames.FAMILY_NAME, "Verdonck")
                                                .claim(StandardClaimNames.EMAIL, "sam.verdonck@test.be")))
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(accountJson))
                .andExpect(status().isOk());
    }
}
