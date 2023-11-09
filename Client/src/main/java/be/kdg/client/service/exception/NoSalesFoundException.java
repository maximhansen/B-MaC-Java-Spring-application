package be.kdg.client.service.exception;

public class NoSalesFoundException extends RuntimeException {
    public NoSalesFoundException(String message) {
        super(message);
    }
}
