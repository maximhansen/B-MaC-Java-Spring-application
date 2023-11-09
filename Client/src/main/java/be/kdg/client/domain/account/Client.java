package be.kdg.client.domain.account;

import be.kdg.client.domain.loyalty.LoyaltyEvent;
import be.kdg.client.domain.order.Order;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Entity
@Component
@Getter @Setter
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "client")
    @JsonIgnore
    private List<Order> orders;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "client")
    @JsonIgnore
    private List<LoyaltyEvent> loyaltyEvents;

    public Client() {
        this.orders = new ArrayList<>();
        this.loyaltyEvents = new ArrayList<>();
    }
}
