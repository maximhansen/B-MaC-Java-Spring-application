package be.kdg.client.controller.dto;

import be.kdg.client.domain.order.OrderLine;
import be.kdg.client.domain.order.OrderState;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter @Setter
public class OrderDto {
    @NotNull(message = "orderId can't be null")
    private UUID orderId;
    private Double totalPrice;
    private LocalDateTime orderDate;
    private String specialInstructions;
    private OrderState lastOrderState;
    private List<OrderLine> orderLines;

    public OrderDto(UUID orderId, Double totalPrice, LocalDateTime orderDate, String specialInstructions, OrderState lastOrderState, List<OrderLine> orderLines) {
        this.orderId = orderId;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
        this.specialInstructions = specialInstructions;
        this.lastOrderState = lastOrderState;
        this.orderLines = orderLines;
    }
}
