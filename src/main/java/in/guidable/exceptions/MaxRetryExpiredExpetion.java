package in.guidable.exceptions;

public class MaxRetryExpiredExpetion extends RuntimeException {
  public MaxRetryExpiredExpetion(String max_retry_expired) {
    super(max_retry_expired);
  }
}
