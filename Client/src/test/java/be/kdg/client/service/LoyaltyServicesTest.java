package be.kdg.client.service;

import be.kdg.client.controller.dto.OrderClientDto;
import be.kdg.client.controller.dto.ProductDto;
import be.kdg.client.domain.order.Order;
import be.kdg.client.service.exception.OrderAlreadyConfirmedException;
import be.kdg.client.service.exception.OrderNotFoundException;
import be.kdg.client.service.exception.OrderStateException;
import be.kdg.client.service.loyalty.LoyaltyService;
import be.kdg.client.service.order.OrderCrudService;
import be.kdg.client.service.order.OrderService;
import be.kdg.client.service.product.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class LoyaltyServicesTest {

    @Autowired
    private LoyaltyService loyaltyServices;
    @Autowired
    private OrderCrudService orderCrudService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;

    /**
     * Test to check if the loyalty points are added to the account when the order is confirmed.
     * precondition: the order is not confirmed yet
     * precondition: the current client has 500 loyalty points
     * precondition: the order has a price of 10 euro (1 loyalty point)
     * post condition: the order is confirmed and the client has 501 loyalty points
     */
    @Test
    @Transactional
    public void whenIConfirmedMyOrderIWantToGetLoyaltyPointsToMyAccount() throws OrderNotFoundException, OrderAlreadyConfirmedException, OrderStateException {
        orderCrudService.confirmOrder(UUID.fromString("8b8691e7-0287-4f1d-bbaf-1e10be917e96"), UUID.fromString("3dc99fa5-90aa-48e4-b76d-b45bc65c6d80"));
        int loyaltyPointsAfter = Integer.parseInt(loyaltyServices.getLoyaltyPoints(UUID.fromString("3dc99fa5-90aa-48e4-b76d-b45bc65c6d80")));
        assertEquals(loyaltyPointsAfter, 501);
    }

    /**
     * Test to check if the loyalty type is changed when the order is confirmed. When the client has 1000 loyalty points, the loyalty type should be changed to SILVER.
     * precondition: the order is not confirmed yet
     * precondition: the current client has 500 loyalty points
     * precondition: the order has a price of 5000 euro (500 loyalty points)
     * post condition: the order is confirmed and the client has a loyalty type of SILVER
     */
    @Test
    @Transactional
    public void whenIConfirmedMyOrderIWantToGoToAnotherLoyaltyType() throws OrderNotFoundException, OrderAlreadyConfirmedException, OrderStateException {
        orderCrudService.confirmOrder(UUID.fromString("d1f6a9d2-7df5-4a78-8e1d-d20e8125e8a2"), UUID.fromString("3dc99fa5-90aa-48e4-b76d-b45bc65c6d80"));
        String loyaltyTypeAfter = loyaltyServices.getLoyalty(UUID.fromString("3dc99fa5-90aa-48e4-b76d-b45bc65c6d80"));
        assertEquals(loyaltyTypeAfter, "Silver");
    }

    /**
     * Test to check if the client has a total order price with his discount.
     * precondition: the order is not confirmed yet
     * precondition: the current client has SILVER loyalty type
     * precondition: the order has a price of 2 euro - 5% discount = 1.9 euro
     * post condition: the order is placed and the client has a total order price of 1.9 euro and not 2 euro
     */
    @Test
    @Transactional
    public void whenIPlaceAnOrderIWantMyDiscountToBeCalculated() throws OrderNotFoundException {
        ProductDto productDto1 = new ProductDto();
        productDto1.setProductNumber(UUID.fromString("6f8d5a2b-aa4e-4b71-a33d-182ccda1d3d0"));
        productDto1.setQuantity(2);
        OrderClientDto orderClientDto = new OrderClientDto();
        orderClientDto.setAccountId(UUID.fromString("b03a604f-112d-4b67-a0a0-5aa228d8877f"));
        orderClientDto.setProductDtoList(List.of(productDto1));

        orderCrudService.createOrder(orderClientDto);
        List<Order> orders = orderService.getAllOrdersByAccountId(UUID.fromString("b03a604f-112d-4b67-a0a0-5aa228d8877f"));
        Order order = orders.get(orders.size() - 1);

        double price = productService.getActiveProductById(UUID.fromString("6f8d5a2b-aa4e-4b71-a33d-182ccda1d3d0")).getPrice();
        Double totalPrice = price * 2;

        assertNotEquals(order.getTotalPrice(), totalPrice);
    }
}
