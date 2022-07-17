package in.guidable.entities;

import java.util.UUID;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.*;

@Entity
@Builder(toBuilder = true)
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@AttributeOverride(name = "id", column = @Column(name = "checkpoint_id", length = 16))
public class Checkpoints extends BaseEntity {
  private String name;
  private String quickLink;
  private String shortDescription;
  private String detailedDescription;
  private boolean isComplete;

  @ManyToOne
  @JoinColumn(name = "roadmap_id", referencedColumnName = "roadmap_id")
  private Roadmap roadmap;

  @Column(length = 16)
  private UUID customerId;
}
