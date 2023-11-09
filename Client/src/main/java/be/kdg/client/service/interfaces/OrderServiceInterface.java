package be.kdg.client.service.interfaces;

import be.kdg.client.controller.dto.FilterOrderByDateAndStatusDto;
import be.kdg.client.domain.order.Order;
import be.kdg.client.service.exception.OrderNotFoundException;

import java.util.List;
import java.util.UUID;

public interface OrderServiceInterface {
    List<Order> getAllOrdersByAccountId(UUID accountIdFromLoggedInUser) throws OrderNotFoundException;
    List<Order> getAllUnconfirmedOrdersByAccountId(UUID accountIdFromLoggedInUser) throws OrderNotFoundException;
    List<Order> getAllOrdersByAccountIdSorted(UUID accountIdFromLoggedInUser, FilterOrderByDateAndStatusDto filterOrderByDateAndStatusDto) throws OrderNotFoundException;
}
