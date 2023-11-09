package be.kdg.client.service.account.exception;

public class AccountHasOrdersException extends RuntimeException{
    public AccountHasOrdersException(String message) {
        super(message);
    }
}
