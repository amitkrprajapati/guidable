package in.guidable.repositories;

import in.guidable.entities.VerificationToken;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
  VerificationToken findByToken(String token);

  @Query(nativeQuery = true, value = "select * from VERIFICATION_TOKEN_TBL where USER_ID=:userId")
  VerificationToken getVerificationTokenByUserId(UUID userId);
}
