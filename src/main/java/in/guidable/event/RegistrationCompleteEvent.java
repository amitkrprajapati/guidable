package in.guidable.event;

import in.guidable.entities.Customer;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class RegistrationCompleteEvent extends ApplicationEvent {

  private final Customer customer;
  private final String applicationUrl;

  public RegistrationCompleteEvent(Customer customer, String applicationUrl) {
    super(customer);
    this.customer = customer;
    this.applicationUrl = applicationUrl;
  }
}
