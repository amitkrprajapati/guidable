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
@AttributeOverride(name = "id", column = @Column(name = "roadmap_id", length = 16))
public class SharableResourceView extends BaseEntity {
  String userToken;
  UUID resourceId;
}
