package be.kdg.client.domain.loyalty;

import be.kdg.client.domain.account.Client;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class LoyaltyEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime localDateTime;

    @Column(nullable = false)
    @Min(value = 0, message = "Points must be a positive number.")
    private int points;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    public LoyaltyEvent() {
    }

    public LoyaltyEvent(LocalDateTime localDateTime, int points, Client client) {
        this.localDateTime = localDateTime;
        this.points = points;
        this.client = client;
    }
}
