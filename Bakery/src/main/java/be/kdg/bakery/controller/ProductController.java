package be.kdg.bakery.controller;
import be.kdg.bakery.domain.Ingredient;
import be.kdg.bakery.domain.Product;
import be.kdg.bakery.domain.Recipe;
import be.kdg.bakery.messaging.RabbitTopology;
import be.kdg.bakery.service.api.IngredientApiService;
import be.kdg.bakery.service.ProductService;
import be.kdg.bakery.service.RecipeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import java.util.*;

@Controller
public class ProductController {
    Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;
    private final RecipeService recipeService;
    private final IngredientApiService ingredientApiService;
    private final ObjectMapper objectMapper;
    private final RabbitTemplate rabbitTemplate;

    public ProductController(ProductService productService,
                             RecipeService recipeService,
                             IngredientApiService ingredientApiService,
                             ObjectMapper objectMapper,
                             RabbitTemplate rabbitTemplate) {
        this.productService = productService;
        this.recipeService = recipeService;
        this.ingredientApiService = ingredientApiService;
        this.objectMapper = objectMapper;
        this.rabbitTemplate = rabbitTemplate;
    }

    @GetMapping
    public String showProducts(Model model) {
        logger.info("Get all products");
        model.addAttribute("products", productService.retrieveAllProducts());
        logger.info("Products found");
        return "inventory";
    }

    @GetMapping("/newProduct")
    public String makeNewProduct(Model model) {
        model.addAttribute("ingredients", ingredientApiService.getAllIngredients());
        return "newProduct";
    }

    @GetMapping("/chosenProduct")
    public String showChosenProduct(Model model, @RequestParam String productName) {
        logger.info("Get product");

        try {
            Product product = productService.retrieveProduct(productName);
            if (product != null) {
                logger.info("Product found");
                Recipe recipe = recipeService.retrieveRecipe(product);
                product.setRecipe(recipe);
                List<Ingredient> ingredientList = product.getIngredients();
                model.addAttribute("product", product);
                model.addAttribute("ingredientList", ingredientList);
                logger.info("Ingredients found");
                model.addAttribute("ingredients", ingredientApiService.getAllIngredients());
            } else {
                logger.info("Product not found");
                return "redirect:/";
            }
        } catch (Exception e) {
            logger.error("Error while fetching product details: " + e.getMessage());
            model.addAttribute("error", true);
        }

        return "product";
    }



    @PostMapping("/deactivateProduct")
    public RedirectView deactivateProduct(@RequestParam String productName) throws JsonProcessingException {
        logger.info("Change activation");
        Product product = productService.retrieveProduct(productName);
        if (product.getDeactivated())
            product.setDeactivated(false);
        else
            product.setDeactivated(true);
        productService.saveProduct(product);
        rabbitTemplate.convertAndSend(RabbitTopology.TOPIC_EXCHANGE, "activateProduct.client", objectMapper.writeValueAsString(product.getProductNumber()));
        logger.info("Activation changed");
        return new RedirectView("/");
    }

}
