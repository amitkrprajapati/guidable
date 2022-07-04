package in.guidable.entities;

import java.util.UUID;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder(toBuilder = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@AttributeOverride(name = "id", column = @Column(name = "roadmap_id", length = 16))
public class Roadmap extends SharableEntity {
  private String name;
  private String description;
  private String originalAuthor;
  private String updatedBy;

  @Column(length = 16)
  private UUID customerId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "journey_id", referencedColumnName = "journey_Id")
  private Journey journey;
}
