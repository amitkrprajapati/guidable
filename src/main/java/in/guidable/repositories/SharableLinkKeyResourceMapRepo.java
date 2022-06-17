package in.guidable.repositories;

import in.guidable.entities.SharableLinkKeyResourceMap;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;


@Repository
public interface SharableLinkKeyResourceMapRepo extends JpaRepository<SharableLinkKeyResourceMap, Long> {
    @Modifying
    @Query("UPDATE SharableLinkKeyResourceMap linkMap SET linkMap.isEnabled = :status where linkMap.resourceId= :resourceId")
    void changeLinkStatus(@Param("resourceId") String resourceId, Boolean status);

    Optional<SharableLinkKeyResourceMap> findByLinkKey(String linkKey);

    Collection<SharableLinkKeyResourceMap> findAllByLinkKey(String linkKey);

    default String generateUniqueLinkKey() throws Exception {
        int itr = 0;
        int MAX_ITERATION_FOR_FINDING_LINK_KEY = 50;
        while(itr++ < MAX_ITERATION_FOR_FINDING_LINK_KEY) {
             String linkKey = RandomStringUtils.randomAlphanumeric(6);

             if(this.findAllByLinkKey(linkKey).size() == 0)
             {
                 return linkKey;
             }
        }
        throw new Exception();
    }
}
