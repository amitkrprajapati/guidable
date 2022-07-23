package in.guidable.event.services;

import in.guidable.entities.Customer;
import in.guidable.entities.CustomerDetails;
import in.guidable.repositories.CustomerRepo;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final CustomerRepo customerRepo;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<Customer> customer = customerRepo.findByCustomerUserName(username);

    return customer
        .map(CustomerDetails::new)
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
  }
}
