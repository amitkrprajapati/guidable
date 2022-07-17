package in.guidable.repositories;

import in.guidable.entities.Checkpoints;

import java.util.Optional;
import java.util.UUID;

import in.guidable.entities.Roadmap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckpointRepo extends JpaRepository<Checkpoints, UUID> {
    Page<Checkpoints> findAllByRoadmap(Roadmap roadmap, Pageable pageable);

    Optional<Checkpoints> findByCustomerIdAndId(UUID customerId, UUID checkpointId);

    @Modifying
    void deleteByCustomerIdAndId(UUID customerId, UUID checkpointId);
}
