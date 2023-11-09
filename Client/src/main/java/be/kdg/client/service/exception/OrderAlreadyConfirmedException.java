package be.kdg.client.service.exception;

public class OrderAlreadyConfirmedException extends Exception {
    public OrderAlreadyConfirmedException(String message) {
        super(message);
    }
}
