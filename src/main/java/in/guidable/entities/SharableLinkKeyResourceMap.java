package in.guidable.entities;

import in.guidable.model.PublicResourceType;
import java.util.UUID;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@AttributeOverride(name = "id", column = @Column(name = "link_id", length = 16))
public class SharableLinkKeyResourceMap extends BaseEntity {
  private UUID resourceId;
  private PublicResourceType objectType;
  private UUID customerId;

  @Column(unique = true)
  private String linkKey;

  private Boolean isEnabled;
}
