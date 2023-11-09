package be.kdg.client.domain.order;

import be.kdg.client.domain.account.Client;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity(name = "order_tbl")
@Getter @Setter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID orderId;

    @Column(nullable = false)
    @Min(value = 0, message = "Total price must be a positive number.")
    private Double totalPrice;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
    private List<OrderEvent> orderEvents;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime orderDate;

    @Column(columnDefinition = "TEXT")
    private String specialInstructions;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
    private List<OrderLine> orderLines;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    public Order() {
        this.orderEvents = new ArrayList<>();
    }

    public Order(Double totalPrice, LocalDateTime orderDate, String specialInstructions, List<OrderLine> orderLines, OrderEvent orderEvent) {
        this.orderId = UUID.randomUUID();
        this.orderEvents = new ArrayList<>();
        orderEvents.add(orderEvent);
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
        this.specialInstructions = specialInstructions;
        this.orderLines = orderLines;
    }

    public Order(Double totalPrice, LocalDateTime orderDate, String specialInstructions, OrderEvent orderEvent) {
        this.orderId = UUID.randomUUID();
        this.orderEvents = new ArrayList<>();
        orderEvents.add(orderEvent);
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
        this.specialInstructions = specialInstructions;
    }
}
