package in.guidable.entities;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
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
@AttributeOverride(name = "id", column = @Column(name = "journey_id", length = 16))
public class Journey extends SharableEntity {
  private String name;
  private String description;
  private String originalAuthor;
  private String updatedBy;
  @Embedded private PublicMetadata publicMetadata;

  @ManyToOne
  @JoinColumn(name = "customer_id", referencedColumnName = "customer_Id")
  private Customer customer;
}
