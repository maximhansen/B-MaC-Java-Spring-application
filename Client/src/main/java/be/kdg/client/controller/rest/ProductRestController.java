package be.kdg.client.controller.rest;

import be.kdg.client.controller.dto.ProductDto;
import be.kdg.client.service.exception.NoProductsFoundException;
import be.kdg.client.service.product.ProductService;
import be.kdg.client.service.product.rest.ProductRestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/api/product")
public class ProductRestController {

    private final ProductService productService;
    private final ProductRestService productRestService;

    public ProductRestController(ProductService productService, ProductRestService productRestService) {
        this.productService = productService;
        this.productRestService = productRestService;
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('admin', 'user')")
    public ResponseEntity<?> getAllProducts() {
        try {
            return ResponseEntity.ok(productService.retrieveAllActiveProducts());
        } catch (NoProductsFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/getNewProducts")
    @PreAuthorize("hasAuthority('user')")
    public ResponseEntity<Iterable<ProductDto>> getNewProducts() {
        return ResponseEntity.ok(productService.retrieveNewProducts());
    }

    @PostMapping("setPrice/{price}/{productNumber}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<?> setPrice(@PathVariable double price, @PathVariable UUID productNumber) {
        try {
            productRestService.setPrice(price, productNumber);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
