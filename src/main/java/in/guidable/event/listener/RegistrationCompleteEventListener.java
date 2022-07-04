package in.guidable.event.listener;

import in.guidable.entities.Customer;
import in.guidable.event.RegistrationCompleteEvent;
import in.guidable.services.CustomerValidationService;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RegistrationCompleteEventListener
    implements ApplicationListener<RegistrationCompleteEvent> {

  public static final String VALIDATION_VERIFY_REGISTRATION_TOKEN =
      "/validation/verifyRegistration?token=";
  @Autowired private CustomerValidationService customerValidationService;

  @Override
  public void onApplicationEvent(RegistrationCompleteEvent event) {

    Customer customer = event.getCustomer();
    String token = UUID.randomUUID().toString();
    customerValidationService.saveVerificationTokenForUser(token, customer);

    String url = event.getApplicationUrl() + VALIDATION_VERIFY_REGISTRATION_TOKEN + token;

    // TODO sendVerificationEmail()
    log.info("Click the link to verify your account: {}", url);
  }
}
