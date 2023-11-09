package be.kdg.client.service.interfaces;

import be.kdg.client.controller.dto.OrderClientDto;
import be.kdg.client.service.exception.*;

import java.util.UUID;

public interface OrderCrudServiceInterface {
    void createOrder(OrderClientDto orderDto) throws ProductNotPresentException;
    void createOrderViaExistingOrder(UUID orderId, UUID accountId) throws OrderNotFoundException, ProductNotPresentException, AccountNotFoundException;
    void confirmOrder(UUID orderId, UUID accountId) throws OrderNotFoundException, OrderStateException, OrderAlreadyConfirmedException;
    void cancelOrder(UUID orderId, UUID accountIdFromLoggedInUser) throws OrderNotFoundException, OrderStateException;
}
