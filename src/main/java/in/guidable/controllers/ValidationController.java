package in.guidable.controllers;

import in.guidable.api.ValidationApi;
import in.guidable.entities.Customer;
import in.guidable.event.RegistrationCompleteEvent;
import in.guidable.event.services.CustomerValidationService;
import in.guidable.jwt.JwtUtil;
import in.guidable.model.AuthRequest;
import in.guidable.model.SignUpDTO;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ValidationController implements ValidationApi {

  private final CustomerValidationService customerValidationService;
  private final JwtUtil jwtUtil;
  private final ApplicationEventPublisher publisher;

  private final HttpServletRequest request;

  @Override
  public ResponseEntity<Void> customerSignUp(@RequestBody SignUpDTO signUpDTO) {

    Customer customer = customerValidationService.verifyAndSaveCustomer(signUpDTO);
    if (customer != null) {
      publisher.publishEvent(new RegistrationCompleteEvent(customer, applicationUrl(request)));
    }
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @Override
  public ResponseEntity<String> customerLogin(@RequestBody AuthRequest authRequest) {
    customerValidationService.validateUserNameAndPassword(authRequest);
    customerValidationService.checkIfCustomerEnabled(authRequest);
    return new ResponseEntity<>(jwtUtil.generateToken(authRequest.getUserName()), HttpStatus.OK);
  }

  @Override
  public ResponseEntity<String> verifyRegistration(@RequestParam("token") String token) {
    String result = customerValidationService.validateVerificationToken(token);

    return new ResponseEntity<>(result, HttpStatus.OK);
  }

  // @PostMapping("/reGenerateVerificationToken")
  @Override
  public ResponseEntity<String> reGenerateVerificationToken(@RequestBody AuthRequest authRequest) {
    customerValidationService.validateUserNameAndPassword(authRequest);
    String result =
        customerValidationService.resendVerificationToken(
            authRequest.getUserName(), applicationUrl(request));
    return new ResponseEntity<>(result, HttpStatus.OK);
  }

  private String applicationUrl(HttpServletRequest request) {
    return "http://"
        + request.getServerName()
        + ":"
        + request.getServerPort()
        + request.getContextPath();
  }
}
