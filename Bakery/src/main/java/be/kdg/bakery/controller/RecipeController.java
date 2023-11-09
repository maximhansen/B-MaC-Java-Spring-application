package be.kdg.bakery.controller;
import be.kdg.bakery.controller.api.RecipeRestController;
import be.kdg.bakery.controller.api.dto.ProductDto;
import be.kdg.bakery.domain.Product;
import be.kdg.bakery.domain.Recipe;
import be.kdg.bakery.messaging.RabbitTopology;
import be.kdg.bakery.service.ProductService;
import be.kdg.bakery.service.RecipeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
public class RecipeController {
    Logger logger = LoggerFactory.getLogger(RecipeRestController.class);

    private final RecipeService recipeService;
    private final ProductService productService;
    private final RabbitTemplate rabbitTemplate;

    public RecipeController(RecipeService recipeService,
                            ProductService productService,
                            RabbitTemplate rabbitTemplate) {
        this.recipeService = recipeService;
        this.productService = productService;
        this.rabbitTemplate = rabbitTemplate;
    }

    // TODO DELETEMAPPING?

    @PostMapping("removeStep")
    public RedirectView removeStep(@RequestParam UUID recipeNumber, @RequestParam int stepIndex, @RequestParam String productName) {
        logger.info("Remove step");

        Optional<Recipe> optionalRecipe = recipeService.retrieveRecipeById(recipeNumber);

        if (optionalRecipe.isPresent()) {
            Recipe recipe = optionalRecipe.get();
            List<String> steps = recipe.getSteps();

            if (isValidStepIndex(stepIndex, steps)) {
                steps.remove(stepIndex);
                recipeService.saveRecipe(recipe);
                logger.info("Step removed");
            } else {
                logger.warn("Invalid stepIndex: {}", stepIndex);
            }
        } else {
            logger.warn("Recipe not found for recipeNumber: {}", recipeNumber);
        }

        return new RedirectView("/chosenProduct?productName=" + productName);
    }

    private boolean isValidStepIndex(int stepIndex, List<String> steps) {
        return stepIndex >= 0 && stepIndex < steps.size();
    }

    @PostMapping("addStep")
    public RedirectView addStep(@RequestParam UUID recipeNumber, @RequestParam String productName, @RequestParam String step) {
        logger.info("Add step");
        Optional<Recipe> optionalRecipe = recipeService.retrieveRecipeById(recipeNumber);
        if (optionalRecipe.isPresent()) {
            logger.info("Recipe found");
            Recipe recipe = optionalRecipe.get();
            recipe.getSteps().add(step);
            recipeService.saveRecipe(recipe);
            logger.info("Step added");
        }
        return new RedirectView("/chosenProduct?productName=" + productName);
    }


    @PostMapping("finaliseRecipe")
    public RedirectView finaliseRecipe(@RequestParam UUID recipeNumber, @RequestParam UUID productNumber) {
        logger.info("Finalise recipe");
        Optional<Recipe> optionalRecipe = recipeService.retrieveRecipeById(recipeNumber);
        if (optionalRecipe.isPresent()) {
            logger.info("Recipe found");
            Recipe recipe = optionalRecipe.get();
            recipe.setFinalised(true);
            recipeService.saveRecipe(recipe);
            logger.info("Recipe finalised");
        }

        Optional<Product> product = productService.retrieveProductById(productNumber);
        if (product.isPresent()) {
            logger.info("Product found");
            ProductDto productDto = new ProductDto(
                    product.get().getProductNumber(),
                    product.get().getProductName(),
                    product.get().getIngredients()
            );
            rabbitTemplate.convertAndSend(RabbitTopology.TOPIC_EXCHANGE, "say.#.product", productDto);
            rabbitTemplate.convertAndSend(RabbitTopology.TOPIC_EXCHANGE, "newProduct.client", productDto);
            logger.info("Product sent to rabbitmq");
        }
        return new RedirectView("/chosenProduct?productName=" + product.get().getProductName());
    }
}
