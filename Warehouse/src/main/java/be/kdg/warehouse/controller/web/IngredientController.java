package be.kdg.warehouse.controller.web;

import be.kdg.warehouse.service.IngredientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class IngredientController {
    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping("/ingredients")
    public String showIngredients(Model model) {
        model.addAttribute("ingredients", ingredientService.retrieveAllIngredients());
        return "ingredients";
    }
}
