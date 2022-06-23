package in.guidable.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@AttributeOverride(name = "id",column = @Column(name = "roadmap_id",length = 16))
public class SharableResourceView extends BaseEntity{
    String userToken;
    String resourceId;
}
