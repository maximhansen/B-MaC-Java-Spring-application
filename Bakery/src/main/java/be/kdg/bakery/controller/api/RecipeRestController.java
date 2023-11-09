package be.kdg.bakery.controller.api;

import be.kdg.bakery.controller.api.dto.RecipeDto;
import be.kdg.bakery.domain.Product;
import be.kdg.bakery.domain.Recipe;
import be.kdg.bakery.service.ProductService;
import be.kdg.bakery.service.RecipeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/api")
public class RecipeRestController {
    Logger logger = LoggerFactory.getLogger(RecipeRestController.class);

    private final RecipeService recipeService;
    private final ProductService productService;

    public RecipeRestController(RecipeService recipeService, ProductService productService) {
        this.recipeService = recipeService;
        this.productService = productService;
    }

    @PostMapping("/add-recipe")
    public RedirectView addRecipeToProduct(@RequestParam String productName, @ModelAttribute RecipeDto recipeDTO) {
        logger.info("Add recipe to product");
        Product product = productService.retrieveProduct(productName);

        if (product == null){
            logger.info("Product not found");
            return new RedirectView("/");
        }

        Recipe recipe = new Recipe();
        recipe.setSteps(recipeDTO.getSteps());
        recipe.setProduct(product);
        recipeService.saveRecipe(recipe);
        logger.info("Recipe created");

        product.setRecipe(recipe);
        productService.saveProduct(product);
        return new RedirectView("/chosenProduct?productName=" + productName);

    }
}
