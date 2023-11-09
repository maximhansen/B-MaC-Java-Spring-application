package be.kdg.client.service.loyalty;

import be.kdg.client.domain.loyalty.LoyaltyLevel;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Component
@ConfigurationProperties(prefix = "loyalty-level")
public class LoyaltyLevelConfig {
    private List<LoyaltyLevel> levels;

    @Autowired
    private LoyaltyLevelService loyaltyLevelService;

    public void setLevels(List<LoyaltyLevel> levels) {
        this.levels = levels;
        for (LoyaltyLevel level : levels) {
            loyaltyLevelService.createLoyaltyLevel(level.getName(), level.getDiscountPercentage(), level.getThreshold());
        }
    }
}
