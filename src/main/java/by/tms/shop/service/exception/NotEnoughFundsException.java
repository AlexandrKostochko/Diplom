package by.tms.shop.service.exception;

public class NotEnoughFundsException extends RuntimeException{

    public NotEnoughFundsException() {
    }

    public NotEnoughFundsException(String message) {
        super(message);
    }
}
