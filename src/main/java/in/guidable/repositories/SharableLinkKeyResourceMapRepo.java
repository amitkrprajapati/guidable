package in.guidable.repositories;

import in.guidable.entities.SharableLinkKeyResourceMap;
import in.guidable.exceptions.RenderableExceptionGenerator;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SharableLinkKeyResourceMapRepo
    extends JpaRepository<SharableLinkKeyResourceMap, UUID> {
  @Modifying
  @Query(
      "UPDATE SharableLinkKeyResourceMap linkMap SET linkMap.isEnabled = :status where linkMap.resourceId= :resourceId")
  void changeLinkStatus(@Param("resourceId") UUID resourceId, Boolean status);

  Optional<SharableLinkKeyResourceMap> findByLinkKey(String linkKey);

  Collection<SharableLinkKeyResourceMap> findAllByLinkKey(String linkKey);

  default String generateUniqueLinkKey() {
    int itr = 0;
    int MAX_ITERATION_FOR_FINDING_LINK_KEY = 50;
    while (itr++ < MAX_ITERATION_FOR_FINDING_LINK_KEY) {
      String linkKey = RandomStringUtils.randomAlphanumeric(6);

      if (this.findAllByLinkKey(linkKey).size() == 0) {
        return linkKey;
      }
    }
    throw RenderableExceptionGenerator.generateInternalServerError();
  }
}
