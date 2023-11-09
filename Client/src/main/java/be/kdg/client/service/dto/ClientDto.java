package be.kdg.client.service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ClientDto {
    private String FirstName;
    private String LastName;
    private Long clientId;

    public ClientDto(String firstName, String lastName, Long clientId) {
        FirstName = firstName;
        LastName = lastName;
        this.clientId = clientId;
    }
}
