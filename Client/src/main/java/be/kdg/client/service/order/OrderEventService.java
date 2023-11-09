package be.kdg.client.service.order;

import be.kdg.client.domain.order.OrderEvent;
import be.kdg.client.domain.order.OrderState;
import be.kdg.client.repository.order.OrderEventRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
public class OrderEventService {

    private final OrderEventRepository orderEventRepository;

    public OrderEventService(OrderEventRepository orderEventRepository) {
        this.orderEventRepository = orderEventRepository;
    }

    public void addOrderEvent(OrderEvent orderEvent) {
        orderEventRepository.save(orderEvent);
    }

    public List<OrderEvent> retrieveOrderEventsByOrder(UUID orderId) {
        return orderEventRepository.findOrderEventsByOrderOrderId(orderId);
    }

    public OrderState getLatestOrderStateByDateTime(UUID orderId) {
        List<OrderEvent> orderEvents = orderEventRepository.findOrderEventsByOrderOrderId(orderId);
        return orderEvents.stream()
                .max(Comparator.comparing(OrderEvent::getDateTime))
                .map(OrderEvent::getOrderState)
                .orElse(null);
    }
}
