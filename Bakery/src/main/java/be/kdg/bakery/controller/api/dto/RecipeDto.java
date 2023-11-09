package be.kdg.bakery.controller.api.dto;

import java.util.List;

public class RecipeDto {
    private List<String> steps;

    public RecipeDto() {
    }

    public List<String> getSteps() {
        return steps;
    }

    public void setSteps(List<String> steps) {
        this.steps = steps;
    }
}
