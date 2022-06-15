package in.guidable.controllers;

import in.guidable.api.SignupApi;
import in.guidable.model.SignUpDTO;
import in.guidable.services.CustomerValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SignUpController implements SignupApi {

    private final CustomerValidationService customerLoginService;

    @Override
    @PostMapping("/signup")
    public ResponseEntity<Void> customerSignUp(@RequestBody SignUpDTO signUpDTO) {

        customerLoginService.verifyAndSaveCustomer(signUpDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
