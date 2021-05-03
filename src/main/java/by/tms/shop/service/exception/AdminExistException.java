package by.tms.shop.service.exception;

public class AdminExistException extends RuntimeException{

    public AdminExistException() {
    }

    public AdminExistException(String message) {
        super(message);
    }
}
