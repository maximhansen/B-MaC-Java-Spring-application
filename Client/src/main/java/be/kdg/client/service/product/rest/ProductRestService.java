package be.kdg.client.service.product.rest;

import be.kdg.client.controller.dto.ProductDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductRestService {
    private final RestTemplate restTemplate;

    public ProductRestService() {
        this.restTemplate = new RestTemplate();
    }

    public Optional<List<ProductDto>> getAllProducts() {
        String url = "http://localhost:8080/api/AllProducts";
        try {
            ResponseEntity<List<ProductDto>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {
                    }
            );
            return Optional.ofNullable(response.getBody());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<ProductDto> getProductById(UUID productNumber) {
        String url = "http://localhost:8080/api/product/" + productNumber;
        try {
            ResponseEntity<ProductDto> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {
                    }
            );
            return Optional.ofNullable(response.getBody());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public List<ProductDto> getNewProducts() {
        String url = "http://localhost:8080/api/unPricedProducts";
        ResponseEntity<List<ProductDto>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );
        return response.getBody();
    }

    public void setPrice(double price, UUID productNumber){
        String url = "http://localhost:8080/api/product/setPrice/"+ price+ "/" + productNumber;
        ResponseEntity<?> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                null,
                new ParameterizedTypeReference<>() {
                }
        );
    }
}
