package in.guidable.repositories;

import in.guidable.entities.Checkpoints;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckpointRepo extends JpaRepository<Checkpoints, UUID> {}
