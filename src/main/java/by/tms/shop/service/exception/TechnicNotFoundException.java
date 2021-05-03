package by.tms.shop.service.exception;

public class TechnicNotFoundException extends RuntimeException{

    public TechnicNotFoundException() {
    }

    public TechnicNotFoundException(String message) {
        super(message);
    }
}
