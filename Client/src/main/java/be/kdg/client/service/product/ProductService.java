package be.kdg.client.service.product;

import be.kdg.client.controller.dto.ProductDto;
import be.kdg.client.service.exception.NoProductsFoundException;
import be.kdg.client.service.exception.ProductNotPresentException;
import be.kdg.client.service.product.rest.ProductRestService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRestService productRestService;

    public ProductService(ProductRestService productRestService) {
        this.productRestService = productRestService;
    }


    public Iterable<ProductDto> retrieveAllActiveProducts() throws NoProductsFoundException {
        Optional<List<ProductDto>> productDtoList =  productRestService.getAllProducts();

        if (productDtoList.isPresent()) {
            return productDtoList.get().stream()
                    .filter(productDto -> !productDto.getDeactivated()).collect(Collectors.toList());
        } else {
            throw new NoProductsFoundException("No products found!");
        }
    }

    public ProductDto getActiveProductById(UUID productNumber) throws ProductNotPresentException {
        Optional<ProductDto> product = productRestService.getProductById(productNumber).stream()
                .filter(productDto -> !productDto.getDeactivated())
                .findFirst();

        if(product.isPresent()) {
            return product.get();
        } else {
            throw new ProductNotPresentException("Product with id " + productNumber + " not found!");
        }
    }

    public ProductDto getProductById(UUID productNumber) throws ProductNotPresentException {
        Optional<ProductDto> product = productRestService.getProductById(productNumber).stream()
                .findFirst();

        if(product.isPresent()) {
            return product.get();
        } else {
            throw new ProductNotPresentException("Product with id " + productNumber + " not found!");
        }
    }

    public Iterable<ProductDto> retrieveNewProducts() {
        return productRestService.getNewProducts();
    }
}
