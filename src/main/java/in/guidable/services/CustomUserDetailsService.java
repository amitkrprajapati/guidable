package in.guidable.services;

import in.guidable.entities.Customer;
import in.guidable.entities.CustomerDetails;
import in.guidable.repositories.CustomerRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final CustomerRepo customerRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Customer> customer = customerRepo.findByCustomerUserName(username);

      return customer.map(CustomerDetails::new).orElseThrow(()-> new UsernameNotFoundException("User not found"));
    }
}
