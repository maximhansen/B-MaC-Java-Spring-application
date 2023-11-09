package be.kdg.bakery.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class Product{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID productNumber;

    @Column(unique = true)
    String productName;

    @Column(nullable = true)
    Double price;

    @Column
    @NotNull
    Boolean isDeactivated;

    @Column(length = 200)
    String description;

    @OneToOne(mappedBy="product")
    @JsonIgnore
    Recipe recipe;

    @OneToMany
    @JoinColumn(name = "productNumber")
    List<Ingredient> ingredients;

    public Product() {
        this.isDeactivated = true;
        this.recipe = new Recipe(UUID.randomUUID(), this, new ArrayList<>());
    }

    public Product(UUID productNumber, String productName, String description, List<Ingredient> ingredients) {
        this.productNumber = productNumber;
        this.productName = productName;
        this.description = description;
        this.ingredients = ingredients;
        this.isDeactivated = true;
        this.recipe = new Recipe(UUID.randomUUID(), this, new ArrayList<>());
    }

    public Product(UUID productNumber, String productName, String description) {
        this.productNumber = productNumber;
        this.productName = productName;
        this.description = description;
        this.isDeactivated = true;
        this.recipe = new Recipe(UUID.randomUUID(), this, new ArrayList<>());
    }

    public Product(String productName) {
        this(null, productName, null);
    }

    public Product(String productName, String description) {
        this(null, productName, description);
    }

    public UUID getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(UUID productNumber) {
        this.productNumber = productNumber;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public Boolean getDeactivated() {
        return isDeactivated;
    }

    public void setDeactivated(Boolean deactivated) {
        isDeactivated = deactivated;
    }
}



