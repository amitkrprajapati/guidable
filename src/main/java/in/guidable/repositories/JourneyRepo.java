package in.guidable.repositories;

import in.guidable.entities.Journey;
import java.util.Optional;
import java.util.UUID;

import in.guidable.entities.Roadmap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JourneyRepo extends JpaRepository<Journey, UUID> {

  Page<Journey> findByCustomer_Id(UUID userId, Pageable pageable);

  Optional<Journey> findByCustomer_IdAndId(UUID userId, UUID journeyId);

  void deleteByCustomerIdAndId(UUID userId, UUID journeyId);

  @Query("SELECT j FROM Journey j where j.publicMetadata.isSharable = true")
  Page<Journey> getAllBySharedJourneys(Pageable pageable);
}
