package in.guidable.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice
public class GlobalExceptions {

    @ExceptionHandler(value = CustomerPresnetException.class)
    public ResponseEntity<String> handleCustomerPresntExcption(CustomerPresnetException e){

        return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = InvalidCredentialsException.class)
    public ResponseEntity<String> handleCustomerPresntExcption(InvalidCredentialsException e){

        return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
    }
}
