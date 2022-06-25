package in.guidable.exceptions;

import lombok.AllArgsConstructor;
import lombok.Setter;

@Setter
@AllArgsConstructor
public class ExceptionResponse {
    private int statusCode;
    private String message;
}
