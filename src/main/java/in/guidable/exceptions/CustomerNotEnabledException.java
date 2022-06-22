package in.guidable.exceptions;

public class CustomerNotEnabledException extends RuntimeException {
    public CustomerNotEnabledException(String customer_not_enabled) {
        super(customer_not_enabled);
    }
}
