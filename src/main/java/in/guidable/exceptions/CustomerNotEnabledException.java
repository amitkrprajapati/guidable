package in.guidable.exceptions;

public class CustomerNotEnabledException extends RuntimeException {
  public CustomerNotEnabledException(String customerNotEnabled) {
    super(customerNotEnabled);
  }
}
