package in.guidable.util;


import in.guidable.exceptions.RenderableException;
import in.guidable.exceptions.RenderableExceptionGenerator;
import in.guidable.jwt.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AuthenticationUtil {
    public static final String CUSTOMER_ID = "customerId";
    private final JwtUtil jwtUtil;
    public String getUserFromToken(String token) throws RenderableException
    {
        try
        {
            if (token != null && token.startsWith("Bearer ")) {
                token = getTokenFromHeader(token);
                return jwtUtil.extractUsername(token);
            }
        } catch (Exception e)
        {
            throw RenderableExceptionGenerator.generateInvalidAuthTokenException();
        }
        throw RenderableExceptionGenerator.generateInvalidAuthTokenException();
    }
    /**
     * Returns customerId as UUID
     * Accepts authHeader
     */
    public UUID getCustomerIdFromToken(String authHeader){
        final String token = getTokenFromHeader(authHeader);
        Claims claims = jwtUtil.extractAllClaims(token);
        return  UUID.fromString((String) claims.get(CUSTOMER_ID));
    }

    private String getTokenFromHeader(String token) {
        token = token.substring(7);
        return token;
    }
}
