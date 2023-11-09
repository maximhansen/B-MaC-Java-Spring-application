package be.kdg.bakery.service;

import be.kdg.bakery.domain.Ingredient;
import be.kdg.bakery.domain.Product;
import be.kdg.bakery.repository.ProductRepository;
import be.kdg.bakery.repository.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductService {
    private final ProductRepository repository;
    private final RecipeRepository recipeRepository;

    public ProductService(ProductRepository productRepository, RecipeRepository recipeRepository) {
        this.repository = productRepository;
        this.recipeRepository = recipeRepository;
    }

    public Collection<Product> retrieveAllProducts() {
        return repository.findAllBy();
    }

    public Product retrieveProduct(String productName) {
        return repository.findByProductName(productName).orElse(null);
    }

    public void addProduct(UUID productNumber, String productName, String description, List<Ingredient> ingredientList) {
        Product product = new Product(productNumber, productName, description, ingredientList);
        repository.save(product);
        recipeRepository.save(product.getRecipe());
    }

    public void saveProduct(Product existingProduct) {
        repository.save(existingProduct);
    }

    public Optional<Product> retrieveProductById(UUID productNumber) {
        return repository.findById(productNumber);
    }

    public Collection<Product> retrieveAllProductsByPriceIsNull() {
        return repository.findAllByPriceIsNull();
    }

    public void setPrice(double price, UUID productNumber) {
        Product product = repository.findByProductNumber(productNumber);
        product.setPrice(price);
        repository.save(product);
    }


}
