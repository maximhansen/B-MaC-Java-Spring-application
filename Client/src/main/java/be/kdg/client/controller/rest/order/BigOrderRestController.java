package be.kdg.client.controller.rest.order;

import be.kdg.client.controller.dto.OrderClientDto;
import be.kdg.client.controller.rest.order.xmlExtractor.OrderExtractor;
import be.kdg.client.controller.rest.order.xmlExtractor.XmlParser;
import be.kdg.client.service.order.OrderCrudService;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;

@RestController
@Slf4j
@RequestMapping("/api/orders")
public class BigOrderRestController {
    private final OrderCrudService orderCrudService;

    public BigOrderRestController(OrderCrudService orderCrudService) {
        this.orderCrudService = orderCrudService;
    }

    @PostMapping("/upload")
    @PreAuthorize("hasAnyAuthority('user')")
    public ResponseEntity<String> uploadOrderBatch(@RequestBody @NotNull MultipartFile file) {
        try {
            OrderClientDto orderClientDto = getDataFromXml(file);
            addOrder(orderClientDto);
            return ResponseEntity.ok("Order created");
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
        }
    }

    private OrderClientDto getDataFromXml(MultipartFile file) throws Exception {
        byte[] fileBytes = file.getBytes();
        Document document = XmlParser.parseXml(fileBytes);
        OrderClientDto orderClientDto = OrderExtractor.extractItems(document);
        orderClientDto.setAccountId(OrderExtractor.extractAccountId(document));
        return orderClientDto;
    }

    private void addOrder(OrderClientDto orderClientDto) {
        orderCrudService.createOrder(orderClientDto);
    }
}
