package in.guidable.repositories;

import in.guidable.entities.Customer;
import in.guidable.entities.Journey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface JourneyRepo extends JpaRepository<Journey, UUID> {

    Optional<Journey> findByIdAndCustomer(UUID journeyId, Customer customer);
}
