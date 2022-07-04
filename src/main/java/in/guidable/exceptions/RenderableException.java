package in.guidable.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class RenderableException extends RuntimeException {
  private final ExceptionResponse exceptionResponse;
  private final HttpStatus errorCode;

  public RenderableException(String message, HttpStatus errorCode) {
    super(message);
    exceptionResponse = new ExceptionResponse(errorCode.value(), message);
    this.errorCode = errorCode;
  }
}
