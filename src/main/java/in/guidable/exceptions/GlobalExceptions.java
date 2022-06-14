package in.guidable.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice
public class GlobalExceptions {

    @ExceptionHandler(value = CustomerPresnetException.class)
    public ResponseEntity<?> handleCustomerPresntExcption(CustomerPresnetException customerPresnetException){

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
