package be.kdg.client.controller.rest;

import be.kdg.client.domain.account.Account;
import be.kdg.client.domain.order.Order;
import be.kdg.client.service.account.AccountService;
import be.kdg.client.service.product.ProductService;
import be.kdg.client.service.sale.SaleService;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/api/sales")
public class SaleRestController {

    private final ProductService productService;
    private final SaleService saleService;
    private final AccountService accountService;

    public SaleRestController(SaleService saleService, ProductService productService, AccountService accountService) {
        this.saleService = saleService;
        this.productService = productService;
        this.accountService = accountService;
    }

    @GetMapping("/date")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<String> getSalesByDate(@RequestParam @NotNull LocalDate date) {
        try {
            List<Order> orders = saleService.generateSalesByDate(date);
            log.info("Sales for date: " + date + " generated!");
            makeDocument(orders, date);
            return ResponseEntity.ok("Sales for date: " + date + " generated!");
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/product")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<String> getSalesByProduct(@RequestParam @NotNull UUID productId) {
        try {
            List<Order> orders = saleService.generateSalesByProduct(productId);
            String productName = productService.getProductById(productId).getProductName();
            log.info("Sales for product: " + productName + " generated!");
            makeDocument(orders, productId);
            return ResponseEntity.ok("Sales for product: " + productName+ " generated!");
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/client")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<String> getAllOrdersByClient(@RequestParam @NotNull Long clientId) {
        try {
            List<Order> orders = saleService.getAllOrderByClient(clientId);
            Account account = accountService.getAccountByClientId(clientId);
            String clientName = account.getName() + " " + account.getLastName();
            log.info("Sales for client: " + clientName + " generated!");
            makeDocument(orders, clientName);
            return ResponseEntity.ok("Sales for client: " + clientName + " generated!");
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private void makeDocument(List<Order> orders, Object object) {
        String FILE_PATH = "src/main/resources/generatedFiles/";
        try(FileWriter writer = new FileWriter(FILE_PATH + object.toString() + "-" + UUID.randomUUID()+ ".csv")) {
            writer.write("Order ID, Total Price, Order Date, Special Instruction, ClientID\n");
            for(Order order : orders) {
                writer.write(order.getOrderId() + ", " + order.getTotalPrice() + ", "
                        + order.getOrderDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + ", "
                        + order.getSpecialInstructions() + ", "
                        + order.getClient().getId() + "\n");
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
