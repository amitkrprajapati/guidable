package in.guidable.util;


import in.guidable.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationUtil {


    private final JwtUtil jwtUtil;
    public String getUserFromToken(String token)
    {
        String userName= "";
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            userName= jwtUtil.extractUsername(token);
        }
        return userName;
    }
}
