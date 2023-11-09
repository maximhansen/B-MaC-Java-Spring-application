package be.kdg.client.domain.account;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.UUID;

@Entity
@Getter
public class Account {
    @Id
    private final UUID accountId;

    @OneToOne
    private Client client;

    private String name;

    private String lastName;

    private String phoneNumber;

    public Account() {
        this.accountId = UUID.randomUUID();
    }

    public Account(UUID accountIdFromLoggedInUser, Client client, String firstName, String lastName, String phoneNumber) {
        this.accountId = accountIdFromLoggedInUser;
        this.client = client;
        this.name = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }
}
