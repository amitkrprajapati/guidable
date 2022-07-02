package in.guidable.services;

import in.guidable.entities.Customer;
import in.guidable.entities.VerificationToken;
import in.guidable.exceptions.CustomerNotEnabledException;
import in.guidable.exceptions.CustomerPresnetException;
import in.guidable.exceptions.InvalidCredentialsException;
import in.guidable.exceptions.MaxRetryExpiredExpetion;
import in.guidable.exceptions.TokenExpiredException;
import in.guidable.model.AuthRequest;
import in.guidable.model.SignUpDTO;
import in.guidable.repositories.CustomerRepo;
import in.guidable.repositories.VerificationTokenRepository;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CustomerValidationService {

  public static final String VALIDATION_VERIFY_REGISTRATION_TOKEN =
      "/validation/verifyRegistration?token=";

  private final CustomerRepo customerRepo;
  private final ModelMapper modelMapper;
  private final PasswordEncoder passwordEncoder;
  private final VerificationTokenRepository verificationTokenRepository;
  private final AuthenticationManager authenticationManager;

  public Customer verifyAndSaveCustomer(SignUpDTO signUpDTO) {
    if (customerRepo.findByCustomerUserName(signUpDTO.getCustomerUserName()).isPresent()) {
      throw new CustomerPresnetException("Username Already Present");
    }
    if (customerRepo.findByCustomerEmail(signUpDTO.getCustomerEmail()).isPresent()) {
      throw new CustomerPresnetException("Email Already Present for this customer");
    }
    Customer customer = null;
    try {
      signUpDTO.setCustomerPassword(passwordEncoder.encode(signUpDTO.getCustomerPassword()));
      customer = modelMapper.map(signUpDTO, Customer.class);
      customer.setCreationDate(new Date());
      customer = customerRepo.save(customer);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return customer;
  }

  public void validateUserNameAndPassword(AuthRequest authRequest) {
    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
              authRequest.getUserName(), authRequest.getPassword()));
    } catch (Exception ex) {
      ex.printStackTrace();
      throw new InvalidCredentialsException("inavalid username/password");
    }
  }

  public void checkIfCustomerEnabled(AuthRequest authRequest) {
    boolean isCustomerEnabled =
        customerRepo.findByCustomerUserName(authRequest.getUserName()).get().isEnabled();
    if (!isCustomerEnabled) {
      throw new CustomerNotEnabledException("Customer Not Enabled");
    }
  }

  public void saveVerificationTokenForUser(String token, Customer customer) {
    VerificationToken verificationToken = new VerificationToken(customer, token);
    verificationToken.setRetryCount(1);
    verificationTokenRepository.save(verificationToken);
  }

  public String validateVerificationToken(String token) {
    VerificationToken verificationToken = verificationTokenRepository.findByToken(token);

    if (verificationToken == null) {
      throw new InvalidCredentialsException("Invalid Token");
    }
    if (verificationToken.isVerified()) {
      return "Verification Is Already Completed Goto Login Page";
    }

    Customer customer = verificationToken.getCustomer();
    Calendar cal = Calendar.getInstance();

    if ((verificationToken.getExpirationTime().getTime() - cal.getTime().getTime()) <= 0) {
      verificationTokenRepository.delete(verificationToken);
      throw new TokenExpiredException("Token Expired");
    }

    customer.setEnabled(true);
    verificationToken.setVerified(true);
    customerRepo.save(customer);
    verificationTokenRepository.save(verificationToken);
    return "Verification Done Goto Login Page";
  }

  public String resendVerificationToken(String userName, String prefixUrl) {

    Customer customer = customerRepo.findByCustomerUserName(userName).orElseThrow();

    VerificationToken oldVerificationToken =
        verificationTokenRepository.getVerificationTokenByUserId(customer.getId());

    if (oldVerificationToken != null) {
      if (oldVerificationToken.isVerified()) {
        return "Verification Is Already Completed Goto Login Page";
      }
      if (oldVerificationToken.getRetryCount() == 2) {
        throw new MaxRetryExpiredExpetion("Max retry expired");
      }
      Calendar cal = Calendar.getInstance();
      if ((oldVerificationToken.getExpirationTime().getTime() - cal.getTime().getTime()) <= 0) {
        verificationTokenRepository.delete(oldVerificationToken);
        throw new TokenExpiredException("Token Expired");
      }
      String newToken = UUID.randomUUID().toString();
      VerificationToken newVerificationToken = new VerificationToken(customer, newToken);
      newVerificationToken.setId(oldVerificationToken.getId());
      newVerificationToken.setRetryCount(oldVerificationToken.getRetryCount() + 1);
      verificationTokenRepository.save(newVerificationToken);

      // end email
      String url = prefixUrl + VALIDATION_VERIFY_REGISTRATION_TOKEN + newToken;
      log.info("Click the link to verify your account new URL: {}", url);
    }
    return "Email Sent";
  }
}
