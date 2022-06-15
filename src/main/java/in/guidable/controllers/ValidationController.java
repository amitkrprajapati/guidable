package in.guidable.controllers;

import in.guidable.api.ValidationApi;
import in.guidable.exceptions.InvalidCredentialsException;
import in.guidable.jwt.JwtUtil;
import in.guidable.model.AuthRequest;
import in.guidable.model.SignUpDTO;
import in.guidable.services.CustomerValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ValidationController implements ValidationApi {

    private final CustomerValidationService customerLoginService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;


    @Override
    public ResponseEntity<Void> customerSignUp(@RequestBody SignUpDTO signUpDTO) {

        customerLoginService.verifyAndSaveCustomer(signUpDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<String> customerLogin(@RequestBody AuthRequest authRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword())
            );
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new InvalidCredentialsException("inavalid username/password");
        }
        return new ResponseEntity<>(jwtUtil.generateToken(authRequest.getUserName()), HttpStatus.OK);
    }
    /*
    * Demo to access any api after login
    *  @GetMapping("/test")
    public void test(@RequestHeader("Authorization") String token){
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            String userName = jwtUtil.extractUsername(token);
            System.out.println("username is: "+userName);
        }
    }
    * */





}
