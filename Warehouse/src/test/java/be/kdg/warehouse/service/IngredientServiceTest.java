package be.kdg.warehouse.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class IngredientServiceTest {

    @Autowired
    private IngredientService ingredientService;

    @Test
    public void testRetrieveIngredient() {
        var ingredient = ingredientService.retrieveIngredient("Butter");
        assertNotNull(ingredient);
    }
}
