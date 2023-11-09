package be.kdg.client.domain.order;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class OrderEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateTime;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderState orderState;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    public OrderEvent() {
    }

    public OrderEvent(OrderState orderState, LocalDateTime dateTime) {
        this.orderState = orderState;
        this.dateTime = dateTime;
    }

    public OrderEvent(OrderState orderState, LocalDateTime dateTime, Order order) {
        this.orderState = orderState;
        this.dateTime = dateTime;
        this.order = order;
    }
}
