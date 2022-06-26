package in.guidable.repositories;

import in.guidable.entities.Customer;
import in.guidable.entities.Journey;
import in.guidable.entities.Roadmap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoadmapRepo extends JpaRepository<Roadmap, UUID> {
    Page<Roadmap> findAllByJourney(Journey journey, Pageable pageable);
    Optional<Roadmap> findByIdAndCustomer(UUID roadmapId, Customer customer);

    @Modifying
    void deleteByIdAndCustomer(UUID roadmapId, Customer customer);
}
