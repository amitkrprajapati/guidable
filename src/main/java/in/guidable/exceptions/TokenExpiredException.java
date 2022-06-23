package in.guidable.exceptions;

public class TokenExpiredException extends RuntimeException {
    public TokenExpiredException(String token_expired) {
        super(token_expired);
    }
}
