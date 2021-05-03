package by.tms.shop.service.exception;

public class UsersNotFoundException extends RuntimeException{

    public UsersNotFoundException() {
    }

    public UsersNotFoundException(String message) {
        super(message);
    }
}
