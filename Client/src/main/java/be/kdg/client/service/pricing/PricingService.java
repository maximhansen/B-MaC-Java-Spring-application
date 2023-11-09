package be.kdg.client.service.pricing;

import be.kdg.client.controller.dto.ProductDto;
import be.kdg.client.service.exception.ProductNotPresentException;
import be.kdg.client.service.loyalty.LoyaltyService;
import be.kdg.client.service.product.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PricingService {

    private final ProductService productService;
    private final LoyaltyService loyaltyService;

    public PricingService(ProductService productService, LoyaltyService loyaltyService) {
        this.productService = productService;
        this.loyaltyService = loyaltyService;
    }

    public Double getTotalPriceInclDiscount(List<ProductDto> productDtoList, Long clientId) {
        return roundTwoDigitsAfterDecimalPoint(calculateTotalPrice(productDtoList) * (1 - getDiscount(clientId)));
    }

    private Double calculateTotalPrice(List<ProductDto> productDtoList) throws ProductNotPresentException {
        double totalPrice = 0.0;
        for (ProductDto productDto : productDtoList) {
            ProductDto product = productService.getActiveProductById(productDto.getProductNumber());
            totalPrice += product.getPrice() * productDto.getQuantity();
        }
        return totalPrice;
    }

    private Double getDiscount(Long id) {
        return loyaltyService.getDiscount(id);
    }

    private Double roundTwoDigitsAfterDecimalPoint(Double number) {
        return Math.round(number * 100.0) / 100.0;
    }
}
