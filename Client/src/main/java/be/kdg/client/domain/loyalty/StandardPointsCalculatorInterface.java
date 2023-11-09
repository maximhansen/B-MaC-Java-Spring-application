package be.kdg.client.domain.loyalty;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "points-calculator", havingValue = "standard")
public class StandardPointsCalculatorInterface implements PointsCalculatorInterface {
    @Override
    public int calculatePoints(Double price) {
        return price.intValue() / 10;
    }
}
