package be.kdg.warehouse.domain.ingredient;

import jakarta.persistence.*;
import jdk.jfr.Name;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Entity
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String name;

    private double amount;

    @Enumerated(EnumType.STRING)
    private Unit unit;

    @Enumerated(EnumType.STRING)
    private TemperatureZone temperatureZone;

    private Boolean dangerZone;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate expiryDate;

    @Column(nullable = false)
    @Name("minimum_Stock")
    private double minimumStock;
    public Ingredient() {
    }

    public Ingredient(String name) {
        this.name = name;
    }

    public Ingredient(UUID ingredientNumber, String name, double amount, Unit unit, TemperatureZone temperatureZone, Boolean dangerZone, LocalDate expiryDate, double minimumStock) {
        this.id = ingredientNumber;
        this.name = name;
        this.amount = amount;
        this.unit = unit;
        this.temperatureZone = temperatureZone;
        this.dangerZone = dangerZone;
        this.expiryDate = expiryDate;
        this.minimumStock = minimumStock;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "name='" + name + '\'' +
                ", amount=" + amount +
                '}';
    }
}
