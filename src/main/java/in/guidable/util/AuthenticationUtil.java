package in.guidable.util;


import in.guidable.exceptions.RenderableException;
import in.guidable.exceptions.RenderableExceptionGenerator;
import in.guidable.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationUtil {
    private final JwtUtil jwtUtil;
    public String getUserFromToken(String token) throws RenderableException
    {
        try
        {
            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7);
                return jwtUtil.extractUsername(token);
            }
        } catch (Exception e)
        {
            throw RenderableExceptionGenerator.generateInvalidAuthTokenException();
        }
        throw RenderableExceptionGenerator.generateInvalidAuthTokenException();
    }
}
