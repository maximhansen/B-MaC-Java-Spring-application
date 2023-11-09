package be.kdg.client.repository.loyalty;

import be.kdg.client.domain.loyalty.LoyaltyEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoyaltyEventRepository extends JpaRepository<LoyaltyEvent, Long> {
}
