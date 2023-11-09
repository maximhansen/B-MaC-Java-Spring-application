package be.kdg.client.controller.rest.order;

import be.kdg.client.controller.dto.FilterOrderByDateAndStatusDto;
import be.kdg.client.controller.dto.OrderDto;
import be.kdg.client.controller.rest.security.SecurityUtils;
import be.kdg.client.domain.order.Order;
import be.kdg.client.domain.order.OrderLine;
import be.kdg.client.service.dto.TransferDtoService;
import be.kdg.client.service.exception.OrderNotFoundException;
import be.kdg.client.service.order.OrderService;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/api/orders")
public class OrderRestController {
    private final OrderService orderService;
    private final TransferDtoService transferDtoService;
    public OrderRestController(OrderService orderService, TransferDtoService transferDtoService) {
        this.orderService = orderService;
        this.transferDtoService = transferDtoService;
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('user')")
    public ResponseEntity<?> getOrdersByAccountId() {
        try {
            List<Order> orderList = orderService.getAllOrdersByAccountId(SecurityUtils.getAccountIdFromLoggedInUser());
            List<OrderDto> orderDtoList = transferDtoService.ordersToOrderDtos(orderList);
            return ResponseEntity.ok(orderDtoList);
        } catch (OrderNotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("all/unconfirmed")
    @PreAuthorize("hasAnyAuthority('user')")
    public ResponseEntity<?> getConfirmedOrdersByAccountId() {
        try {
            List<Order> orderList = orderService.getAllUnconfirmedOrdersByAccountId(SecurityUtils.getAccountIdFromLoggedInUser());
            List<OrderDto> orderDtoList = transferDtoService.ordersToOrderDtos(orderList);
            return ResponseEntity.ok(orderDtoList);
        } catch (OrderNotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("all/confirmed")
    public ResponseEntity<?> getConfirmedOrders() {
        try {
            List<Order> orderList = orderService.retrieveAllConfirmedOrders();
            List<OrderDto> orderDtoList = transferDtoService.ordersToOrderDtos(orderList);
            return ResponseEntity.ok(orderDtoList);
        } catch (OrderNotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("all/sorted")
    @PreAuthorize("hasAnyAuthority('user')")
    public ResponseEntity<?> getOrdersByAccountIdSorted(@RequestBody FilterOrderByDateAndStatusDto filterOrderByDateAndStatusDto) {
        try {
            List<Order> orderList = orderService.getAllOrdersByAccountIdSorted(SecurityUtils.getAccountIdFromLoggedInUser(), filterOrderByDateAndStatusDto);
            List<OrderDto> orderDtoList = transferDtoService.ordersToOrderDtos(orderList);
            return ResponseEntity.ok(orderDtoList);
        } catch (OrderNotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/state")
    @PreAuthorize("hasAnyAuthority('user')")
    public ResponseEntity<String> getOrderStateOfOrder(@RequestParam @NotNull UUID orderId) {
        try {
            String orderState = orderService.getOrderStateOfOrder(orderId, SecurityUtils.getAccountIdFromLoggedInUser());
            return ResponseEntity.ok(orderState);
        } catch (OrderNotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/getOrderLines/{orderId}")
    public ResponseEntity<List<OrderLine>> getOrderById(@PathVariable UUID orderId) throws OrderNotFoundException {
        List<OrderLine> orderLineList = orderService.getOrderLinesFromOrder(orderId);
        return ResponseEntity.ok(orderLineList);
    }
}
