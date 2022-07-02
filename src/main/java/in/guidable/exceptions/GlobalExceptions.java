package in.guidable.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptions {

  @ExceptionHandler(value = CustomerPresnetException.class)
  public ResponseEntity<String> handleCustomerPresntExcption(CustomerPresnetException e) {

    return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = InvalidCredentialsException.class)
  public ResponseEntity<String> handleCustomerPresntExcption(InvalidCredentialsException e) {

    return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(value = NoRoadMapFoundException.class)
  public ResponseEntity<String> handleNoRoadMapFoundException(NoRoadMapFoundException e) {

    return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(value = TokenExpiredException.class)
  public ResponseEntity<String> handleTokenExpirationExcption(TokenExpiredException e) {

    return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = CustomerNotEnabledException.class)
  public ResponseEntity<String> handleCustomerNotEnabledExcption(CustomerNotEnabledException e) {

    return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = MaxRetryExpiredExpetion.class)
  public ResponseEntity<String> handleMaxRetryExpiredExpetion(MaxRetryExpiredExpetion e) {

    return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = RenderableException.class)
  public ResponseEntity<ExceptionResponse> handleRenderableException(RenderableException e) {
    return new ResponseEntity<>(e.getExceptionResponse(), e.getErrorCode());
  }
}
