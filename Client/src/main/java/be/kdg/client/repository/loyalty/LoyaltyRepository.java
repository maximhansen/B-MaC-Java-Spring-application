package be.kdg.client.repository.loyalty;

import be.kdg.client.domain.loyalty.LoyaltyEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoyaltyRepository extends JpaRepository<LoyaltyEvent, Long> {
}
