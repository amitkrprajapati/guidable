package in.guidable.repositories;

import in.guidable.entities.SharableResourceLike;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SharableResourceLikeRepo extends JpaRepository<SharableResourceLike, UUID> {

  @Query("SELECT COUNT(s) FROM SharableResourceLike s where s.resourceId= :resourceId")
  Long getLikeCountOfResource(@Param("resourceId") String resourceId);
}
