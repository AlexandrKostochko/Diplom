package by.tms.shop.service.exception;

public class CategoriesNotFoundException extends RuntimeException{
    public CategoriesNotFoundException() {
    }

    public CategoriesNotFoundException(String message) {
        super(message);
    }
}
