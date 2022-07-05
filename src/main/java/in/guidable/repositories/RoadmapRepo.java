package in.guidable.repositories;

import in.guidable.entities.Journey;
import in.guidable.entities.Roadmap;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoadmapRepo extends JpaRepository<Roadmap, UUID> {
  Page<Roadmap> findAllByJourney(Journey journey, Pageable pageable);

  Optional<Roadmap> findByCustomerIdAndId(UUID customerId, UUID roadmapId);

  @Modifying
  Long deleteByCustomerIdAndId(UUID customerId, UUID roadmapId);

  @Query("SELECT r FROM Roadmap r where r.publicMetadata.isSharable = true")
  Page<Roadmap> getAllBySharedRoadmaps(Pageable pageable);
}
