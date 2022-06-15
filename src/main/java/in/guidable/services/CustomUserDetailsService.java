package in.guidable.services;

import in.guidable.entities.Customer;
import in.guidable.repositories.CustomerRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final CustomerRepo customerRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = customerRepo.findByCustomerUserName(username).orElseThrow(()-> new UsernameNotFoundException(""));
        return new User(customer.getCustomerUserName(), customer.getCustomerPassword(), new ArrayList<>());
    }
}
