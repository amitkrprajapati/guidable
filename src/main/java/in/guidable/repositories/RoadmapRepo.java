package in.guidable.repositories;

import in.guidable.entities.Customer;
import in.guidable.entities.Roadmap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoadmapRepo extends JpaRepository<Roadmap, UUID> {

}
