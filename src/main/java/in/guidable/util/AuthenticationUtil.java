package in.guidable.util;

import in.guidable.exceptions.RenderableException;
import in.guidable.exceptions.RenderableExceptionGenerator;
import in.guidable.jwt.JwtUtil;
import in.guidable.models.CustomerModel;
import io.jsonwebtoken.Claims;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationUtil {
  public static final String CUSTOMER_ID = "customerId";
  private final JwtUtil jwtUtil;

  public String getUserFromToken(String token) throws RenderableException {
    try {
      if (token != null && token.startsWith("Bearer ")) {
        token = getTokenFromHeader(token);
        return jwtUtil.extractUsername(token);
      }
    } catch (Exception e) {
      throw RenderableExceptionGenerator.generateInvalidAuthTokenException();
    }
    throw RenderableExceptionGenerator.generateInvalidAuthTokenException();
  }
  /** Returns customerId as UUID Accepts authHeader */
  public CustomerModel getCustomerModelFromToken(String authHeader) {
    final String token = getTokenFromHeader(authHeader);
    Claims claims = jwtUtil.extractAllClaims(token);
    String userName = jwtUtil.extractUsername(token);
    UUID userId = UUID.fromString((String) claims.get(CUSTOMER_ID));
    return CustomerModel.builder().userId(userId).userName(userName).build();
  }

  private String getTokenFromHeader(String token) {
    token = token.substring(7);
    return token;
  }
}
