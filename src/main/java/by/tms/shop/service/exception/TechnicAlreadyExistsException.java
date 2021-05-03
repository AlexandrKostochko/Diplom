package by.tms.shop.service.exception;

public class TechnicAlreadyExistsException extends RuntimeException{

    public TechnicAlreadyExistsException() {
    }

    public TechnicAlreadyExistsException(String message) {
        super(message);
    }

}
