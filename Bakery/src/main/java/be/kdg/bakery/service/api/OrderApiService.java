package be.kdg.bakery.service.api;

import be.kdg.bakery.controller.api.dto.OrderDto;
import be.kdg.bakery.controller.api.dto.OrderLineDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@Service
public class OrderApiService {
    private final RestTemplate restTemplate;

    public OrderApiService() {
        this.restTemplate = new RestTemplate();
    }

    public List<OrderDto> getAllConfirmedOrders() {
        ResponseEntity<List<OrderDto>> response = restTemplate.exchange(
                "http://localhost:8082/api/orders/all/confirmed",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );
        if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
            return null;
        }
        return response.getBody();
    }

    public List<OrderLineDto> getOrderlinesOfOrder(@RequestParam UUID orderId) {
        ResponseEntity<List<OrderLineDto>> response= restTemplate.exchange(
                "http://localhost:8082/api/orders/getOrderLines/" + orderId,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );
        if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
            return null;
        }
        return response.getBody();
    }

}
