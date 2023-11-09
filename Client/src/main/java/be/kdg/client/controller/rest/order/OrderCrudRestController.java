package be.kdg.client.controller.rest.order;

import be.kdg.client.controller.dto.OrderClientDto;
import be.kdg.client.controller.rest.security.SecurityUtils;
import be.kdg.client.service.exception.AccountNotFoundException;
import be.kdg.client.service.exception.OrderNotFoundException;
import be.kdg.client.service.exception.OrderStateException;
import be.kdg.client.service.exception.ProductNotPresentException;
import be.kdg.client.service.order.OrderCrudService;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/api/orders")
public class OrderCrudRestController {
    private final OrderCrudService orderCrudService;

    public OrderCrudRestController(OrderCrudService orderCrudService) {
        this.orderCrudService = orderCrudService;
    }

    @PostMapping("/create")
    @PreAuthorize("hasAnyAuthority('user')")
    public ResponseEntity<String> addOrder(@RequestBody @NotNull OrderClientDto orderClientDto) {
        try {
            orderClientDto.setAccountId(SecurityUtils.getAccountIdFromLoggedInUser());
            orderCrudService.createOrder(orderClientDto);
            log.info("Order created: " + SecurityUtils.getAccountIdFromLoggedInUser());
            return ResponseEntity.ok("Order created");
        } catch (AccountNotFoundException | ProductNotPresentException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/create/via-existing-order")
    @PreAuthorize("hasAnyAuthority('user')")
    public ResponseEntity<String> addOrderViaExistingOrder(@RequestParam @NotNull UUID orderId) {
        try {
            orderCrudService.createOrderViaExistingOrder(orderId, SecurityUtils.getAccountIdFromLoggedInUser());
            log.info("Order created via existing order: " + orderId);
            return ResponseEntity.ok("Order created via existing order");
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/confirm")
    @PreAuthorize("hasAnyAuthority('user')")
    public ResponseEntity<String> confirmOrder(@RequestParam @NotNull UUID orderId) {
        try {
            orderCrudService.confirmOrder(orderId, SecurityUtils.getAccountIdFromLoggedInUser());
            log.info("Order confirmed: " + orderId);
            return ResponseEntity.ok("Order confirmed");
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/cancel")
    @PreAuthorize("hasAnyAuthority('user')")
    public ResponseEntity<String> cancelOrder(@RequestParam @NotNull UUID orderId) {
        try {
            orderCrudService.cancelOrder(orderId, SecurityUtils.getAccountIdFromLoggedInUser());
            log.info("Order cancelled: " + orderId);
            return ResponseEntity.ok("Order cancelled");
        } catch (OrderNotFoundException | OrderStateException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
