package be.kdg.client.repository.order;

import be.kdg.client.domain.order.Order;
import be.kdg.client.domain.order.OrderEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderEventRepository extends JpaRepository<OrderEvent, Long> {
    List<OrderEvent> findOrderEventsByOrderOrderId(UUID orderId);
}
