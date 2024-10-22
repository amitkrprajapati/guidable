package in.guidable.repositories;

import in.guidable.entities.Customer;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, UUID> {

  Optional<Customer> findByCustomerUserName(String customerUserName);

  Optional<Customer> findByCustomerEmail(String customerEmail);
}
