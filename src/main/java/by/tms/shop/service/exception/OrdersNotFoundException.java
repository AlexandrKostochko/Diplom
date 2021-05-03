package by.tms.shop.service.exception;

public class OrdersNotFoundException extends RuntimeException{

    public OrdersNotFoundException() {
    }

    public OrdersNotFoundException(String message) {
        super(message);
    }
}
