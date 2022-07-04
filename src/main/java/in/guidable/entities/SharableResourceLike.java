package in.guidable.entities;

import java.util.UUID;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@AttributeOverride(name = "id", column = @Column(name = "like_id", length = 16))
public class SharableResourceLike extends BaseEntity {

  UUID customerId;
  UUID resourceId;
}
