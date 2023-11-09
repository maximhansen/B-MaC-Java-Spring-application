package be.kdg.bakery.domain;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID recipeNumber;
    @OneToOne
    private Product product;

    @Column
    Boolean isFinalised = false;

    @ElementCollection
    @CollectionTable(name = "recipe_steps", joinColumns = @JoinColumn(name = "recipe_id"))
    @Column(name = "step")
    private List<String> steps;

    public Recipe() {
    }

    public Recipe(UUID recipeNumber, Product product, List<String> steps) {
        this.recipeNumber = recipeNumber;
        this.product = product;
        this.steps = steps;
    }

    public UUID getRecipeNumber() {
        return recipeNumber;
    }

    public void setRecipeNumber(UUID recipeNumber) {
        this.recipeNumber = recipeNumber;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<String> getSteps() {
        return steps;
    }

    public void setSteps(List<String> steps) {
        this.steps = steps;
    }

    public Boolean getFinalised() {
        return isFinalised;
    }

    public void setFinalised(Boolean finalised) {
        isFinalised = finalised;
    }
}
