package be.kdg.client.repository.order;

import be.kdg.client.domain.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    Optional<List<Order>> findByClient_Id(long id);
    Optional<List<Order>> findByOrderDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    Optional<List<Order>> findAllByClientId(Long clientId);
}
