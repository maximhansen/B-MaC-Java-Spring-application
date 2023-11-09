package be.kdg.bakery.controller.api;

import be.kdg.bakery.controller.api.dto.ProductDto;
import be.kdg.bakery.domain.Ingredient;
import be.kdg.bakery.domain.Product;
import be.kdg.bakery.service.IngredientService;
import be.kdg.bakery.service.ProductService;
import be.kdg.bakery.service.api.IngredientApiService;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import org.slf4j.Logger;

import java.util.*;

@RestController
@RequestMapping("/api")
public class ProductRestController {
    Logger logger = LoggerFactory.getLogger(ProductRestController.class);

    private final ProductService productService;
    private final IngredientService ingredientService;
    private final IngredientApiService ingredientApiService;

    public ProductRestController(ProductService productService,
                                 IngredientService ingredientService, IngredientApiService ingredientApiService) {
        this.productService = productService;
        this.ingredientService = ingredientService;
        this.ingredientApiService = ingredientApiService;
    }

    @GetMapping("/AllProducts")
    public ResponseEntity<Collection<ProductDto>> getAllProducts() {
        logger.info("Get all products");
        var products = productService.retrieveAllProducts();
        if (!products.isEmpty()) {
            logger.info("Products found");
            List<ProductDto> productDtoList = new ArrayList<>();
            for (Product product : products) {
                ProductDto productDto = new ProductDto(
                        product.getProductNumber(),
                        product.getProductName(),
                        product.getDescription(),
                        product.getPrice(),
                        product.getDeactivated()
                );
                productDtoList.add(productDto);
            }
            return ResponseEntity.ok(productDtoList);
        } else {
            logger.warn("No products found");
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/unPricedProducts")
    public ResponseEntity<Collection<ProductDto>> getUnPricedProducts() {
        logger.info("Get unpriced products");
        var products = productService.retrieveAllProductsByPriceIsNull();
        if (!products.isEmpty()) {
            logger.info("Products found");
            List<ProductDto> productDtoList = new ArrayList<>();
            for (Product product : products) {
                ProductDto productDto = new ProductDto(
                        product.getProductNumber(),
                        product.getProductName(),
                        product.getDescription()
                );
                productDtoList.add(productDto);
            }
            return ResponseEntity.ok(productDtoList);
        } else {
            logger.warn("No products found");
            return ResponseEntity.noContent().build();
        }
    }


    @PostMapping("/postProduct")
    public RedirectView createProduct(
            @RequestParam("productName") String productName,
            @RequestParam("productDescription") String productDescription,
            @RequestParam("selectedIngredients") String selectedIngredients,
            @RequestParam("ingredientAmounts") String ingredientAmounts) {

        logger.info("Create product");
        String[] selectedIngredientArray = selectedIngredients.split(",");
        String[] ingredientAmountArray = ingredientAmounts.split(",");
        List<Ingredient> ingredientList = new ArrayList<>();

        for (int i = 0; i < selectedIngredientArray.length; i++) {
            Ingredient ingredient = new Ingredient(selectedIngredientArray[i], Double.parseDouble(ingredientAmountArray[i]));
            UUID ingredientNumber = ingredientApiService.getIngredientNumberByName(selectedIngredientArray[i]);
            ingredient.setIngredientNumber(ingredientNumber);
            ingredientList.add(ingredient);
            ingredientService.addIngredient(UUID.randomUUID(), ingredient);
            logger.info("Ingredient added");

        }
        productService.addProduct(UUID.randomUUID(), productName, productDescription, ingredientList);
        logger.info("Product added");
        return new RedirectView("/");
    }

    @GetMapping("/product/{productNumber}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable UUID productNumber) {
        logger.info("Get product by id");
        Optional<Product> product = productService.retrieveProductById(productNumber);
        if (product.isPresent()) {
            logger.info("Product found");
            ProductDto productDto = new ProductDto(
                    product.get().getProductNumber(),
                    product.get().getProductName(),
                    product.get().getDescription(),
                    product.get().getPrice(),
                    product.get().getDeactivated()
            );
            return ResponseEntity.ok(productDto);
        } else {
            logger.warn("Product not found");
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping ("/product/setPrice/{price}/{productNumber}")
    public ResponseEntity<?> setPrice(@PathVariable double price, @PathVariable UUID productNumber) {
        logger.info("Set price");
        try {
            productService.setPrice(price, productNumber);
            logger.info("Price set");
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.warn("Price not set");
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
