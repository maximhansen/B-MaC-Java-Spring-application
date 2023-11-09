package be.kdg.client.service.loyalty;

import be.kdg.client.domain.loyalty.LoyaltyLevel;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@Getter
public class LoyaltyLevelService {
    private final HashMap<Integer, LoyaltyLevel> loyaltyLevels;
    private final LoyaltyLevelFactory loyaltyLevelFactory;

    public LoyaltyLevelService(LoyaltyLevelFactory loyaltyLevelFactory) {
        this.loyaltyLevels = new HashMap<>();
        this.loyaltyLevelFactory = loyaltyLevelFactory;
    }

    public LoyaltyLevel getLoyaltyLevel(int points) {
        return loyaltyLevels.values()
                .stream()
                .filter(loyaltyLevel -> points < loyaltyLevel.getThreshold())
                .findFirst()
                .orElse(loyaltyLevels.get(loyaltyLevels.size() - 1));
    }

    public double getDiscountPercentage(int points) {
        return getLoyaltyLevel(points).getDiscountPercentage();
    }

    public void changeLoyaltyLevel(int id, String loyaltyLevelName, double discountPercentage, int threshold) {
        if(loyaltyLevelFactory.changeLoyaltyLevel(discountPercentage, threshold)) {
            if(!loyaltyLevels.containsKey(id)) throw new IllegalArgumentException("Loyalty level does not exist!");
            loyaltyLevels.get(id).setName(loyaltyLevelName);
            loyaltyLevels.get(id).setDiscountPercentage(discountPercentage);
            loyaltyLevels.get(id).setThreshold(threshold);
        }
    }

    public void createLoyaltyLevel(String loyaltyLevelName, double discountPercentage, int threshold) throws IllegalArgumentException{
        loyaltyLevels.put(loyaltyLevels.size(), loyaltyLevelFactory.createLoyaltyLevel(loyaltyLevelName, discountPercentage, threshold, loyaltyLevels));
    }

    public void deleteLoyaltyLevel(int id) {
        if(loyaltyLevels.containsKey(id))
        {
            loyaltyLevels.remove(id);
        } else {
            throw new IllegalArgumentException("Loyalty level does not exist!");
        }
    }
}
