package in.guidable.exceptions;

import org.springframework.http.HttpStatus;

public class RenderableExceptionGenerator {

    public static RenderableException generateEntityNotFoundException(String resourceName, String resourceId)
    {
        String message = String.format("Resource %s with resourceId %s,Not Found", resourceName, resourceId);
        return new RenderableException(message, HttpStatus.NOT_FOUND);
    }
    public static RenderableException generateInvalidParameterException(String parameterName)
    {
        String message = String.format("Invalid parameter %s", parameterName);
        return new RenderableException(message, HttpStatus.BAD_REQUEST);
    }
}
