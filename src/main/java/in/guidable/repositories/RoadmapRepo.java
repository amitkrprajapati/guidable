package in.guidable.repositories;

import in.guidable.entities.Roadmap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoadmapRepo extends JpaRepository<Roadmap, String> {
}
