package in.guidable.entities;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CustomerDetails implements UserDetails {
    private String customerUserName;
    private String customerEmail;
    private String customerPassword;
    private List<GrantedAuthority> userRoles;

    public  CustomerDetails(Customer customer){
        this.customerUserName = customer.getCustomerUserName();
        this.customerEmail = customer.getCustomerEmail();
        this.customerPassword = customer.getCustomerPassword();
        this.userRoles = Stream.of(customer.getUserRole().toString()).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userRoles;
    }

    @Override
    public String getPassword() {
        return customerPassword;
    }

    @Override
    public String getUsername() {
        return customerUserName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
