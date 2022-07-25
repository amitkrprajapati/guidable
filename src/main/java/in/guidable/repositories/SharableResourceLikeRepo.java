package in.guidable.repositories;

import in.guidable.entities.SharableResourceLike;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SharableResourceLikeRepo extends JpaRepository<SharableResourceLike, UUID> {

  @Query("SELECT COUNT(s) FROM SharableResourceLike s where s.resourceId= :resourceId")
  Long getLikeCountOfResource(@Param("resourceId") String resourceId);

  @Query(
      "SELECT s FROM SharableResourceLike s where s.customerId= :customerId AND s.resourceId= :resourceId")
  Optional<SharableResourceLike> getCustomerLikeStatus(
      @Param("customerId") UUID customerId, @Param("resourceId") UUID resourceId);

  @Modifying
  Long deleteByCustomerIdAndResourceId(UUID customerId, UUID resourceId);
}
