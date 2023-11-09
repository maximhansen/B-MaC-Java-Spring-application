package be.kdg.warehouse.domain.stock;

import be.kdg.warehouse.domain.ingredient.Ingredient;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Entity
public class StockMovement {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;
    @ManyToOne
    private Ingredient ingredient;
    private double amount;
    private LocalDateTime dateTime;
    private StockMovementType type;

    public StockMovement() {
    }

    public StockMovement(Ingredient ingredient, double amount, LocalDateTime dateTime, StockMovementType type) {
        this.ingredient = ingredient;
        this.amount = amount;
        this.dateTime = dateTime;
        this.type = type;
    }
}
