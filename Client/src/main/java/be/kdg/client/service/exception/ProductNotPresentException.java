package be.kdg.client.service.exception;

public class ProductNotPresentException extends RuntimeException{
    public ProductNotPresentException(String message) {
        super(message);
    }
}
