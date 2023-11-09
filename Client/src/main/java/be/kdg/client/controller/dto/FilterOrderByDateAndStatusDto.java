package be.kdg.client.controller.dto;

import be.kdg.client.domain.order.OrderState;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class FilterOrderByDateAndStatusDto {
    @NotNull(message = "Start date is required")
    private LocalDate startDate;
    @NotNull(message = "End date is required")
    private LocalDate endDate;
    private OrderState orderState;
}
