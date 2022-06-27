package in.guidable.repositories;

import in.guidable.entities.Customer;
import in.guidable.entities.Journey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JourneyRepo extends JpaRepository<Journey, UUID> {

    Optional<Page<Journey>> findByCustomer(Customer customer, Pageable pageable);

    Optional<Journey> findByCustomerAndId(Customer customer, UUID journeyId);

}
