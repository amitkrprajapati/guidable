package in.guidable.exceptions;

public class NoRoadMapFoundException extends RuntimeException {
  public NoRoadMapFoundException(String message) {
    super(message);
  }
}
