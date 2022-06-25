package in.guidable.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@AllArgsConstructor
@Getter
@NoArgsConstructor
public class ExceptionResponse {
    private int status;
    private String message;
}
