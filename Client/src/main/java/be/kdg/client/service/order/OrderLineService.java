package be.kdg.client.service.order;

import be.kdg.client.controller.dto.ProductDto;
import be.kdg.client.domain.order.Order;
import be.kdg.client.domain.order.OrderLine;
import be.kdg.client.repository.order.OrderLineRepository;
import be.kdg.client.service.exception.ProductNotPresentException;
import be.kdg.client.service.product.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderLineService {
    private final OrderLineRepository orderLineRepository;
    private final ProductService productService;

    public OrderLineService(OrderLineRepository orderLineRepository, ProductService productService) {
        this.orderLineRepository = orderLineRepository;
        this.productService = productService;
    }

    public void addOrderliness(List<ProductDto> productDtoList, Order order) throws ProductNotPresentException {
        if (productDtoList.isEmpty()) {
            throw new ProductNotPresentException("No products in order");
        }
        for (ProductDto productDto : productDtoList) {
            ProductDto product = productService.getActiveProductById(productDto.getProductNumber());
            OrderLine orderLine = new OrderLine(productDto.getQuantity(), product.getProductNumber(), productDto.getSpecialRequest());
            orderLine.setOrder(order);
            orderLineRepository.save(orderLine);
        }
    }
}
