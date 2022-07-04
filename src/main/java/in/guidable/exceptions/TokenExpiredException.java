package in.guidable.exceptions;

public class TokenExpiredException extends RuntimeException {
  public TokenExpiredException(String tokenExpired) {
    super(tokenExpired);
  }
}
