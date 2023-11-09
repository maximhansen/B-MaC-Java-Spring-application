package be.kdg.client.service.dto;

import be.kdg.client.controller.dto.OrderClientDto;
import be.kdg.client.controller.dto.OrderDto;
import be.kdg.client.controller.dto.ProductDto;
import be.kdg.client.domain.account.Account;
import be.kdg.client.domain.order.Order;
import be.kdg.client.domain.order.OrderLine;
import be.kdg.client.service.order.OrderEventService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransferDtoService {

    private final OrderEventService orderEventService;

    public TransferDtoService(OrderEventService orderEventService) {
        this.orderEventService = orderEventService;
    }

    public List<OrderDto> ordersToOrderDtos(List<Order> orders) {
        return orders.stream()
                .map(order -> new OrderDto(order.getOrderId(),
                        order.getTotalPrice(), order.getOrderDate(),
                        order.getSpecialInstructions(), orderEventService.getLatestOrderStateByDateTime(order.getOrderId()), order.getOrderLines()))
                .collect(Collectors.toList());
    }

    public List<ClientDto> accountsToClientDto(List<Account> accounts) {
        return accounts.stream()
                .map(account -> new ClientDto(account.getName(), account.getLastName(),
                        account.getClient().getId()))
                .collect(Collectors.toList());
    }

    public OrderClientDto makeOrderClientDtoFromOrder(Order order) {
        OrderClientDto orderClientDto = new OrderClientDto();
        orderClientDto.setSpecialInstructions(order.getSpecialInstructions());
        orderClientDto.setOrderDate(order.getOrderDate());

        List<ProductDto> productDtoList = new ArrayList<>();
        for (OrderLine orderLine: order.getOrderLines()) {
            ProductDto productDto = new ProductDto();
            productDto.setProductNumber(orderLine.getProductNumber());
            productDto.setQuantity(orderLine.getQuantity());
            productDtoList.add(productDto);
        }
        orderClientDto.setProductDtoList(productDtoList);
        return orderClientDto;
    }

    public List<OrderLineMessageDto> makeOrderLineMessageDtoFromOrderLines(List<OrderLine> orderLines) {
        List<OrderLineMessageDto> orderLineMessageDtos = new ArrayList<>();
        for (OrderLine orderLine : orderLines) {
            orderLineMessageDtos.add(new OrderLineMessageDto(orderLine.getQuantity(), orderLine.getProductNumber()));
        }
        return orderLineMessageDtos;
    }
}
