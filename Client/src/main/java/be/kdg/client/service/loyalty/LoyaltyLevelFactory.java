package be.kdg.client.service.loyalty;

import be.kdg.client.domain.loyalty.LoyaltyLevel;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class LoyaltyLevelFactory {
    public LoyaltyLevel createLoyaltyLevel(String name, double discountPercentage, int threshold, Map<Integer, LoyaltyLevel> loyaltyLevels) {
        if(checkIfLoyaltyLevelIsValid(name, discountPercentage, threshold, loyaltyLevels)) {
            return new LoyaltyLevel(name, discountPercentage, threshold);
        }
        return null;
    }

    public boolean changeLoyaltyLevel(double discountPercentage, int threshold) {
        return checkIfExistingLoyaltyLevelChangingIsValid(discountPercentage, threshold);
    }

    private boolean checkIfExistingLoyaltyLevelChangingIsValid(double discountPercentage, int threshold) {
        checkIfDiscountPercentageIsBetweenZeroAndHundred(discountPercentage);
        checkIfThresholdIsPositive(threshold);
        return true;
    }

    private boolean checkIfLoyaltyLevelIsValid(String name, double discountPercentage, int threshold, Map<Integer, LoyaltyLevel> loyaltyLevels) {
        checkIfNameLoyaltyNameExists(name, loyaltyLevels);
        checkIfDiscountPercentageIsBetweenZeroAndHundred(discountPercentage);
        checkIfThresholdIsPositive(threshold);
        checkIfLoyaltyPercentageExistAlready(discountPercentage, loyaltyLevels);
        return true;
    }

    private void checkIfNameLoyaltyNameExists(String name, Map<Integer, LoyaltyLevel> loyaltyLevels) {
        loyaltyLevels.values().forEach(loyaltyLevel -> {
            if (loyaltyLevel.getName().equals(name)) {
                throw new IllegalArgumentException("Loyalty level name already exists!");
            }
        });
    }

    private void checkIfDiscountPercentageIsBetweenZeroAndHundred(double discountPercentage) {
        if (discountPercentage < 0 || discountPercentage > 100) {
            throw new IllegalArgumentException("Discount percentage must be between 0 and 100!");
        }
    }

    private void checkIfLoyaltyPercentageExistAlready(double discountPercentage, Map<Integer, LoyaltyLevel> loyaltyLevels) {
        loyaltyLevels.values().forEach(loyaltyLevel -> {
            if (loyaltyLevel.getDiscountPercentage() == discountPercentage) {
                throw new IllegalArgumentException("Loyalty level discount percentage already exists!");
            }
        });
    }

    private void checkIfThresholdIsPositive(int threshold) {
        if (threshold < 0) {
            throw new IllegalArgumentException("Threshold must be positive!");
        }
    }
}
