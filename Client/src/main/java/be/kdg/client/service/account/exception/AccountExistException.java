package be.kdg.client.service.account.exception;

public class AccountExistException extends RuntimeException {
    public AccountExistException(String message) {
        super(message);
    }
}
