package be.kdg.client.controller.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class NewAccountDto {
    @NotNull(message = "firstname can't be null")
    String firstName;
    @NotNull(message = "lastname can't be null")
    String lastName;
    String phoneNumber;
}
