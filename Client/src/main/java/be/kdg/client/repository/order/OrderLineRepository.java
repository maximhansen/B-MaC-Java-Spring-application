package be.kdg.client.repository.order;

import be.kdg.client.domain.order.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderLineRepository extends JpaRepository<OrderLine, Long> {
    Optional<List<OrderLine>> findByProductNumber(UUID productNumber);
}
