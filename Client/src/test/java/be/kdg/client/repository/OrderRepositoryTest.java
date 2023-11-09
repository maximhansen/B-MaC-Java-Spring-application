package be.kdg.client.repository;

import be.kdg.client.domain.order.Order;
import be.kdg.client.repository.order.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    @Transactional
    public void getAllOrders() {
        Optional<List<Order>> orders = orderRepository.findByClient_Id(1);
        assert orders.isPresent();
    }

    @Test
    @Transactional
    public void getAllOrdersByClientId() {
        Optional<List<Order>> orders = orderRepository.findByClient_Id(1);
        assert orders.isPresent();
    }
}
