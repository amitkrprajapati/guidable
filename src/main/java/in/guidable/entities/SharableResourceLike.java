package in.guidable.entities;

import java.util.UUID;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@AttributeOverride(name = "id", column = @Column(name = "like_id", length = 16))
public class SharableResourceLike extends BaseEntity {
  @Column(length = 16)
  UUID customerId;

  @Column(length = 16)
  UUID resourceId;
}
