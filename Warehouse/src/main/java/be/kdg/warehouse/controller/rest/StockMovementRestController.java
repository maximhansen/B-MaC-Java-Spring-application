package be.kdg.warehouse.controller.rest;

import be.kdg.warehouse.service.StockMovementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stock")
@Slf4j
public class StockMovementRestController {
    private final StockMovementService stockMovementService;

    public StockMovementRestController(StockMovementService stockMovementService) {
        this.stockMovementService = stockMovementService;
    }
    @PostMapping
    public HttpStatus addStockToIngredient(@RequestParam double amount, @RequestParam String name) {
        log.info("Adding stock to ingredient");
        try {
            stockMovementService.addStockToIngredient(amount, name);
            return HttpStatus.OK;
        } catch (Exception e) {
            log.error("An error occurred while adding stock to the ingredient: " + e.getMessage());
            return HttpStatus.NOT_FOUND;
        }
    }

}
