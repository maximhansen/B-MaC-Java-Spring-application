package be.kdg.client.service.order;

import be.kdg.client.controller.dto.OrderClientDto;
import be.kdg.client.controller.dto.ProductDto;
import be.kdg.client.domain.order.Order;
import be.kdg.client.domain.order.OrderEvent;
import be.kdg.client.domain.order.OrderLine;
import be.kdg.client.domain.order.OrderState;
import be.kdg.client.repository.order.OrderRepository;
import be.kdg.client.service.account.AccountService;
import be.kdg.client.service.dto.LoyaltyPointEvent;
import be.kdg.client.service.dto.OrderLineMessageDto;
import be.kdg.client.service.dto.OrderMessagingDto;
import be.kdg.client.service.dto.TransferDtoService;
import be.kdg.client.service.exception.*;
import be.kdg.client.service.interfaces.OrderCrudServiceInterface;
import be.kdg.client.service.pricing.PricingService;
import be.kdg.client.service.send.Sender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class OrderCrudService implements OrderCrudServiceInterface {
    private final OrderRepository orderRepository;
    private final OrderEventService orderEventService;
    private final AccountService accountService;
    private final ApplicationEventPublisher publisher;
    private final Sender sender;
    private final PricingService pricingService;
    private final OrderLineService orderLineService;
    private final TransferDtoService transferDtoService;
    private final OrderService orderService;

    public OrderCrudService(OrderRepository orderRepository, OrderEventService orderEventService,
                            AccountService accountService, ApplicationEventPublisher publisher,
                            Sender sender, PricingService pricingService, OrderLineService orderLineService,
                            TransferDtoService transferDtoService, OrderService orderService) {
        this.orderRepository = orderRepository;
        this.orderEventService = orderEventService;
        this.accountService = accountService;
        this.publisher = publisher;
        this.sender = sender;
        this.pricingService = pricingService;
        this.orderLineService = orderLineService;
        this.transferDtoService = transferDtoService;
        this.orderService = orderService;
    }

    public void createOrder(OrderClientDto orderDto) throws ProductNotPresentException {
        OrderEvent orderEvent = new OrderEvent(OrderState.ORDER_PLACED, LocalDateTime.now());
        Order order = orderRepository.save(new Order
                (getTotalPriceIncludingDiscount(orderDto), LocalDateTime.now(), orderDto.getSpecialInstructions(), orderEvent));
        order.setClient(accountService.getClientByAccountId(orderDto.getAccountId()));
        orderEvent.setOrder(order);
        orderEventService.addOrderEvent(orderEvent);
        addProductLines(orderDto.getProductDtoList(), order);
    }

    public void createOrderViaExistingOrder(UUID orderId, UUID accountIdFromLoggedInUser) throws OrderNotFoundException, ProductNotPresentException, AccountNotFoundException {
        Order order = retrieveOrderByIdWichBelongsToAccount(orderId, accountIdFromLoggedInUser);
        OrderClientDto orderClientDto = transferDtoService.makeOrderClientDtoFromOrder(order);
        orderClientDto.setAccountId(accountIdFromLoggedInUser);
        createOrder(orderClientDto);
    }

    public void confirmOrder(UUID orderId, UUID accountId) throws OrderNotFoundException, OrderStateException, OrderAlreadyConfirmedException {
        Order order = retrieveOrderByIdWichBelongsToAccount(orderId, accountId);
        OrderState orderState = orderEventService.getLatestOrderStateByDateTime(orderId);

        if(orderState.equals(OrderState.ORDER_PLACED)) {
            orderEventService.addOrderEvent(new OrderEvent(OrderState.ORDER_CONFIRMED, LocalDateTime.now(), order));
            publisher.publishEvent(new LoyaltyPointEvent(order.getTotalPrice(), accountId));
            sendOrderToBakery(orderId, order.getSpecialInstructions(), order.getOrderLines());
        } else if (orderState.equals(OrderState.ORDER_CONFIRMED)) {
            throw new OrderAlreadyConfirmedException("Order is already confirmed");
        } else {
            throw new OrderStateException("Order can't be confirmed current state is: " + orderState);
        }
    }

    public void cancelOrder(UUID orderId, UUID accountIdFromLoggedInUser) throws OrderNotFoundException, OrderStateException {
        Order order = retrieveOrderByIdWichBelongsToAccount(orderId, accountIdFromLoggedInUser);

        if(orderEventService.getLatestOrderStateByDateTime(orderId).equals(OrderState.ORDER_PLACED)) {
            orderEventService.addOrderEvent(new OrderEvent(OrderState.ORDER_CANCELLED, LocalDateTime.now(), order));
        } else
            throw new OrderStateException("Order can't be cancelled! The current state is: "
                    + orderEventService.getLatestOrderStateByDateTime(orderId));
    }

    private Double getTotalPriceIncludingDiscount(OrderClientDto orderClientDto) {
        return pricingService.getTotalPriceInclDiscount(orderClientDto.getProductDtoList(),
                accountService.getClientByAccountId(orderClientDto.getAccountId()).getId());
    }

    private void addProductLines(List<ProductDto> productDtoList, Order order) throws ProductNotPresentException {
        orderLineService.addOrderliness(productDtoList, order);
    }

    private List<Order> retrieveOrdersByAccountId(UUID accountId) throws OrderNotFoundException {
        Optional<List<Order>> orders = orderService.retrieveOrdersByAccountId(accountId);
        if (orders.isPresent()) {
            return orders.get();
        } else {
            throw new OrderNotFoundException("No orders found");
        }
    }

    private Order retrieveOrderByIdWichBelongsToAccount(UUID orderId, UUID accountId) throws OrderNotFoundException {
        Optional<Order> order = retrieveOrdersByAccountId(accountId).stream()
                .filter(o -> o.getOrderId().equals(orderId))
                .findFirst();
        if (order.isPresent()) {
            return order.get();
        }
        throw new OrderNotFoundException("Order not found exception!");
    }

    private void sendOrderToBakery(UUID orderId, String specialInstruction, List<OrderLine> orderLines) {
        OrderMessagingDto orderMessagingDto = new OrderMessagingDto(orderId, specialInstruction, getOrderLinesInMessageDto(orderLines));
        sender.sendOrderToBakery(orderMessagingDto);
    }

    private List<OrderLineMessageDto> getOrderLinesInMessageDto(List<OrderLine> orderLines) {
        return transferDtoService.makeOrderLineMessageDtoFromOrderLines(orderLines);
    }

    @EventListener
    public void orderReady(UUID orderId) throws OrderNotFoundException {
        Order order = orderService.retrieveOrderById(orderId);
        orderEventService.addOrderEvent(new OrderEvent(OrderState.ORDER_READY, LocalDateTime.now(), order));
        log.info("Order ready: " + orderId);
    }
}
