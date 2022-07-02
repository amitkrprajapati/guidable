package in.guidable.exceptions;

public class MaxRetryExpiredExpetion extends RuntimeException {
  public MaxRetryExpiredExpetion(String maxRetryExpired) {
    super(maxRetryExpired);
  }
}
