package be.kdg.client.controller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserDto {
    private String userName;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
}
