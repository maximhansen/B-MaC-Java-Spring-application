package be.kdg.client.service.order;

import be.kdg.client.controller.dto.FilterOrderByDateAndStatusDto;
import be.kdg.client.domain.account.Client;
import be.kdg.client.domain.order.Order;
import be.kdg.client.domain.order.OrderLine;
import be.kdg.client.domain.order.OrderState;
import be.kdg.client.repository.order.OrderRepository;
import be.kdg.client.service.account.AccountService;
import be.kdg.client.service.interfaces.OrderServiceInterface;
import be.kdg.client.service.exception.OrderNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderService implements OrderServiceInterface {
    private final OrderRepository orderRepository;
    private final OrderEventService orderEventService;
    private final AccountService accountService;

    public OrderService(OrderRepository orderRepository, OrderEventService orderEventService, AccountService accountService) {
        this.orderRepository = orderRepository;
        this.orderEventService = orderEventService;
        this.accountService = accountService;
    }

    public List<Order> getAllOrdersByAccountId(UUID accountIdFromLoggedInUser) throws OrderNotFoundException {
        List<Order> orders = retrieveOrdersByAccountId(accountIdFromLoggedInUser).orElse(Collections.emptyList());
        if(orders.isEmpty()) {
            throw new OrderNotFoundException("No orders found");
        }
        return orders;
    }

    public List<Order> getAllUnconfirmedOrdersByAccountId(UUID accountIdFromLoggedInUser) throws OrderNotFoundException {
        List<Order> orders = retrieveOrdersByAccountId(accountIdFromLoggedInUser).orElse(Collections.emptyList());
        List<Order> unconfirmedOrders = orders.stream()
                .filter(order -> orderEventService.getLatestOrderStateByDateTime(order.getOrderId()).equals(OrderState.ORDER_PLACED))
                .collect(Collectors.toList());
        if (unconfirmedOrders.isEmpty()) {
            throw new OrderNotFoundException("No unconfirmed orders found for the given account");
        }
        return unconfirmedOrders;
    }

    public List<Order> getAllOrdersByAccountIdSorted(UUID accountIdFromLoggedInUser, FilterOrderByDateAndStatusDto filterOrderByDateAndStatusDto) throws OrderNotFoundException {
        List<Order> orders = retrieveOrdersByAccountIdSorted(accountIdFromLoggedInUser, filterOrderByDateAndStatusDto).orElse(Collections.emptyList());
        if (orders.isEmpty()) {
            throw new OrderNotFoundException("No orders found");
        }
        return orders;
    }

    public List<OrderLine> getOrderLinesFromOrder(UUID orderId) throws OrderNotFoundException {
        Order order = retrieveOrderById(orderId);
        return order.getOrderLines();
    }

    public List<Order> getAllOrdersConfirmedByAccount(UUID accountId) throws OrderNotFoundException {
        List<Order> orders = retrieveAllConfirmedOrdersByOrderStateByAccount(accountId);
        if (orders.isEmpty()) {
            throw new OrderNotFoundException("No orders found");
        }
        return orders;
    }

    public Order retrieveOrderById(UUID orderId) throws OrderNotFoundException {
        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isPresent()) {
            return order.get();
        }
        throw new OrderNotFoundException("Order not found exception!");
    }

    public Optional<List<Order>> retrieveOrdersByAccountId(UUID accountId) {
        Client client = accountService.getClientByAccountId(accountId);
        List<Order> orders = orderRepository.findByClient_Id(client.getId()).orElse(Collections.emptyList());
        orders.forEach(order -> order.setOrderEvents(orderEventService.retrieveOrderEventsByOrder(order.getOrderId())));
        return Optional.of(orders);
    }

    public String getOrderStateOfOrder(UUID orderId, UUID accountIdFromLoggedInUser) throws OrderNotFoundException {
        List<Order> retrieveOrdersByAccountId = retrieveOrdersByAccountId(accountIdFromLoggedInUser)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));

        return retrieveOrdersByAccountId.stream()
                .filter(order -> order.getOrderId().equals(orderId))
                .findFirst()
                .map(order -> orderEventService.getLatestOrderStateByDateTime(order.getOrderId()).toString())
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));
    }

    public List<Order> retrieveAllConfirmedOrders() throws OrderNotFoundException {
        List<Order> orders = orderRepository.findAll();
        orders = orders.stream()
                .filter(order -> orderEventService.getLatestOrderStateByDateTime(order.getOrderId()).equals(OrderState.ORDER_CONFIRMED))
                .collect(Collectors.toList());
        if (orders.isEmpty()) {
            throw new OrderNotFoundException("No orders found");
        }
        return orders;
    }

    private Optional<List<Order>> retrieveOrdersByAccountIdSorted(UUID accountIdFromLoggedInUser, FilterOrderByDateAndStatusDto filterOrderByDateAndStatusDto) {
        Client client = accountService.getClientByAccountId(accountIdFromLoggedInUser);
        List<Order> orders = orderRepository.findByClient_Id(client.getId()).orElse(Collections.emptyList());
        orders = retrieveAllOrdersSortedByDate(filterOrderByDateAndStatusDto.getStartDate(), filterOrderByDateAndStatusDto.getEndDate(), orders);
        if(filterOrderByDateAndStatusDto.getOrderState() == null) {
            return Optional.of(orders);
        }
        orders = retrieveAllOrdersByOrderState(orders, filterOrderByDateAndStatusDto.getOrderState());
        return Optional.of(orders);
    }

    private List<Order> retrieveAllOrdersSortedByDate(LocalDate startDate, LocalDate endDate, List<Order> orders) {
        return orders.stream()
                .filter(order -> order.getOrderDate().isAfter(startDate.atStartOfDay().minusDays(1)) && order.getOrderDate().isBefore(endDate.atStartOfDay().plusDays(1)))
                .collect(Collectors.toList());}

    private List<Order> retrieveAllOrdersByOrderState(List<Order> orders, OrderState orderState) {
        return orders.stream()
                .filter(order -> orderEventService.getLatestOrderStateByDateTime(order.getOrderId()).equals(orderState)).collect(Collectors.toList());
    }

    private List<Order> retrieveAllConfirmedOrdersByOrderStateByAccount(UUID accountId) {
        Client client = accountService.getClientByAccountId(accountId);
        Optional<List<Order>> orders = orderRepository.findByClient_Id(client.getId());
        return orders.map(orderList -> orderList.stream()
                .filter(order -> orderEventService.getLatestOrderStateByDateTime(order.getOrderId()).equals(OrderState.ORDER_CONFIRMED))
                .collect(Collectors.toList())).orElse(Collections.emptyList());
    }
}
