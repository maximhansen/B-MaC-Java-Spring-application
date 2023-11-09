package be.kdg.client.domain.loyalty;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LoyaltyLevel {
    private String name;
    private double discountPercentage;
    private int threshold;

    public LoyaltyLevel(String name, double discountPercentage, int threshold) {
        this.name = name;
        this.discountPercentage = discountPercentage;
        this.threshold = threshold;
    }
}
