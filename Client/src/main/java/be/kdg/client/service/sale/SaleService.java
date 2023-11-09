package be.kdg.client.service.sale;

import be.kdg.client.domain.order.Order;
import be.kdg.client.domain.order.OrderLine;
import be.kdg.client.repository.order.OrderLineRepository;
import be.kdg.client.repository.order.OrderRepository;
import be.kdg.client.service.exception.NoSalesFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SaleService {

    private final OrderRepository orderRepository;
    private final OrderLineRepository orderLineRepository;

    public SaleService(OrderRepository orderRepository, OrderLineRepository orderLineRepository) {
        this.orderRepository = orderRepository;
        this.orderLineRepository = orderLineRepository;
    }

    public List<Order> generateSalesByDate(LocalDate date) throws NoSalesFoundException{
        LocalDateTime startDate = LocalDateTime.from(date.atStartOfDay());
        LocalDateTime endDate = LocalDateTime.from(date.atStartOfDay().plusDays(1));
        Optional<List<Order>> orders = orderRepository.findByOrderDateBetween(startDate, endDate);
        if(orders.isPresent() && !orders.get().isEmpty()) {
            return orders.get();
        } else {
            throw new NoSalesFoundException("No sales found for date: " + date);
        }
    }

    public List<Order> generateSalesByProduct(UUID productId) {
        Optional<List<OrderLine>> orderLines = orderLineRepository.findByProductNumber(productId);
        List<Order> orders = new ArrayList<>();
        if(orderLines.isPresent() && !orderLines.get().isEmpty()) {
            for(OrderLine orderLine : orderLines.get()) {
                orders.add(orderLine.getOrder());
            }
            return orders;
        } else {
            throw new NoSalesFoundException("No sales found for product: " + productId);
        }
    }

    public List<Order> getAllOrderByClient(Long clientId) {
        Optional<List<Order>> orders = orderRepository.findAllByClientId(clientId);
        if(orders.isPresent() && !orders.get().isEmpty()) {
            return orders.get();
        } else {
            throw new NoSalesFoundException("No sales found for client: " + clientId);
        }
    }
}
