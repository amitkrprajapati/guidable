package in.guidable.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Getter
@Setter
public class SharableEntity extends BaseEntity {
    @Embedded
    private PublicMetadata publicMetadata;
}
