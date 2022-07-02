package in.guidable.exceptions;

import org.springframework.http.HttpStatus;

public class RenderableExceptionGenerator {

  public static RenderableException generateEntityNotFoundOrNotAuthorizedException(
      String resourceName, String resourceId) {
    String message =
        String.format("%s with id %s, not found or not authorized", resourceName, resourceId);
    return new RenderableException(message, HttpStatus.NOT_FOUND);
  }

  public static RenderableException generateInvalidUserException(String username) {
    String message = String.format("Invalid user %s", username);
    return new RenderableException(message, HttpStatus.NOT_FOUND);
  }

  public static RenderableException generateInvalidParameterException(String parameterName) {
    String message = String.format("Invalid parameter %s", parameterName);
    return new RenderableException(message, HttpStatus.BAD_REQUEST);
  }

  public static RenderableException generateInvalidAuthTokenException() {
    String message = "Invalid authorization token";
    return new RenderableException(message, HttpStatus.UNAUTHORIZED);
  }

  public static RenderableException generateInternalServerError() {
    String message = "Internal Server Error";
    return new RenderableException(message, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
