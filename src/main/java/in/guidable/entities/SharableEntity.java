package in.guidable.entities;

import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public class SharableEntity extends BaseEntity {
  @Embedded private PublicMetadata publicMetadata;
}
