package be.kdg.client.service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LoyaltyLevelDto {
    private int id;
    private String name;
    private double discountPercentage;
    private int threshold;

    public LoyaltyLevelDto(int id, String name, double discountPercentage, int threshold) {
        this.id = id;
        this.name = name;
        this.discountPercentage = discountPercentage;
        this.threshold = threshold;
    }
}
