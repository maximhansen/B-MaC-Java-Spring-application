package be.kdg.client.domain.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter @Setter
public class OrderLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private int quantity;

    @Column(columnDefinition = "TEXT")
    private String specialInstructions;

    @Column(nullable = false)
    private UUID productNumber;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    public OrderLine() {
    }

    public OrderLine(int quantity, UUID productNumber, String specialInstructions) {
        this.quantity = quantity;
        this.productNumber = productNumber;
        this.specialInstructions = specialInstructions;
    }
}
