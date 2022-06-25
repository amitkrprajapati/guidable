package in.guidable.repositories;

import in.guidable.entities.Journey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JourneyRepo extends JpaRepository<Journey, UUID> {
}
