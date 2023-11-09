package be.kdg.bakery.controller;

import be.kdg.bakery.domain.Ingredient;
import be.kdg.bakery.domain.Product;
import be.kdg.bakery.service.api.IngredientApiService;
import be.kdg.bakery.service.IngredientService;
import be.kdg.bakery.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.UUID;

@Controller
public class IngredientController {
    Logger logger = LoggerFactory.getLogger(IngredientController.class);

    private final IngredientApiService ingredientApiService;
    private final IngredientService ingredientService;
    private final ProductService productService;

    public IngredientController(IngredientApiService ingredientApiService, IngredientService ingredientService, ProductService productService) {
        this.ingredientApiService = ingredientApiService;
        this.ingredientService = ingredientService;
        this.productService = productService;
    }

    @GetMapping("/ingredients")
    public String displayIngredients(Model model) {
        logger.info("Get all ingredients");
        try {
            List<Ingredient> ingredients = ingredientApiService.getAllIngredients();
            logger.info("Ingredients found");
            model.addAttribute("ingredients", ingredients);
            model.addAttribute("error", false);
        } catch (Exception e) {
            logger.error("Error while getting ingredients from API: " + e.getMessage());
            model.addAttribute("error", true);
        }
        return "ingredient-list";
    }


    //TODO DeleteMapping ????
    @PostMapping("/deleteIngredient")
    public RedirectView deleteIngredient(@RequestParam UUID ingredientNumber, @RequestParam UUID productNumber) {
        logger.info("Delete ingredient");
        if (ingredientService.retrieveIngredientById(ingredientNumber).isPresent()) {
            logger.info("Ingredient found");
            Product product = productService.retrieveProductById(productNumber).get();
            ingredientService.deleteIngredient(ingredientNumber);
            logger.info("Ingredient deleted");
            return new RedirectView("/chosenProduct?productName=" + product.getProductName());
        } else {
            logger.info("Ingredient not found");
            return new RedirectView("/");
        }
    }

    @GetMapping("editIngredient")
    public String editIngredient(@RequestParam UUID ingredientNumber, Model model) {
        logger.info("Edit ingredient");
        Ingredient ingredient = ingredientService.retrieveIngredientById(ingredientNumber).get();
        logger.info("Ingredient found");
        model.addAttribute("ingredient", ingredient);
        return "editIngredient";
    }


    @PostMapping("/updateIngredient")
    public RedirectView updateIngredient(@RequestParam UUID ingredientNumber,
                                         @RequestParam UUID productNumber,
                                         @RequestParam int newAmount) {
        logger.info("Update ingredient");
        if (ingredientService.retrieveIngredientById(ingredientNumber).isPresent()) {
            logger.info("Ingredient found");
            Product product = productService.retrieveProductById(productNumber).get();
            Ingredient ingredient = ingredientService.retrieveIngredientById(ingredientNumber).get();
            ingredient.setAmount(newAmount);
            ingredientService.updateIngredient(ingredient);
            logger.info("Ingredient updated");
            return new RedirectView("/chosenProduct?productName=" + product.getProductName());
        } else {
            logger.info("Ingredient not found");
            return new RedirectView("/");
        }
    }

    @PostMapping("/addSingleIngredient")
    public RedirectView addIngredient(@RequestParam UUID productNumber, @RequestParam String ingredientName, @RequestParam int ingredientAmount) {
        logger.info("Add ingredient");
        if (productService.retrieveProductById(productNumber).isPresent()) {
            logger.info("Product found");
            Product product = productService.retrieveProductById(productNumber).get();
            Ingredient ingredient = ingredientApiService.getIngredientByName(ingredientName);
            logger.info("Ingredient found");
            ingredient.setProduct(product.getProductNumber());
            ingredient.setAmount(ingredientAmount);
            ingredient.setIngredientNumber(ingredientApiService.getIngredientNumberByName(ingredientName));
            ingredientService.addIngredient(UUID.randomUUID(), ingredient);
            logger.info("Ingredient added");
            return new RedirectView("/chosenProduct?productName=" + product.getProductName());
        } else {
            logger.info("Product not found");
            return new RedirectView("/");
        }
    }
}
