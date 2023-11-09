package be.kdg.bakery.service.api;
import be.kdg.bakery.domain.Ingredient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@Service
public class IngredientApiService {
    private final RestTemplate restTemplate;
    private final String url = "http://localhost:8081/api/ingredients?name=";

    public IngredientApiService() {
        this.restTemplate = new RestTemplate();
    }

    public List<Ingredient> getAllIngredients() {
        ResponseEntity<List<Ingredient>> response = restTemplate.exchange(
                "http://localhost:8081/api/ingredients/all",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Ingredient>>() {}
        );
        return response.getBody();
    }

    public Ingredient getIngredientByName(String ingredientName) {
        ResponseEntity<Ingredient> response = restTemplate.exchange(
                "http://localhost:8081/api/ingredients?name=" + ingredientName,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Ingredient>() {}
        );
        return response.getBody();
    }

    public UUID getIngredientNumberByName(String ingredientName){
        ResponseEntity<UUID> response = restTemplate.exchange(
                "http://localhost:8081/api/ingredients/number?name=" + ingredientName,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<UUID>() {}
        );
        return response.getBody();
    }

}
